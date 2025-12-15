package com.uog.expansionappredesign

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * A reusable Composable component that displays a summary of a recipe.
 *
 * This card dynamically changes its layout (Spotlight or Standard List) and elevation
 * based on the [isSpotlight] flag. It also includes the persistent favourite toggle icon.
 *
 * @param recipe The [Recipe] object containing the data to display.
 * @param isFavorite True if the recipe is currently saved as a favourite.
 * @param onToggleFavorite Lambda function invoked when the favourite icon is clicked.
 * @param onClick Lambda function invoked when the card itself is clicked to view details.
 * @param isSpotlight If true, uses the larger layout for carousels; otherwise, uses the compact list layout.
 * @param modifier Standard Compose modifier for customising layout.
 */
@Composable
fun RecipeCard(
    recipe: Recipe,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onClick: () -> Unit,
    isSpotlight: Boolean = false, // Controls if it's the big Home Screen version or list version
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        // Spotlight gets more elevation to pop out
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSpotlight) 8.dp else 4.dp)
    ) {
        Box {
            // Base layout depends on flag
            if (isSpotlight) {
                SpotlightLayout(recipe)
            } else {
                StandardListLayout(recipe)
            }

            // Favourite Heart Icon - Always top right corner
            IconButton(
                onClick = onToggleFavorite,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(50))
                    .size(36.dp)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Toggle Favourite",
                    tint = if (isFavorite) Color.Red else Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

/**
 * The layout for the large, prominent recipe card used in the Home Screen carousel.
 *
 * This layout features a full-bleed image with overlaid text and a gradient for readability.
 *
 * @param recipe The [Recipe] data to display.
 */
@Composable
fun SpotlightLayout(recipe: Recipe) {
    Box(modifier = Modifier.height(250.dp).fillMaxWidth()) {
        Image(
            painter = painterResource(id = recipe.imageResId),
            contentDescription = recipe.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        // Dark gradient overlay so white text is readable
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                        startY = 100f
                    )
                )
        )
        // Overlaid Text
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text("CHEF'S RECOMMENDATION", style = MaterialTheme.typography.labelSmall, color = Color.Yellow)
            Text("Recipe Spotlight", style = MaterialTheme.typography.labelMedium, color = Color.White)
            Text(recipe.title, style = MaterialTheme.typography.titleLarge, color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

/**
 * The compact, side-by-side layout for recipes used in the Browse and Search lists.
 *
 * @param recipe The [Recipe] data to display.
 */
@Composable
fun StandardListLayout(recipe: Recipe) {
    Row(modifier = Modifier.padding(12.dp)) {
        Image(
            painter = painterResource(id = recipe.imageResId),
            contentDescription = recipe.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = recipe.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = recipe.description,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

/**
 * A reusable component to show when a list or view is empty (e.g., no search results, no favourites).
 *
 * This component centres a message vertically and horizontally for clear user feedback.
 *
 * @param icon The icon to display in the centre.
 * @param title The main header text of the message.
 * @param message The supporting descriptive text.
 * @param modifier Standard Compose modifier for customising layout.
 */
@Composable
fun EmptyStateMessage(
    icon: ImageVector,
    title: String,
    message: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(64.dp).padding(bottom = 16.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * A preview recipe ingredient used only for display purposes in the Android Studio preview pane.
 */
val previewIngredient = Ingredient(200.0, "g", "Pasta")

/**
 * A preview recipe used only for display purposes in the Android Studio preview pane.
 */
val previewRecipe = Recipe(
    id = 99,
    title = "Spaghetti Carbonara",
    description = "A delicious...",
    imageResId = R.mipmap.spaghetti_carbonara,
    ingredients = listOf(previewIngredient),
    steps = listOf("Step 1: ", "Step 2: "),
    categories = listOf("Dinner", "Quick"),
    cookingTime = 20
)

/**
 * Preview demonstrating the large, spotlight card design (Home Screen style).
 */
@Preview(showBackground = true, name = "1. Spotlight Card", widthDp = 350, heightDp = 300)
@Composable
fun RecipeCardSpotlightPreview() {
    // Wrap in MaterialTheme so fonts and colours look right
    MaterialTheme {
        RecipeCard(
            recipe = previewRecipe,
            isFavorite = false, // Test with heart empty
            onToggleFavorite = {}, // Empty action
            onClick = {}, // Empty action
            isSpotlight = true
        )
    }
}

/**
 * Preview demonstrating the compact list card design (Browse Screen style).
 */
@Preview(showBackground = true, name = "2. Standard List Card", widthDp = 350)
@Composable
fun RecipeCardStandardPreview() {
    MaterialTheme {
        RecipeCard(
            recipe = previewRecipe,
            isFavorite = true, // Test with heart FULL (red)
            onToggleFavorite = {},
            onClick = {},
            isSpotlight = false // Standard mode layout
        )
    }
}

/**
 * Preview demonstrating the component in Dark Mode for visual testing.
 */
@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES, name = "3. Dark Mode Standard")
@Composable
fun RecipeCardDarkPreview() {
    // Wrapping the app's theme to test custom dark colours.
    com.uog.expansionappredesign.ui.theme.ChefScaleTheme(darkTheme = true) {
        Surface {
            RecipeCard(
                recipe = previewRecipe,
                isFavorite = false,
                onToggleFavorite = {},
                onClick = {},
                isSpotlight = false
            )
        }
    }
}