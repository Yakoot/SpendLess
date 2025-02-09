package dev.mamkin.spendless.ui.theme

import android.app.Activity
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

private val LightColorScheme = lightColorScheme(
    primary = AppColors.Primary,
    primaryContainer = AppColors.PrimaryContainer,
    inversePrimary = AppColors.InversePrimary,
    onPrimary = AppColors.OnPrimary,
    secondary = AppColors.Secondary,
    secondaryContainer = AppColors.SecondaryContainer,
    onSecondaryContainer = AppColors.OnSecondaryContainer,
    onBackground = AppColors.OnBackground,
    background = AppColors.Background,
    surface = AppColors.Surface,
    surfaceVariant = AppColors.OnSurfaceVariant,
    onSurface = AppColors.OnSurface,
    inverseSurface = AppColors.InverseSurface,
    inverseOnSurface = AppColors.InverseOnSurface,
    surfaceContainerLowest = AppColors.SurfContainerLowest,
    surfaceContainerLow = AppColors.SurfContainerLow,
    surfaceContainer = AppColors.SurfContainer,
    surfaceContainerHighest = AppColors.SurfContainerHighest,
    error = AppColors.Error,
    onError = AppColors.OnError,
    tertiaryContainer = AppColors.TertiaryContainer,
    outline = AppColors.Outline,
)

@Composable
fun SpendLessTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = typography,
        content = content
    )
}