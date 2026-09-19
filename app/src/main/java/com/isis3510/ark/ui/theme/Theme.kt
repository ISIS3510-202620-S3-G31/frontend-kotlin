package com.isis3510.ark.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val ArkColorScheme = lightColorScheme(
    primary = ArkPrimary,
    onPrimary = ArkText,
    secondary = ArkSecondary,
    onSecondary = ArkText,
    tertiary = ArkAccent,
    onTertiary = ArkText,
    background = ArkBackground,
    onBackground = ArkText,
    surface = ArkBackground,
    onSurface = ArkText,
    surfaceVariant = ArkSurfaceDim,
    onSurfaceVariant = ArkText,
    inverseSurface = ArkText,
    inverseOnSurface = ArkBackground,
    error = ArkError,
    onError = ArkBackground,
    outline = ArkText.copy(alpha = 0.2f),
)

// The palette is the same in light and dark mode on purpose: the warm cream background
// is part of the brand (MS6, section 2.2), so there is no dark colour scheme.
@Composable
fun ArkTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ArkColorScheme,
        typography = ArkTypography,
        shapes = ArkShapes,
        content = content,
    )
}
