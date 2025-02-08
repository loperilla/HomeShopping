package io.loperilla.designsystem

/*****
 * Project: HomeShopping
 * From: io.loperilla.designsystem
 * Created By Manuel Lopera on 2/2/25 at 16:12
 * All rights reserved 2025
 */

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView

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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}