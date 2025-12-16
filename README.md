# Mobile App Project - ChefScale

**Author:** Sri Vigneswaran Arumugaraj

**Student ID:** 001419849

---

## Overview

This mobile app, **ChefScale**, is a **significant redesign and expansion** of a core module lab app (**HealthyRecipes**), transforming the base app into a functional kitchen utility tool. The primary problem I set out to solve was the user frustration of manually scaling recipe ingredients when adjusting serving sizes.

ChefScale's core value is its **real-time ingredient portion scaling** for 16 diverse recipes, complemented by a robust **multithreaded cooking timer** for task management. My goal was to demonstrate technical competence in applying **Object-Oriented Design (MVVM)**, creating a professional **Jetpack Compose UI**, and mastering **asynchronous processing** using Kotlin Coroutines.

---

## Features

This project was built to show a substantial expansion, adding layers of complexity and value beyond the starter lab app.

| Feature | Core Functionality |
| ------- | ------------------ |
| **Real-Time Portion Scaling** | Dynamically calculates ingredient quantities based on the user-controlled serving slider (1–10 servings). |
| **Multithreaded Cooking Timer** | A background countdown timer using `viewModelScope` and `Job` control, ensuring zero UI freezing. |
| **Favourites Section** | Users can save recipes to a private list using ViewModel state for quick access. |
| **Optimised Search & Filter** | Allows users to search by text and filter by category chips, improving content discoverability. |
| **Adaptive UI & Stability** | The UI maintains state and prevents crashes when the device is rotated (Orientation Change). |
| **Custom Theming** | Supports light/dark mode based on system settings and features custom typography for a branded, unique look. |

---

## Architecture & Design

I implemented the **MVVM (Model-View-ViewModel)** architectural pattern to create a solution that is maintainable and extensible, fully demonstrating Object-Oriented Design principles.

* **Architecture pattern:** MVVM (Model-View-ViewModel).
* **Concurrency:** Handled using **Kotlin Coroutines** (detailed below).

**Note on Project Timeline:** I started this building this app in the same project that I created to build the lab app as per the instructions in the workbook. Hence, the core codebase was developed in the previous project environment (`com.uog.ChefScale`) before being copied into this final repository (`com.uog.expansionappredesign`). This constraint means the commits provided are condensed and do not reflect the actual time spent developing the app. Crucially, the code maintains a fully separated and decoupled MVVM architecture in this final package structure.

### File Structure (OOD Separation):

The architecture is reinforced by a clear file structure separating concerns:

**`model`**: Contains all Data Classes (`Recipe.kt`, `Ingredient.kt`) and Data Sources (`RecipeData.kt`).

**`viewmodel`**: Contains the single point of state management (`RecipeViewModel.kt`).

**`ui/screen`**: Houses all Composable View elements (e.g., `HomeScreen.kt`, `RecipeScreen.kt`).

**`ui/theme`**: Dedicated to branding elements (`Theme.kt`, `Type.kt`).

---

### Key Implementations: Core Logic (`RecipeViewModel.kt`)

#### 1. Portion Scaling Logic

This logic calculates the necessary factor based on the default servings (assumed to be 2) and the user's selected servings. This calculation happens in real-time on the `RecipeScreen.kt` view layer.

```kotlin
// RecipeScreen.kt (Calculation of new quantity)
val factor = servings.toDouble() / 2.0
val newQuantity = ingredient.quantity * factor
```

### 2. Favourites System Logic (Encapsulation)

The favourites list is managed internally by the ViewModel using a private mutable state set and exposed as read-only, demonstrating proper encapsulation (LO1).

```kotlin
// RecipeViewModel.kt (Encapsulation)
private var _favoriteRecipeIds = mutableStateOf<Set<Int>>(emptySet())
val favoriteRecipeIds: Set<Int> get() = _favoriteRecipeIds.value
```

#### 3. Search and Filter Logic

The `updateSearch` function implements complex boolean filtering to check if a recipe matches the text query **AND** the currently selected category chip.

```kotlin
// RecipeViewModel.kt (Core Filtering Logic)
filteredRecipes = allRecipes.filter { recipe ->
  val matchesQuery = recipe.title.contains(query, ignoreCase = true) //...
  val matchesCategory = category == null || recipe.categories.contains(category)
  matchesQuery && matchesCategory
}
```

#### 4. Dark Mode Toggle Logic

The theme toggle is managed by a simple boolean state in the ViewModel, which is passed to the `ChefScaleTheme` in `MainActivity.kt`.

```kotlin
// RecipeViewModel.kt (Theme State Logic)
var isDarkTheme by mutableStateOf(false)
private set
fun toggleTheme() {
  isDarkTheme = !isDarkTheme
}
```

#### 5. Navigation Argument Retrieval (Null Safety)

Retrieving dynamic arguments from the navigation stack requires careful handling of nulls. This snippet from `MainActivity.kt` shows how the Recipe ID is safely handled.

```kotlin
// MainActivity.kt (Recipe Detail Navigation)
composable("recipe_detail/{recipeId}") { backStackEntry ->
  val recipeId = backStackEntry.arguments?.getString("recipeId")?.toIntOrNull() ?: 1
  // ... proceed with non-null recipeId
}
```

#### 6. State Delegation (Compose Idiom)

State variables like `currentServings` are managed using the Kotlin property delegate, a fundamental idiom for clean Compose development.

```kotlin
// RecipeViewModel.kt (State Delegation)
var currentServings by mutableStateOf(2)
private set
```

#### 7. Ingredient Formatting (Readability)

An extension function is used to ensure all dynamically scaled ingredient quantities are displayed cleanly with a maximum of one decimal place in the UI.

```kotlin
// Double Extension (Ensures clean UI display for scaling)
fun Double.toOneDecimalPlace(): String = "%.1f".format(this)
```

---

## UI / UX Design

I consciously employed Material 3 components and advanced Compose features to ensure the app is professional, user-centric, and highly polished. I also took inspiration from food delivery apps (e.g., Swiggy, Zomato) which helped me to visualise the colourful canvas of my app.

* **Custom Typography:** I introduced two custom fonts, **Fjalla One** (for titles) and **Inter Tight** (for body text), by defining the custom `Typography` object in `ui.theme/Type.kt` to enhance branding and legibility.
* **Layout Responsiveness:** The UI uses scalable composables and modifiers. All content, including the detail screen, is vertical scrolling (`Modifier.verticalScroll(rememberScrollState())`) to prevent content overflow.

### Compose Features Used:

* **State Preservation on Orientation Change (Adaptive UI):** The application is robust against device rotation. All dynamic state (like the current serving size or the timer countdown) is hosted within the retained `ViewModel`, ensuring the UI can be recreated without crashing or losing data.
  ```kotlin
  // In MainActivity.kt, the ViewModel is retained across configuration changes:
  val viewModel: RecipeViewModel = viewModel()
  ```
* **Gradient Overlay:** I used the `Brush.verticalGradient` (in `SharedUIComponents.kt`) to apply a smooth, professional gradient fade underneath the text, resolving contrast issues on bright images.
* **Home Screen Layout:** Features a **two-row** `LazyRow` split using `.take()` and `.drop()` for efficient category browsing.

### App ScreenShots:

The following table provides a visual overview of the application, demonstrating the professional, material-driven UI implemented across all six core screens

| Home Screen | Help Screen | RecipeList Screen |
|-------------|-------------|-------------------|
|![](https://github.com/UniOfGreenwich/expansion-redesign-app-SriVigneswaran7/blob/main/HomeScreen.png?raw=true) | ![](https://github.com/UniOfGreenwich/expansion-redesign-app-SriVigneswaran7/blob/main/HelpScreen.png?raw=true) | ![](https://github.com/UniOfGreenwich/expansion-redesign-app-SriVigneswaran7/blob/main/RecipeListScreen.png?raw=true) |

| Favourites Screen | Search Screen | Recipe Screen |
|---------------|---------------|-------------------|
| ![](https://github.com/UniOfGreenwich/expansion-redesign-app-SriVigneswaran7/blob/main/FavouritesScreen.png?raw=true) | ![](https://github.com/UniOfGreenwich/expansion-redesign-app-SriVigneswaran7/blob/main/SearchScreen.png?raw=true) | ![](https://github.com/UniOfGreenwich/expansion-redesign-app-SriVigneswaran7/blob/main/RecipeScreen.png?raw=true) |

---

## Technologies Used

* **Kotlin:** The primary programming language, utilising modern idioms for clean and concise code.
* **Jetpack Compose (Material 3):** The modern, declarative UI toolkit used for building the entire user interface.
* **Kotlin Coroutines (`viewModelScope`, `Job`):** Used for structured concurrency, specifically powering the non-blocking, multithreaded cooking timer.
* **ViewModel / AndroidViewModel:** The core component of the MVVM architecture, managing all application state and business logic.
* **Compose Navigation:** Manages the routing between the app's six different screens.
* **Android Media (`RingtoneManager`):** Used to access system services to trigger the audible alert when the cooking timer completes.
* **Material Design 3:** Provides the underlying theming, colour system, and component design principles.

---

## Multithreading / Background Tasks

I implemented a robust **Cooking Timer** feature in `RecipeViewModel.kt` using structured coroutines to satisfy the requirement for meaningful background processing. The entire countdown runs off the main thread, preventing the UI from freezing.

### Rationale for AndroidViewModel

The `AndroidViewModel` was specifically required to handle the final timer action. It allows the `RecipeViewModel.kt` to securely access the necessary Application Context needed to integrate with the system service (`RingtoneManager`) and trigger the audible timer alert, demonstrating secure integration with Android APIs.

### 1. Coroutine Control (Job and Lifecycle)

The timer function, `toggleTimer()`, is responsible for starting the background countdown, pausing it, and resuming it. It achieves reliable concurrency control by managing a coroutine `Job`, ensuring only one timer instance is active at any time.

```kotlin
// RecipeViewModel.kt (Start/Cancel Logic)
timerJob?.cancel() // CRITICAL: Stops any previous/concurrent job
timerJob = viewModelScope.launch {
  // Coroutine runs in the background
  while (timerState.secondsRemaining > 0) {
    delay(1000) // Pauses the coroutine for 1 second
    // State updates trigger UI changes
  }
}
```

### 2. System Service Integration (Timer Alert)

The final action of the timer involves triggering an audible alert using the system's `RingtoneManager`. This background execution requires access to the system, which necessitates adding the required permission to the application's manifest.

```kotlin
// AndroidManifest.xml (Required Permission for System Alert)
<uses-permission android:name="android.permission.VIBRATE"/>
```

---

## How to Build & Run

1.  **Clone this repository:**
    ```bash
    git clone git@github.com:uniofgreenwich/ELEE1146_CW_SriVigneswaran7
    ```
2.  **Open the project** in Android Studio Giraffe or newer.
3.  Connect a device or launch an emulator.
4.  Click **Run**.

---

# References

* **Jetpack Compose Official Documentation:** ([https://developer.android.com/jetpack/compose](https://developer.android.com/jetpack/compose))
  * *Used extensively for core UI implementation, state observation techniques, and compositional best practices.*
* **Kotlin Coroutines Documentation:** ([https://kotlinlang.org/docs/coroutines-overview.html](https://kotlinlang.org/docs/coroutines-overview.html))
  * *Used for understanding asynchronous processing concepts, lifecycle control, and `Job` management for the cooking timer.*
* **Material Design 3 Guidelines:** ([https://m3.material.io/](https://m3.material.io/))
  * *Used for principles applied in theming, colour usage, typography scaling, and overall component design.*
* **Android Developers Guide: `RingtoneManager`:** ([https://developer.android.com/reference/android/media/RingtoneManager](https://developer.android.com/reference/android/media/RingtoneManager))
  * *Used for system service integration required for the timer alert.*
* **Font Sources and Licensing:**
  * **Google Fonts:** Source for Fjalla One and Inter Tight fonts.
  * **Licence:** Both fonts are covered by the SIL Open Font License (OFL), permitting free use, modification, and distribution in software.

---

## Acknowledged AI Assistance

Generative AI tools (e.g., ChatGPT) were used as assistive aids in the development of this project. All AI-generated content was reviewed and modified where necessary, supporting my understanding without replacing it.

| # | Component | Prompts Used | My Understanding Gained |
| - | --------- | ------------ | ----------------------- |
| 1 | Data Population | "Generate 16 diverse recipes including ingredients and steps in this specific Kotlin data class format." | Saved significant time on content creation, allowing me to focus on high-value implementation tasks. |
| 2 | Notification Context | "How to play the default notification sound using `RingtoneManager` from within an `AndroidViewModel`?" | Highlighted the need for the **Application Context** and the **VIBRATE permission** in the `AndroidManifest.xml` for system service access. |
| 3 | Timer Concurrency Bug | "Why does my countdown speed up when I pause and resume using Kotlin Coroutines?" | Diagnosed the race condition and taught me to use `timerJob?.cancel()` to ensure only one coroutine runs at a time. |
| 4 | Null Safety | "How to safely handle nullable types and provide a fallback value in Kotlin when retrieving navigation arguments?" | Learned to use the **Elvis Operator (?:)** to elegantly and safely assign a default value, improving code robustness. |
| 5 | Gradient Text Overlay | "How to add a smooth black gradient fade to the bottom of an image in Jetpack Compose to improve text readability?" | Introduced the powerful **Brush.verticalGradient** feature, providing a professional solution to a complex UI contrast problem. |
| 6 | Search Filter Reset | "How can I reset the text in a ViewModel's `searchQuery` when a category filter is clicked in a different screen?" | Learned to call `viewModel.updateSearch("", category)` to clear the query text while applying the new filter, solving a key logic conflict. |
| 7 | Ingredient Formatting | "How do I format a Double variable to one decimal place in Kotlin?" | Provided the concise extension function syntax using `"%.${digits}f".format(this)`, ensuring a clean UI display for the scaling feature. |
| 8 | State Delegation | "What is the correct way to use `mutableStateOf` inside a ViewModel so I can read it with an observer?" | Clarified the usage of the Kotlin `by` property delegate (e.g., `var servings by mutableStateOf(2)`) for state, which is the idiomatic Compose pattern. |
| 9 | Multi-Row Category Layout | "How can I easily split a large Kotlin list into two equal parts for different horizontal lists in Compose?" | Suggested using the list functions `.take(6)` and `.drop(6)` for efficient data management for the two `LazyRow` components. |
| 10 | Icon Alignment in TopBar | "How do I put an image and text in a row inside the `CenterAlignedTopAppBar` title slot in Compose?" | Showed how to wrap both components inside a `Row(verticalAlignment = Alignment.CenterVertically)` for perfect alignment and polish. |
| 11 | Button Shape | "How can I give a standard Material 3 button rounded corners in Jetpack Compose?" | Provided the syntax to apply `shape = RoundedCornerShape(12.dp)` directly to the button composables, enhancing the app's modern aesthetics. |
| 12 | Compose Preview Context | "Why do my composables crash in the preview window when they use a ViewModel or Navigation?" | Diagnosed the issue, emphasising that Compose Previews require a mocked or default parameter for components that rely on LocalContext or a ViewModel factory. |
| 13 | List Index Protection | "How to safely handle fetching a recipe by ID from a list without risking an `IndexOutOfBoundsException` in Kotlin?" | Learned to use the safer collection function `allRecipes.firstOrNull { it.id == recipeId }` and handle the nullable result with the Elvis operator, improving data retrieval robustness. |
---

## Licence

Educational Community License 2.0 [ECL]

---

## Future Improvements

I have reflected on several areas where the application could be further enhanced to meet or exceed industry standards if time permitted:

- **Persistent Data Storage (Room/DataStore):** Implementing a database solution would allow user preferences (favorites and dark mode settings) to persist across sessions.

- **Dedicated Notification Channel:** Creating a proper Android Notification Channel for the timer alert would give users fine-grained control over the alert sound and importance.

- **Haptic Feedback:** Adding subtle haptic feedback using the VIBRATE service for key interactions, such as adding a recipe to favorites.

- **Dynamic Theming:** Allowing the user to select an accent colour that overrides the default Material 3 palette.

- **Deep Linking:** Implementing deep links so external shared recipe links navigate directly to the correct detail screen.

---