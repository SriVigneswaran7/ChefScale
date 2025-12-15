package com.uog.expansionappredesign

/**
 * Represents a single ingredient required for a recipe.
 *
 * This data class is the fundamental building block for the recipe's structure (Model layer).
 * It uses a Double for the quantity to allow for fractional measurements
 * which is essential for the portion scaling feature.
 *
 * @property quantity The measurable amount of the ingredient (e.g., 200.0).
 * @property unit The unit of measurement (e.g., "g", "cup", "tsp").
 * @property name The name of the ingredient (e.g., "Flour", "Eggs").
 */
data class Ingredient(
    val quantity: Double, // Uses Double for math (e.g., 2.5 cups)
    val unit: String,
    val name: String
)