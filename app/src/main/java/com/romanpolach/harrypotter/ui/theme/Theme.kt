package com.romanpolach.harrypotter.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = GoldPrimary,
    onPrimary = Color.Black,
    primaryContainer = GoldDark,
    onPrimaryContainer = Color.White,
    secondary = Purple80,
    onSecondary = Color.Black,
    secondaryContainer = Purple40,
    onSecondaryContainer = Color.White,
    tertiary = Pink80,
    background = DarkBackground,
    onBackground = Color.White,
    surface = DarkSurface,
    onSurface = Color.White,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = Color(0xFFCAC4D0),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005)
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    onPrimary = Color.White,
    primaryContainer = Purple80,
    onPrimaryContainer = Color.Black,
    secondary = PurpleGrey40,
    onSecondary = Color.White,
    secondaryContainer = PurpleGrey80,
    onSecondaryContainer = Color.Black,
    tertiary = Pink40,
    background = LightBackground,
    onBackground = Color.Black,
    surface = LightSurface,
    onSurface = Color.Black,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = Color(0xFF49454F),
    error = Color(0xFFB3261E),
    onError = Color.White
)

@Composable
fun HarryPotterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}