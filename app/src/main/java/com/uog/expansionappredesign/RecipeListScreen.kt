package com.uog.expansionappredesign

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

/**
 * The screen displaying the complete, unfiltered list of all recipes available in the app.
 *
 * This screen fetches data directly from the ViewModel's master list and displays each recipe
 * using the standard, compact [RecipeCard] layout.
 *
 * @param navController The navigation controller instance.
 * @param viewModel The shared [RecipeViewModel] instance holding the master recipe list and favourite IDs.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen(navController: NavHostController, viewModel: RecipeViewModel) {
    /**
     * Retrieves the master list of all recipes.
     */
    val allRecipes = viewModel.allRecipes
    /**
     * Observes the set of IDs for recipes marked as favourites.
     */
    val favoriteIds = viewModel.favoriteRecipeIds

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Browse Recipes") },
                navigationIcon = {
                    /**
                     * Back button to return to the previous screen (Home Screen).
                     */
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        /**
         * A vertically scrolling list that efficiently displays a large number of [RecipeCard] components.
         */
        LazyColumn(contentPadding = padding) {
            items(allRecipes) { recipe ->
                RecipeCard(
                    recipe = recipe,
                    isFavorite = favoriteIds.contains(recipe.id),
                    // Toggles the favourite status when the heart icon is clicked.
                    onToggleFavorite = { viewModel.toggleFavorite(recipe.id) },
                    // Navigates to the detail screen when the card is clicked.
                    onClick = { navController.navigate("recipe_detail/${recipe.id}") },
                    isSpotlight = false,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}