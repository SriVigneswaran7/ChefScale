package com.uog.expansionappredesign

/**
 * A singleton object providing the static, hardcoded list of recipes for the app.
 *
 * Using a Kotlin object ensures that only a single instance of the recipe data exists
 * across the entire app, which is crucial for consistency.
 */
object RecipeData { // singleton (1 data for whole app)
    // I used mipmap for simplicity
    /**
     * The master list of all 16 recipes available in the app.
     */
    val recipes = listOf(
        Recipe(
            id = 1,
            title = "Spaghetti Carbonara",
            description = "A classic Italian pasta dish made with eggs, cheese, pancetta, and pepper.",
            imageResId = R.mipmap.spaghetti_carbonara,
            ingredients = listOf(
                Ingredient(200.0, "g", "Spaghetti"),
                Ingredient(100.0, "g", "Pancetta"),
                Ingredient(2.0, "large", "Eggs"),
                Ingredient(50.0, "g", "Pecorino")
            ),
            // Added placeholder steps
            steps = listOf(
                "Boil spaghetti in salted water until al dente. Reserve 1/2 cup pasta water, then drain.",
                "Meanwhile, fry pancetta in a large skillet until crispy. Remove pan from heat.",
                "In a bowl, whisk eggs, cheese, and lots of black pepper.",
                "Off heat, toss pasta in the skillet. Quickly stir in the egg mixture to create a creamy sauce. Serve immediately."
            ),
            // Added categories for filtering
            categories = listOf("Dinner", "Quick"),
            cookingTime = 20,

            ),
        Recipe(
            id = 2,
            title = "Chicken Tikka Masala",
            description = "Grilled chicken chunks enveloped in a creamy, spicy tomato sauce.",
            imageResId = R.mipmap.chicken_tikka_masala,
            ingredients = listOf(
                Ingredient(4.0, "whole", "Chicken Breasts, cubed"),
                Ingredient(1.0, "cup", "Yogurt (for marinade)"),
                Ingredient(1.0, "can (14oz)", "Crushed Tomatoes"),
                Ingredient(1.0, "cup", "Heavy Cream")
            ),
            steps = listOf(
                "Cut chicken into chunks. Coat thoroughly with yogurt and marinate for 20 minutes.",
                "Grill or pan-sear chicken over medium-high heat until charred on the outside. Set aside.",
                "Simmer crushed tomatoes in a pot for 10 minutes to thicken. Stir in heavy cream and bring to a gentle simmer.",
                "Add chicken back into the sauce. Simmer for 5-10 minutes until tender. Serve hot."
            ),
            categories = listOf("Dinner", "Spicy", "Meat"),
            cookingTime = 45,
        ),
        Recipe(
            id = 3,
            title = "Avocado Toast",
            description = "A simple and healthy breakfast option with mashed avocado on toast.",
            imageResId = R.mipmap.avocado_toast,
            ingredients = listOf(
                Ingredient(2.0, "slices", "Artisan Bread"),
                Ingredient(1.0, "large", "Avocado, ripe"),
                Ingredient(1.0, "tsp", "Lemon Juice")
            ),
            steps = listOf(
                "Toast the bread slices to your liking.",
                "Halve, pit, and scoop the avocado flesh into a bowl.",
                "Add lemon juice, salt, and pepper. Mash with a fork to desired consistency.",
                "Spread mashed avocado generously onto the toast. Add toppings if desired and serve."
            ),
            categories = listOf("Breakfast & Brunch", "Quick Prep", "Healthy", "Vegetarian"),
            cookingTime = 5,
        ),
        Recipe(
            id = 4,
            title = "Chicken Biriyani",
            description = "A fragrant Indian rice dish layered with spiced chicken, herbs, and saffron.",
            imageResId = R.mipmap.chicken_biriyani,
            ingredients = listOf(
                Ingredient(250.0, "g", "Chicken Thighs"),
                Ingredient(1.0, "cup", "Basmati Rice"),
                Ingredient(0.5, "cup", "Yogurt"),
                Ingredient(1.0, "large", "Onion, sliced"),
                Ingredient(2.0, "tbsp", "Biriyani Masala Spice"),
                Ingredient(1.0, "tbsp", "Ginger-Garlic Paste"),
                Ingredient(0.25, "cup", "Fresh Coriander/Mint")
            ),
            steps = listOf(
                "Marinate chicken with yogurt, spices, and ginger-garlic paste for 20 minutes.",
                "Par-boil the basmati rice until 70% cooked, then drain.",
                "Fry onions until golden brown. Cook the marinated chicken in the same pan until sealed.",
                "Layer the chicken and rice in a pot. Cover tightly and cook on very low heat (dum) for 20-25 minutes."
            ),
            categories = listOf("Main Dishes", "Meat"),
            cookingTime = 60
        ),
        Recipe(
            id = 5,
            title = "Fluffy Pancakes",
            description = "Thick and airy homemade pancakes, perfect for a weekend breakfast.",
            imageResId = R.mipmap.fluffy_pancakes,
            ingredients = listOf(
                Ingredient(1.0, "cup", "All-Purpose Flour"),
                Ingredient(1.0, "tbsp", "Sugar"),
                Ingredient(2.0, "tsp", "Baking Powder"),
                Ingredient(0.5, "tsp", "Salt"),
                Ingredient(0.75, "cup", "Milk"),
                Ingredient(1.0, "large", "Egg"),
                Ingredient(2.0, "tbsp", "Butter, melted")
            ),
            steps = listOf(
                "Whisk together flour, sugar, baking powder, and salt in a bowl.",
                "In a separate bowl, whisk milk, egg, and melted butter.",
                "Combine wet and dry ingredients, mixing until just combined (lumps are okay).",
                "Pour batter onto a hot, greased griddle. Flip when bubbles form on top and cook until golden."
            ),
            categories = listOf("Breakfast & Brunch", "Dessert & Sides"),
            cookingTime = 25
        ),
        Recipe(
            id = 6,
            title = "Overnight Oats",
            description = "A no-cook breakfast prepared the night before for a healthy grab-and-go meal.",
            imageResId = R.mipmap.overnight_oats,
            ingredients = listOf(
                Ingredient(1.0, "cup", "Rolled Oats"),
                Ingredient(1.0, "cup", "Milk (dairy or plant-based)"),
                Ingredient(0.25, "cup", "Greek Yogurt"),
                Ingredient(1.0, "tbsp", "Chia Seeds"),
                Ingredient(1.0, "tbsp", "Honey or Maple Syrup"),
                Ingredient(0.5, "cup", "Mixed Berries or fruit")
            ),
            steps = listOf(
                "Combine oats, milk, yogurt, chia seeds, and sweetener in a jar or bowl.",
                "Stir well to ensure all oats are submerged.",
                "Cover and refrigerate for at least 4 hours, preferably overnight.",
                "Stir and top with fresh fruit before serving."
            ),
            categories = listOf("Breakfast & Brunch", "Quick Prep", "Vegetarian"),
            cookingTime = 5
        ),
        Recipe(
            id = 7,
            title = "Simple Greek Salad",
            description = "A refreshing and crunchy salad with fresh vegetables, feta cheese, and olives.",
            imageResId = R.mipmap.simple_greek_salad,
            ingredients = listOf(
                Ingredient(1.0, "large", "Cucumber, diced"),
                Ingredient(2.0, "medium", "Tomatoes, diced"),
                Ingredient(0.5, "red", "Onion, thinly sliced"),
                Ingredient(0.5, "cup", "Kalamata Olives, pitted"),
                Ingredient(100.0, "g", "Feta Cheese, crumbled"),
                Ingredient(2.0, "tbsp", "Olive Oil"),
                Ingredient(1.0, "tsp", "Dried Oregano")
            ),
            steps = listOf(
                "Combine cucumber, tomatoes, onion, and olives in a large bowl.",
                "Drizzle with olive oil and toss gently to coat.",
                "Add crumbled feta cheese and sprinkle with dried oregano.",
                "Serve immediately or chill for 30 minutes to let flavours meld."
            ),
            categories = listOf("Main Dishes", "Quick Prep", "Vegetarian"),
            cookingTime = 15
        ),
        Recipe(
            id = 8,
            title = "Turkey Pesto Sandwich",
            description = "A flavourful sandwich loaded with turkey, pesto, provolone, and fresh veggies.",
            imageResId = R.mipmap.turkey_pesto_sandwich,
            ingredients = listOf(
                Ingredient(4.0, "slices", "Ciabatta or Sourdough Bread"),
                Ingredient(150.0, "g", "Sliced Turkey Breast"),
                Ingredient(4.0, "tbsp", "Basil Pesto"),
                Ingredient(2.0, "slices", "Provolone Cheese"),
                Ingredient(1.0, "cup", "Arugula or Spinach"),
                Ingredient(1.0, "small", "Tomato, sliced")
            ),
            steps = listOf(
                "Spread pesto generously on one side of each bread slice.",
                "Layer turkey, cheese, tomato slices, and arugula on two of the bread slices.",
                "Top with the remaining bread slices.",
                "Serve fresh or grill in a panini press until cheese is melted."
            ),
            categories = listOf("Main Dishes", "Quick Prep"),
            cookingTime = 10
        ),
        Recipe(
            id = 9,
            title = "Lentil Soup",
            description = "A hearty and nutritious vegetarian soup packed with lentils and vegetables.",
            imageResId = R.mipmap.lentil_soup,
            ingredients = listOf(
                Ingredient(1.0, "cup", "Brown or Green Lentils, rinsed"),
                Ingredient(4.0, "cups", "Vegetable Broth"),
                Ingredient(1.0, "medium", "Onion, diced"),
                Ingredient(2.0, "medium", "Carrots, diced"),
                Ingredient(2.0, "stalks", "Celery, diced"),
                Ingredient(2.0, "cloves", "Garlic, minced"),
                Ingredient(1.0, "tsp", "Cumin"),
                Ingredient(1.0, "can (14oz)", "Crushed Tomatoes")
            ),
            steps = listOf(
                "Sauté onion, carrots, and celery in a large pot until softened. Add garlic and cumin, cook for 1 minute.",
                "Add lentils, broth, and crushed tomatoes to the pot.",
                "Bring to a boil, then reduce heat and simmer for 25-30 minutes until lentils are tender.",
                "Season with salt and pepper to taste. Serve warm."
            ),
            categories = listOf("Main Dishes", "Vegetarian"),
            cookingTime = 45
        ),
        Recipe(
            id = 10,
            title = "Beef Tacos",
            description = "Classic ground beef tacos with homemade seasoning, perfect for a weeknight meal.",
            imageResId = R.mipmap.beef_tacos,
            ingredients = listOf(
                Ingredient(250.0, "g", "Ground Beef"),
                Ingredient(1.0, "tbsp", "Taco Seasoning"),
                Ingredient(0.5, "cup", "Water"),
                Ingredient(4.0, "small", "Corn or Flour Tortillas"),
                Ingredient(0.5, "cup", "Shredded Lettuce"),
                Ingredient(0.5, "cup", "Shredded Cheddar Cheese"),
                Ingredient(0.25, "cup", "Salsa")
            ),
            steps = listOf(
                "Brown the ground beef in a skillet over medium-high heat. Drain excess fat.",
                "Stir in taco seasoning and water. Simmer for 5 minutes until sauce thickens.",
                "Warm the tortillas in a dry pan or microwave.",
                "Assemble tacos by filling tortillas with beef, lettuce, cheese, and salsa."
            ),
            categories = listOf("Main Dishes", "Meat"),
            cookingTime = 20
        ),
        Recipe(
            id = 11,
            title = "Lemon Garlic Salmon",
            description = "Tender oven-baked salmon fillets with a bright and savoury lemon-garlic butter sauce.",
            imageResId = R.mipmap.lemon_garlic_salmon,
            ingredients = listOf(
                Ingredient(2.0, "fillets (6oz each)", "Salmon"),
                Ingredient(3.0, "tbsp", "Butter, melted"),
                Ingredient(2.0, "cloves", "Garlic, minced"),
                Ingredient(1.0, "tbsp", "Fresh Parsley, chopped"),
                Ingredient(1.0, "large", "Lemon, sliced into rounds")
            ),
            steps = listOf(
                "Preheat oven to 375°F (190°C). Line a baking sheet with foil.",
                "In a small bowl, mix melted butter, minced garlic, and parsley.",
                "Place salmon fillets on the baking sheet. Brush generously with the butter mixture.",
                "Top each fillet with lemon slices and bake for 12-15 minutes until cooked through and flaky."
            ),
            categories = listOf("Main Dishes", "Seafood","Bake"),
            cookingTime = 20
        ),
        Recipe(
            id = 12,
            title = "Easy Chicken Stir-Fry",
            description = "A quick and healthy weeknight meal with protein, veggies, and a savoury sauce.",
            imageResId = R.mipmap.easy_chicken_stir_fry,
            ingredients = listOf(
                Ingredient(250.0, "g", "Chicken Breast, cubed"),
                Ingredient(2.0, "cups", "Mixed Stir-Fry Vegetables (broccoli, bell peppers, snap peas)"),
                Ingredient(1.0, "tbsp", "Vegetable Oil"),
                Ingredient(0.25, "cup", "Soy Sauce"),
                Ingredient(1.0, "tbsp", "Honey"),
                Ingredient(1.0, "tsp", "Sesame Oil"),
                Ingredient(1.0, "tbsp", "Cornstarch, mixed with 2 tbsp water")
            ),
            steps = listOf(
                "Whisk together soy sauce, honey, sesame oil, and cornstarch slurry in a small bowl.",
                "Heat oil in a large wok or skillet. Stir-fry chicken until browned and cooked through. Remove from pan.",
                "Add vegetables to the pan and stir-fry for 3-4 minutes until tender-crisp.",
                "Return chicken to the pan, pour in the sauce, and toss everything together until the sauce thickens and coats the ingredients."
            ),
            categories = listOf("Main Dishes", "Quick Prep", "Meat"),
            cookingTime = 20
        ),
        Recipe(
            id = 13,
            title = "Black Bean Burgers",
            description = "Hearty homemade vegetarian patties made with black beans, veggies, and spices.",
            imageResId = R.mipmap.black_bean_burgers,
            ingredients = listOf(
                Ingredient(1.0, "can (15oz)", "Black Beans, rinsed and drained"),
                Ingredient(0.5, "cup", "Breadcrumbs"),
                Ingredient(0.25, "cup", "Onion, finely chopped"),
                Ingredient(1.0, "large", "Egg"),
                Ingredient(1.0, "tbsp", "Cumin"),
                Ingredient(1.0, "tsp", "Garlic Powder"),
                Ingredient(2.0, "whole", "Burger Buns")
            ),
            steps = listOf(
                "Mash black beans in a large bowl with a fork, leaving some texture.",
                "Stir in breadcrumbs, onion, egg, cumin, garlic powder, salt, and pepper until combined.",
                "Form the mixture into 2 patties.",
                "Cook patties in a greased skillet over medium heat for 4-5 minutes per side until heated through and slightly crisp."
            ),
            categories = listOf("Main Dishes", "Vegetarian"),
            cookingTime = 25
        ),
        Recipe(
            id = 14,
            title = "Classic Hummus",
            description = "A smooth and creamy dip made from blended chickpeas, tahini, lemon, and garlic.",
            imageResId = R.mipmap.classic_hummus,
            ingredients = listOf(
                Ingredient(1.0, "can (15oz)", "Chickpeas, drained (reserve liquid)"),
                Ingredient(0.25, "cup", "Tahini"),
                Ingredient(0.25, "cup", "Lemon Juice, fresh"),
                Ingredient(2.0, "cloves", "Garlic"),
                Ingredient(2.0, "tbsp", "Olive Oil"),
                Ingredient(1.0, "tsp", "Cumin")
            ),
            steps = listOf(
                "Add chickpeas, tahini, lemon juice, garlic, olive oil, and cumin to a food processor.",
                "Blend for 1 minute, scrape down sides, and blend again.",
                "With the motor running, slowly stream in 2-4 tablespoons of reserved chickpea liquid until smooth and creamy.",
                "Serve drizzled with olive oil and dusted with paprika."
            ),
            categories = listOf("Dessert & Sides", "Vegetarian"),
            cookingTime = 10
        ),
        Recipe(
            id = 15,
            title = "Easy Guacamole",
            description = "A simple and fresh Mexican dip made with ripe avocados, lime, onion, and cilantro.",
            imageResId = R.mipmap.easy_guacamole,
            ingredients = listOf(
                Ingredient(2.0, "large", "Avocados, ripe"),
                Ingredient(1.0, "tbsp", "Lime Juice, fresh"),
                Ingredient(0.25, "cup", "Red Onion, finely chopped"),
                Ingredient(2.0, "tbsp", "Fresh Cilantro, chopped"),
                Ingredient(1.0, "small", "Jalapeño, seeded and minced (optional)"),
                Ingredient(0.5, "tsp", "Salt")
            ),
            steps = listOf(
                "Cut avocados in half, remove pit, and scoop flesh into a bowl.",
                "Add lime juice and salt. Mash with a fork to your desired consistency (chunky or smooth).",
                "Stir in chopped onion, cilantro, and jalapeño if using.",
                "Taste and add more salt or lime juice if needed. Serve immediately with chips."
            ),
            categories = listOf("Dessert & Sides", "Quick Prep", "Vegetarian"),
            cookingTime = 10
        ),
        Recipe(
            id = 16,
            title = "Chocolate Chip Cookies",
            description = "Soft and chewy classic cookies loaded with semi-sweet chocolate chips.",
            imageResId = R.mipmap.chocolate_chip_cookies,
            ingredients = listOf(
                Ingredient(1.5, "cups", "All-Purpose Flour"),
                Ingredient(0.5, "cup", "Unsalted Butter, softened"),
                Ingredient(0.5, "cup", "Brown Sugar, packed"),
                Ingredient(0.25, "cup", "Granulated Sugar"),
                Ingredient(1.0, "large", "Egg"),
                Ingredient(1.0, "tsp", "Vanilla Extract"),
                Ingredient(0.5, "tsp", "Baking Soda"),
                Ingredient(1.0, "cup", "Semi-Sweet Chocolate Chips")
            ),
            steps = listOf(
                "Preheat oven to 375°F (190°C). Line baking sheets with parchment paper.",
                "In a large bowl, beat softened butter and sugars until light and fluffy. Beat in egg and vanilla.",
                "Gradually blend in flour, baking soda, and salt until dough forms. Fold in chocolate chips.",
                "Drop rounded tablespoons of dough onto baking sheets. Bake for 9-11 minutes until edges are golden brown."
            ),
            categories = listOf("Dessert & Sides", "Bake"),
            cookingTime = 30
        )
    )

    /**
     * Calculates and returns a distinct, sorted list of all categories across every recipe.
     * This list is used to generate the category chips on the home screen.
     *
     * @return A list of unique category strings, sorted alphabetically.
     */
    val allCategories = recipes.flatMap { it.categories }.distinct().sorted()
}