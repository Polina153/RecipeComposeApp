package com.example.recipecomposeapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val RecipesAppDarkColorScheme = darkColorScheme(
    primary = PrimaryColorDark,
    secondary = TextSecondaryColorDark,
    tertiary = AccentBlueDark,
    background = BackgroundColorDark,
    surface = SurfaceColorDark,
    surfaceVariant = SurfaceVariantColorDark,
    error = AccentColor,
    onBackground = TextSecondaryColorDark,
    onSurface = TextSecondaryColorDark,
    onSurfaceVariant = TextSecondaryColorDark
)

private val RecipesAppLightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    onPrimary = Color.White,  // ← текст/иконки на primary
    secondary = TextSecondaryColor,
    onSecondary = Color.White,  // ← текст на secondary
    tertiary = AccentBlue,
    onTertiary = Color.White,
    error = AccentColor,  // ← красный для избранного (как в уроке)
    onError = Color.White,
    background = BackgroundColor,
    onBackground = TextPrimaryColor,  // ← основной текст на фоне
    surface = SurfaceColor,
    onSurface = TextPrimaryColor,  // ← основной текст на surface
    surfaceVariant = SurfaceVariantColor,
    onSurfaceVariant = TextSecondaryColor  // ← второстепенный текст

)

@Composable
fun RecipeComposeAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        RecipesAppDarkColorScheme
    } else {
        RecipesAppLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}