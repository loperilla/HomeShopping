package io.loperilla.core_ui

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

//Color.Yellow
private val DarkColorScheme = darkColorScheme(
    background = AzulMarino,
    primary = GrutaAzul,
    onPrimary = AzulMarino,
    primaryContainer = AzulMarino,
    onPrimaryContainer = AzulMarino,
    inversePrimary = CuarzoRosaContent,
    secondary = AzulVerde,
    onSecondary = AzulBebe,
    secondaryContainer = AzulBebe,
    onSecondaryContainer = AzulBebe,
    tertiary = AzulBebe,
    onTertiary = GrutaAzul,
    tertiaryContainer = GrutaAzul,
    onTertiaryContainer = GrutaAzul,
    surface = AzulMarino,
    onSurface = GrutaAzul,
    error = Error,
    onError = Error.copy(
        alpha = 0.7f
    ),
    errorContainer = Error.copy(
        alpha = 0.5f
    ),
    onErrorContainer = Error.copy(
        alpha = 0.7f
    )
)

private val LightColorScheme = lightColorScheme(
    background = CremaBackground,
    primary = CuarzoRosaContent,
    onPrimary = CremaBackground,
    primaryContainer = CremaBackground,
    onPrimaryContainer = CremaBackground,
    inversePrimary = GrutaAzul,
    secondary = Pink,
    onSecondary = CoralSecondary,
    secondaryContainer = CoralSecondary,
    onSecondaryContainer = CoralSecondary,
    tertiary = CoralSecondary,
    onTertiary = CremaBackground,
    tertiaryContainer = CremaBackground,
    onTertiaryContainer = CremaBackground,
    surface = CremaBackground,
    onSurface = Pink,
    error = Error,
    onError = Error.copy(
        alpha = 0.7f
    ),
    errorContainer = Error.copy(
        alpha = 0.5f
    ),
    onErrorContainer = Error.copy(
        alpha = 0.7f
    )
)

@Composable
fun HomeShoppingTheme(
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
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}