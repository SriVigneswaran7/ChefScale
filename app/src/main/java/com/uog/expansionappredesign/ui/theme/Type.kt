package com.uog.expansionappredesign.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.uog.expansionappredesign.R

/**
 * Defines the custom font family used for all main headings and titles (Fjalla One).
 */
val TitleFont = FontFamily(
    Font(R.font.fjallaone_regular, FontWeight.Normal)
)

/**
 * Defines the custom font family used for all body text and content (Inter Tight).
 */
val BodyFont = FontFamily(
    Font(R.font.intertight, FontWeight.Normal)
)

/**
 * The main typography object for the app.
 *
 * This object customises the default Material 3 text styles using [TitleFont] and [BodyFont].
 */
val Typography = Typography(

    /**
     * Style for the App Bar Title and large section headers.
     */
    headlineLarge = TextStyle(
        fontFamily = TitleFont,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),

    /**
     * Style for Recipe Titles and primary screen headers.
     */
    titleLarge = TextStyle(
        fontFamily = TitleFont,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    /**
     * Style for Card Headings and smaller section titles (e.g., "Categories").
     */
    titleMedium = TextStyle(
        fontFamily = TitleFont,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    /**
     * Style for Main Body Content (e.g., Ingredient names, Recipe Steps).
     */
    bodyLarge = TextStyle(
        fontFamily = BodyFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    /**
     * Style for Secondary Body Text (e.g., smaller descriptions, quick info).
     */
    bodyMedium = TextStyle(
        fontFamily = BodyFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),

    /**
     * Style for Labels (e.g., Button text, Chip labels).
     */
    labelLarge = TextStyle(
        fontFamily = BodyFont,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),

    /**
     * Default Fallback style for small descriptive text.
     */
    bodySmall = TextStyle(
        fontFamily = BodyFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    )
)