package com.uog.expansionappredesign

/**
 * The blueprint for a complete recipe, representing the core entity in the Model layer.
 *
 * This data class includes fields necessary for the app's advanced features,
 * such as categorisation for filtering and cooking time for the multithreaded timer.
 *
 * @property id A unique integer identifier for the recipe.
 * @property title The full name of the recipe (e.g., "Spaghetti Carbonara").
 * @property description A short summary of the dish.
 * @property ingredients A list of [Ingredient] objects required for the recipe.
 * @property steps A list of strings detailing the cooking instructions.
 * @property categories A list of strings used for filtering and displaying category chips (e.g., "Dinner", "Quick").
 * @property cookingTime The estimated cooking time in minutes, used to set the timer duration.
 * @property imageResId The Android resource ID for the image associated with the recipe.
 */
data class Recipe(
    val id: Int,
    val title: String,
    val description: String,
    val ingredients: List<Ingredient>,
    // Steps for the detail screen
    val steps: List<String>,
    // Categories for chips and filtering
    val categories: List<String>,
    val cookingTime: Int,
    val imageResId: Int
)