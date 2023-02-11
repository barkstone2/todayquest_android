package com.todayquest.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily
import com.todayquest.R

val textStyle = TextStyle(fontFamily = Font(R.font.maplestory_light).toFontFamily())
val Typography = Typography(
    displayLarge = textStyle,
    displayMedium = textStyle,
    displaySmall = textStyle,
    headlineLarge = textStyle,
    headlineMedium = textStyle,
    headlineSmall = textStyle,
    titleLarge = textStyle,
    titleMedium = textStyle,
    titleSmall = textStyle,
    bodyLarge = textStyle,
    bodyMedium = textStyle,
    bodySmall = textStyle,
    labelLarge = textStyle,
    labelMedium = textStyle,
    labelSmall = textStyle,
)