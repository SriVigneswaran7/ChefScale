package com.uog.expansionappredesign

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

/**
 * An extension function to format a [Double] value to a specified number of decimal digits.
 *
 * This is used to ensure ingredient quantities look clean and readable after scaling calculations.
 *
 * @param digits The number of decimal places to include.
 * @return The formatted string representation of the Double.
 */
fun Double.format(digits: Int) = "%.${digits}f".format(this)

/**
 * The main screen for displaying all details of a single recipe, including the portion scaler
 * and the cooking timer functionality.
 *
 * @param recipeId The unique ID of the recipe to display, passed from navigation.
 * @param navController The navigation controller instance.
 * @param viewModel The shared [RecipeViewModel] instance.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(recipeId: Int, navController: NavHostController, viewModel: RecipeViewModel) {
    // Finds the recipe object from the master list using the ID. Returns early if not found (null safety).
    val recipe = viewModel.allRecipes.find { it.id == recipeId } ?: return

    // Observes the current servings state for scaling calculations.
    val servings = viewModel.currentServings

    // Observes the current timer state for the countdown UI.
    val timerState = viewModel.timerState

    Scaffold { padding ->
        // Main vertical scrolling container
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            // Header Image with Overlay Title (Similar to Spotlight card)
            /**
             * Uses a [Box] for layering the image, back button, gradient, and text title.
             */
            Box(modifier = Modifier.height(300.dp).fillMaxWidth()) {
                Image(
                    painter = painterResource(id = recipe.imageResId),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                // Back button overlaid on image
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.padding(16.dp).background(Color.Black.copy(alpha=0.5f), MaterialTheme.shapes.small)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                // Gradient and Title Overlay
                /**
                 * Applies a vertical gradient brush to ensure the white text at the bottom is readable over the image.
                 */
                Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.9f)), startY = 200f)))
                Column(modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)) {
                    Text(recipe.title, style = MaterialTheme.typography.headlineMedium, color = Color.White, fontWeight = FontWeight.Bold)
                    Text(recipe.description, style = MaterialTheme.typography.bodyMedium, color = Color.LightGray)
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                // Portion Scaler Slider
                Text("Servings: $servings", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                /**
                 * Slider component used to control the [currentServings] state in the ViewModel.
                 */
                Slider(
                    value = servings.toFloat(),
                    onValueChange = { viewModel.updateServings(it.toInt()) },
                    valueRange = 1f..10f,
                    steps = 8
                )

                Divider(modifier = Modifier.padding(vertical = 16.dp))

                // Cooking Timer (Multithreading UI)
                Text("Cooking Timer", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                /**
                 * Displays the current countdown time and the control button.
                 */
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    // Format seconds to MM:SS
                    val minutes = timerState.secondsRemaining / 60
                    val seconds = timerState.secondsRemaining % 60
                    Text(String.format("%02d:%02d", minutes, seconds), style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.primary)

                    Button(onClick = {
                        // Just tells the brain to toggle. It handles the logic.
                        viewModel.toggleTimer(recipe.cookingTime)
                    }) {
                        // Text updates based on the timer's current state (active, paused, or stopped).
                        Text(if (timerState.isActive) "Pause Timer"
                        else if (timerState.secondsRemaining > 0) "Resume Timer"
                        else "Start ${recipe.cookingTime}m Timer")
                    }
                }

                Divider(modifier = Modifier.padding(vertical = 16.dp))

                // Ingredients List (Calculated)
                Text("Ingredients", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                /**
                 * Iterates through ingredients and calculates new quantities based on the current servings value.
                 */
                recipe.ingredients.forEach { ingredient ->
                    // Calculates scaling factor (base recipe is for 2 servings)
                    val factor = servings.toDouble() / 2.0
                    val newQuantity = ingredient.quantity * factor
                    Text("• ${newQuantity.format(1)} ${ingredient.unit} ${ingredient.name}", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(vertical = 4.dp))
                }

                Divider(modifier = Modifier.padding(vertical = 16.dp))

                // Steps List
                Text("Instructions", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                /**
                 * Displays the instruction steps in a numbered list format.
                 */
                recipe.steps.forEachIndexed { index, step ->
                    Row(modifier = Modifier.padding(vertical = 8.dp)) {
                        Text("${index + 1}.", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(step, style = MaterialTheme.typography.bodyLarge)
                    }
                }
                Spacer(modifier = Modifier.height(48.dp)) // Bottom padding
            }
        }
    }
}