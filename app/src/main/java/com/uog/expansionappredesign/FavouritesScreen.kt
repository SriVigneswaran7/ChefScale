package com.uog.expansionappredesign

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.HeartBroken
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

/**
 * The screen dedicated to displaying recipes marked as favourites by the user.
 *
 * It retrieves the live list of favourite recipes from the [RecipeViewModel] and
 * displays either a list of [RecipeCard] components or an [EmptyStateMessage].
 *
 * @param navController The navigation controller instance.
 * @param viewModel The shared [RecipeViewModel] instance.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(navController: NavHostController, viewModel: RecipeViewModel) {
    // Gets the list of favourite recipe objects directly
    val favoriteRecipes = viewModel.getFavoriteRecipes()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Favorites") },
                navigationIcon = {
                    /**
                     * Back button to return to the previous screen (Home Screen).
                     */
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding) // Apply scaffold padding to this main container
        ) {
            /**
             * Conditional logic: displays the empty state message if no favourites are found,
             * otherwise displays the scrolling list of cards.
             */
            if (favoriteRecipes.isEmpty()) {
                // EMPTY STATE
                // Shows a nice message component vertically centred
                EmptyStateMessage(
                    icon = Icons.Rounded.HeartBroken,
                    title = "No favorites yet",
                    message = "Tap the heart icon on any recipe to save it here for easy access.",
                    // weight(1f) makes this fill all available vertical space
                    modifier = Modifier.weight(1f)
                )
            } else {
                // LIST STATE
                // Shows the list of recipe cards vertically stacked
                LazyColumn(
                    // Adds spacing between cards for a cleaner look
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    // weight(1f) ensures the list takes up remaining space and scrolls properly
                    modifier = Modifier.weight(1f)
                ) {
                    items(favoriteRecipes) { recipe ->
                        RecipeCard(
                            recipe = recipe,
                            // On this screen, the recipe is guaranteed to be a favourite.
                            isFavorite = true,
                            onToggleFavorite = { viewModel.toggleFavorite(recipe.id) },
                            onClick = { navController.navigate("recipe_detail/${recipe.id}") },
                            isSpotlight = false,
                            // Add padding around individual cards so they don't touch the edges
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}