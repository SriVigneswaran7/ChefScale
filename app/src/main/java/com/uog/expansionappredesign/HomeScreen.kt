package com.uog.expansionappredesign

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.HelpOutline
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

/**
 * The main screen of the app, serving as the dashboard for browsing.
 *
 * This screen displays the spotlight carousel, category chips, and navigation buttons
 * to other sections. It handles the theme toggle and navigation to the help screen.
 *
 * @param navController The navigation controller instance used to move between screens.
 * @param viewModel The shared [RecipeViewModel] holding application state and logic.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: RecipeViewModel) {
    // Grab data from Brain
    val spotlightRecipes = viewModel.spotlightRecipes
    val allCategories = viewModel.allCategories
    val favoriteIds = viewModel.favoriteRecipeIds
    // Splits the 12 categories into two lists of 6 (for the two-row layout)
    val categoryRow1 = allCategories.take(6)
    val categoryRow2 = allCategories.drop(6)

    Scaffold(
        topBar = {
            /**
             * The application bar, centred and containing the app branding and control icons.
             */
            CenterAlignedTopAppBar(
                title = {
                    /**
                     * Displays the app logo and title text, aligned centrally.
                     */
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(painter = painterResource(id = R.mipmap.chefscale_logo), contentDescription = null, modifier = Modifier.size(32.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("ChefScale", fontWeight = FontWeight.Bold)
                    }
                },
                navigationIcon = {
                    /**
                     * Icon button to navigate to the Help Guide screen.
                     */
                    IconButton(onClick = { navController.navigate("help_screen") }) {
                        Icon(Icons.AutoMirrored.Rounded.HelpOutline, contentDescription = "Help Guide", tint = MaterialTheme.colorScheme.onSurface)
                    }
                },
                actions = {
                    /**
                     * Icon button to toggle the app's theme (light/dark mode).
                     */
                    IconButton(onClick = { viewModel.toggleTheme() }) {
                        Icon(if (viewModel.isDarkTheme) Icons.Default.Brightness7 else Icons.Default.Brightness4, contentDescription = "Toggle Theme")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState()) // Enables vertical scrolling for all content
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            /**
             * Spotlight Carousel
             * Horizontal list displaying featured recipes using the large [RecipeCard] layout.
             */
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(spotlightRecipes) { recipe ->
                    RecipeCard(
                        recipe = recipe,
                        isFavorite = favoriteIds.contains(recipe.id),
                        onToggleFavorite = { viewModel.toggleFavorite(recipe.id) },
                        onClick = { navController.navigate("recipe_detail/${recipe.id}") },
                        isSpotlight = true,
                        modifier = Modifier.width(300.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            /**
             * Fake Search Button
             * A button styled to look like a search bar, navigating to the dedicated search screen.
             */
            FakeSearchButton(onClick = { navController.navigate("search_screen") })

            Spacer(modifier = Modifier.height(24.dp))

            /**
             * Category Chips
             */
            Text("Categories", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(modifier = Modifier.height(12.dp))

            /**
             * First horizontal row of category chips (first six categories).
             */
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categoryRow1) { category ->
                    AssistChip(
                        onClick = {
                            // Clears the search text before navigating to filter by category
                            viewModel.updateSearch("", category)
                            navController.navigate("search_screen")
                        },
                        label = { Text(category) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            /**
             * Second horizontal row of category chips (remaining categories).
             */
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categoryRow2) { category ->
                    AssistChip(
                        onClick = {
                            // Clears the search text before navigating to filter by category
                            viewModel.updateSearch("", category)
                            navController.navigate("search_screen")
                        },
                        label = { Text(category) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))

            /**
             * Bottom Buttons
             */
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                /**
                 * Primary button to view the full list of all recipes.
                 */
                Button(
                    onClick = { navController.navigate("recipe_list") },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Browse All Recipes", fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.height(32.dp))
                /**
                 * Secondary button to view saved favourite recipes.
                 */
                OutlinedButton(
                    onClick = { navController.navigate("favorites_screen") },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Default.Favorite, contentDescription = null, tint = Color.Red)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("My Favorites", fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

/**
 * A dedicated composable that appears visually as a search bar but functions as a clickable button.
 *
 * It navigates to the dedicated search screen where typing is enabled.
 *
 * @param onClick Lambda function to execute navigation when the button is pressed.
 */
@Composable
fun FakeSearchButton(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(50.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.width(12.dp))
        Text("What are you craving...?", color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}