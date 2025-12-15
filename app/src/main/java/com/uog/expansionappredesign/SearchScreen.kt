package com.uog.expansionappredesign

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SearchOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

/**
 * The dedicated screen for searching and viewing filtered recipe results.
 *
 * This screen contains the actual interactive [TextField] for searching and
 * displays results reactively based on the user's input and category selection
 * from the Home Screen.
 *
 * @param navController The navigation controller instance.
 * @param viewModel The shared [RecipeViewModel] holding the search query and filtered list state.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController, viewModel: RecipeViewModel) {
    val query = viewModel.searchQuery
    // Observes the filtered list
    val filteredList = viewModel.filteredRecipes
    val favoriteIds = viewModel.favoriteRecipeIds

    /**
     * Tool used to request focus on the search field, allowing the keyboard to pop up automatically.
     */
    val focusRequester = remember { FocusRequester() }

    /**
     * Executes once when the composable enters the composition, requesting focus
     * on the associated [TextField] to automatically display the soft keyboard.
     */
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    // The Real Search TextField
                    TextField(
                        value = query,
                        onValueChange = { viewModel.updateSearch(it) }, // Update brain on every letter
                        placeholder = { Text("Type chicken, pasta...") },
                        modifier = Modifier.fillMaxWidth().focusRequester(focusRequester), // Attach magnet
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        trailingIcon = {
                            /**
                             * Display a clear button only if there is text in the search bar.
                             */
                            if (query.isNotEmpty()) {
                                IconButton(onClick = { viewModel.updateSearch("") }) {
                                    Icon(Icons.Default.Close, null)
                                }
                            }
                        }
                    )
                },
                navigationIcon = {
                    /**
                     * Back button to return to the previous screen (Home or Recipe List).
                     */
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        /**
         * Results List
         * Conditional rendering logic: If the query is not empty and the filtered list has no results,
         * display the [EmptyStateMessage].
         */
        if (query.isNotEmpty() && filteredList.isEmpty()) {
            Box(modifier = Modifier.padding(padding)) {
                EmptyStateMessage(
                    icon = androidx.compose.material.icons.Icons.Rounded.SearchOff,
                    title = "No matching recipes",
                    message = "Try adjusting your search term to find what you're looking for.",
                    modifier = Modifier.fillMaxSize()
                )
            }
        } else {
            /**
             * Displays the list of matching recipes using the compact list layout.
             */
            LazyColumn(contentPadding = padding) {
                items(items = filteredList) { recipe ->
                    RecipeCard(
                        recipe = recipe,
                        isFavorite = favoriteIds.contains(recipe.id),
                        onToggleFavorite = { viewModel.toggleFavorite(recipeId = recipe.id) },
                        onClick = { navController.navigate(route = "recipe_detail/${recipe.id}") },
                        isSpotlight = false, // Use list layout
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}