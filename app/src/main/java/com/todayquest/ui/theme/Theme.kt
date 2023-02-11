package com.todayquest.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColorScheme(
    primary = DarkBlue,
    onPrimary = White,
    inversePrimary = White,
    secondary = LightBlue,
    onSecondary = White,
    tertiary = MiddleBlue,
    onTertiary = White,
    surface = DarkBlue,
    onSurface = White,
    background = Black,
    onBackground = White
)

private val LightColorPalette = lightColorScheme(
    primary = LightBlue,
    onPrimary = White,
    inversePrimary = White,
    secondary = DarkBlue,
    onSecondary = White,
    tertiary = MiddleBlue,
    onTertiary = White,
    surface = LightBlue,
    onSurface = White,
    background = White,
    onBackground = Black
)

@Composable
fun TodayQuestTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}