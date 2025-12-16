package com.uog.expansionappredesign

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.media.RingtoneManager

/**
 * The core brain of the app (ViewModel layer).
 *
 * This class holds all app state and business logic, managing interactions
 * between the data and the UI. It extends [AndroidViewModel] to allow access
 * to the app context, which is necessary for system services like the timer alert.
 *
 * @param app The app instance required by [AndroidViewModel].
 */
class RecipeViewModel(app: Application) : AndroidViewModel(app) {

    /**
     * Master Data
     * The complete, static list of all recipes available in the app.
     */
    val allRecipes = RecipeData.recipes
    /**
     * The list of unique category strings used for the Home Screen chips.
     */
    val allCategories = RecipeData.allCategories
    /**
     * A randomly selected list of four recipes for the Home Screen spotlight carousel.
     */
    val spotlightRecipes = allRecipes.shuffled().take(4)

    /**
     * App Settings Logic (Dark Mode)
     * The observable state representing the current theme setting.
     * The 'by' keyword is used for idiomatic Compose state delegation.
     */
    var isDarkTheme by mutableStateOf(false)
        private set

    /**
     * Toggles the theme between light and dark mode.
     * Updates the observable [isDarkTheme] state, which triggers a UI recomposition.
     */
    fun toggleTheme() {
        isDarkTheme = !isDarkTheme
    }

    /**
     * Portion Scaler Logic
     * The observable integer state representing the current number of servings selected by the user.
     * This value drives the real-time ingredient calculations on the detail screen.
     */
    var currentServings by mutableStateOf(2) //ask mutable state of
        private set

    /**
     * Updates the number of servings requested by the user.
     *
     * @param newServings The new serving count.
     */
    fun updateServings(newServings: Int) {
        currentServings = newServings
    }

    /**
     * Favourites System Logic
     * The private mutable state holding the set of IDs for all favourite recipes.
     */
    private var _favoriteRecipeIds = mutableStateOf<Set<Int>>(emptySet())
    /**
     * The publicly exposed, read-only set of favourite recipe IDs.
     * This provides encapsulation, ensuring the set can only be modified via [toggleFavorite].
     */
    val favoriteRecipeIds: Set<Int> get() = _favoriteRecipeIds.value

    /**
     * Toggles the favourite status of a recipe based on its ID.
     * If the recipe is currently favourite, it is removed; otherwise, it is added.
     *
     * @param recipeId The unique ID of the recipe to toggle.
     */
    fun toggleFavorite(recipeId: Int) {
        val currentFavorites = _favoriteRecipeIds.value.toMutableSet()
        if (currentFavorites.contains(recipeId)) {
            currentFavorites.remove(recipeId)
        } else {
            currentFavorites.add(recipeId)
        }
        _favoriteRecipeIds.value = currentFavorites
    }

    /**
     * Retrieves the actual recipe objects that are currently marked as favourites.
     *
     * @return A list of [Recipe] objects that match the IDs in [favoriteRecipeIds].
     * @see toggleFavorite
     */
    fun getFavoriteRecipes(): List<Recipe> {
        return allRecipes.filter { favoriteRecipeIds.contains(it.id) }
    }

    /**
     * Search & Filter Logic
     * The observable string representing the user's current search query text.
     */
    var searchQuery by mutableStateOf("")
        private set

    /**
     * The list of recipes currently matching the [searchQuery] and/or the selected category filter.
     * The UI observes this list for display on the Search Screen.
     */
    var filteredRecipes by mutableStateOf(allRecipes)
        private set

    /**
     * Updates the search query and applies the filter logic to the master list of recipes.
     *
     * This function implements complex filtering logic based on both text and category,
     * ensuring recipes match both criteria if a category is selected.
     *
     * @param query The text typed by the user.
     * @param category An optional category string selected via a chip, or null if no chip is active.
     */
    fun updateSearch(query: String, category: String? = null) {
        searchQuery = query
        // The core filtering logic
        filteredRecipes = allRecipes.filter { recipe ->
            // Check matches name OR description (case insensitive)
            val matchesQuery = recipe.title.contains(query, ignoreCase = true) ||
                    recipe.description.contains(query, ignoreCase = true)
            // Check if it matches selected category (if one is selected)
            val matchesCategory = category == null || recipe.categories.contains(category)

            matchesQuery && matchesCategory
        }
    }

    /**
     * Cooking Timer Logic (Multithreading)
     * Data class representing the current state of the countdown timer.
     *
     * @property isActive True if the timer is actively counting down.
     * @property secondsRemaining The number of seconds left on the timer.
     */
    data class TimerState(val isActive: Boolean = false, val secondsRemaining: Int = 0)

    /**
     * The observable state of the cooking timer, used by the UI.
     */
    var timerState by mutableStateOf(TimerState())
        private set
    /**
     * A reference to the currently running coroutine Job.
     * This is essential for controlling the timer (pausing/cancelling) and preventing race conditions.
     */
    private var timerJob: Job? = null // Controls the background task

    /**
     * Stores the initial total time (in seconds) when the timer was started or reset.
     */
    private var timerStartSeconds = 0

    /**
     * The main public function used to control the timer.
     * It handles the logic to switch between pausing, starting new timers, or resuming existing ones.
     *
     * @param recipeMinutes The duration of the timer to set if starting fresh.
     */
    fun toggleTimer(recipeMinutes: Int) {
        if (timerState.isActive) {
            pauseTimer()
        } else {
            startOrResumeTimer(recipeMinutes)
        }
    }

    /**
     * Halts the currently running timer by cancelling the coroutine [timerJob].
     * The seconds remaining are preserved so the timer can be resumed.
     */
    private fun pauseTimer() {
        timerJob?.cancel()
        timerState = timerState.copy(isActive = false)
    }

    /**
     * Starts the countdown coroutine or resumes it from the current time.
     * This function contains the multithreading logic and the final alert implementation.
     *
     * @param recipeMinutes The total duration of the timer.
     */
    private fun startOrResumeTimer(recipeMinutes: Int) {
        val totalSeconds = recipeMinutes * 60

        if (timerState.secondsRemaining <= 0 || timerStartSeconds != totalSeconds) {
            // Reset and start fresh
            timerStartSeconds = totalSeconds
            timerState = TimerState(isActive = true, secondsRemaining = timerStartSeconds)
        } else {
            // Resume: Just flip the switch back to active, keeping current seconds.
            timerState = timerState.copy(isActive = true)
        }

        // Launch Coroutine (Background Thread) to start counting down
        timerJob?.cancel() // Ensures no duplicate jobs (prevents race conditions)
        timerJob = viewModelScope.launch {
            while (timerState.isActive && timerState.secondsRemaining > 0) {
                delay(1000) // Wait 1 second
                timerState = timerState.copy(secondsRemaining = timerState.secondsRemaining - 1)
            }
            // Loop finished because time hit 0 OR it was paused.
            if (timerState.secondsRemaining <= 0) { // Using <= 0 to be safe
                timerState = timerState.copy(isActive = false)

                // Vibration Alert Implementation
                val context = getApplication<Application>().applicationContext
                try {
                    // Get the URI for the default notification sound
                    val notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                    // Create the Ringtone object
                    val ringtone = RingtoneManager.getRingtone(context, notificationUri)
                    // Play the sound
                    ringtone.play()
                } catch (e: Exception) {
                    // Log error if sound fails, but doesn't crash the app
                    e.printStackTrace()
                }
            }
        }
    }

    /**
     * Immediately stops and resets the timer state back to zero.
     * This is useful when the user leaves the Recipe Detail screen.
     */
    fun resetTimer() {
        timerJob?.cancel()
        timerState = TimerState() // Resets to active=false, seconds=0
    }
}