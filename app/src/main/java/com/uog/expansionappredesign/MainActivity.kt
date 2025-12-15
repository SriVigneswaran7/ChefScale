package com.uog.expansionappredesign

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uog.expansionappredesign.ui.theme.ChefScaleTheme

/**
 * The main activity and entry point for the ChefScale mobile app.
 *
 * This activity sets up the Compose environment, initialises the core ViewModel,
 * and hosts the central navigation graph (NavHost).
 * It demonstrates the MVVM architecture pattern by supplying the ViewModel to the entire UI hierarchy.
 */
class MainActivity : ComponentActivity() {
    /**
     * Called when the activity is first created.
     * Sets the content and initialises the ViewModel and theme for the app.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Creates the one and only Brain
            val viewModel: RecipeViewModel = viewModel(factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application))

            // Apply Theme (connected to Brain's dark mode state)
            ChefScaleTheme(darkTheme = viewModel.isDarkTheme) {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    // Creates a navigator through screens
                    val navController = rememberNavController()
                    RecipeNavHost(navController, viewModel)
                }
            }
        }
    }
}

/**
 * Defines the navigation graph for the entire app.
 *
 * This function uses the Jetpack Compose Navigation component to link all six screens
 * and handle navigation arguments, showing the app's structure.
 *
 * @param navController The controller responsible for managing transitions between Composables.
 * @param viewModel The shared ViewModel instance containing app state and business logic.
 */
@Composable
fun RecipeNavHost(navController: NavHostController, viewModel: RecipeViewModel) {
    NavHost(navController = navController, startDestination = "home_screen") {

        // Screen 1: Home
        composable("home_screen") {
            HomeScreen(navController, viewModel)
        }

        // Screen 2: Search
        composable("search_screen") {
            SearchScreen(navController, viewModel)
        }

        // Screen 3: Browse List
        composable("recipe_list") {
            RecipeListScreen(navController, viewModel)
        }

        // Screen 4: Favourites
        composable("favorites_screen") {
            FavoritesScreen(navController, viewModel)
        }

        // Screen 5: Help/Guide
        composable(route = "help_screen") {
            HelpScreen(navController = navController)
        }

        // Screen 6: Recipe Details (Dynamic ID)
        composable("recipe_detail/{recipeId}") { backStackEntry ->
            // Safely retrieves the recipe ID, defaulting to 1 if the argument is missing (Elvis Operator)
            val recipeId = backStackEntry.arguments?.getString("recipeId")?.toIntOrNull() ?: 1

            // Pass navController so detail screen can have a "Back" button
            RecipeScreen(recipeId, navController, viewModel)
        }
    }
}