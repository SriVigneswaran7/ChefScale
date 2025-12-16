package com.uog.expansionappredesign

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

/**
 * The instructional screen providing users with a guide on how to use the app's features.
 *
 * This screen organises instructions into clear sections within a vertically scrolling list.
 *
 * @param navController The navigation controller instance used to move back to the previous screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("App Guide") },
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
         * The main scrolling container displaying the instructional sections.
         */
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            item {
                HelpSection(
                    title = "Finding Recipes",
                    text = "Browse the 'Spotlight' carousel for daily recommendations or use the 'Browse all recipes' button for all the recipes. Use the search bar to find specific dishes or filter by category."
                )
            }
            item {
                HelpSection(
                    title = "Saving Favorites",
                    text = "Tap the heart icon on any recipe card to save it to your favourites. You can access your saved recipes quickly from the 'Favorites' tab at the bottom of the screen."
                )
            }
            item {
                HelpSection(
                    title = "Cooking Mode",
                    text = "Tap on a recipe to view its details. In the Cooking Mode screen, you can use the slider to adjust the number of servings, and the ingredient quantities will automatically update."
                )
            }
            item {
                HelpSection(
                    title = "Using the Timer",
                    text = "Every recipe detail screen has a built-in timer set to the recommended cooking time. Tap 'Start Timer' to begin the countdown. You can pause and resume it at any time."
                )
            }
            item {
                HelpSection(
                    title = "Dark Mode",
                    text = "Toggle between light and dark themes by tapping the sun/moon icon in the top right corner of the Home screen."
                )
            }
            // Adds some bottom padding
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

/**
 * A private composable function used to format and display a single section of instructional text.
 *
 * @param title The bold title for the instruction section.
 * @param text The detailed descriptive text for the instruction.
 */
@Composable
private fun HelpSection(title: String, text: String) {
    Column(modifier = Modifier.padding(vertical = 12.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}