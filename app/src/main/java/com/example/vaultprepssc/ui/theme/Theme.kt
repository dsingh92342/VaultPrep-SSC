package com.example.vaultprepssc.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = VaultPrimaryDark,
    onPrimary = VaultOnPrimaryDark,
    secondary = VaultSecondary,
    onSecondary = VaultOnSecondary,
    background = VaultBackgroundDark,
    surface = VaultSurfaceDark,
    onBackground = VaultOnBackgroundDark,
    onSurface = VaultOnSurfaceDark
)

private val LightColorScheme = lightColorScheme(
    primary = VaultPrimary,
    onPrimary = VaultOnPrimary,
    secondary = VaultSecondary,
    onSecondary = VaultOnSecondary,
    background = VaultBackground,
    surface = VaultSurface,
    onBackground = VaultOnBackground,
    onSurface = VaultOnSurface
)

@Composable
fun VaultPrepSSCTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Disabled for a more consistent branded feel
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