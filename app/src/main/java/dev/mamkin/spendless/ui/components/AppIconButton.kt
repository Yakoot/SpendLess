package dev.mamkin.spendless.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mamkin.spendless.ui.theme.AppColors
import dev.mamkin.spendless.ui.theme.SpendLessTheme

sealed interface AppIconButtonType {
    object Filled : AppIconButtonType
    object Standard : AppIconButtonType
    object Error : AppIconButtonType
    object OnPrimary : AppIconButtonType
}

@Composable
fun AppIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    type: AppIconButtonType = AppIconButtonType.Filled,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val (containerColor, contentColor) = when (type) {
        AppIconButtonType.Filled -> MaterialTheme.colorScheme.primary to MaterialTheme.colorScheme.onPrimary
        AppIconButtonType.Standard -> Color.Transparent to MaterialTheme.colorScheme.onSurfaceVariant
        AppIconButtonType.Error -> MaterialTheme.colorScheme.error.copy(alpha = 0.08f) to MaterialTheme.colorScheme.error
        AppIconButtonType.OnPrimary -> MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.12f) to MaterialTheme.colorScheme.onPrimary
    }

    FilledIconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .size(48.dp),
        shape = RoundedCornerShape(16.dp),
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
private fun AppIconButtonPreview() {
    SpendLessTheme {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Filled buttons
            AppIconButton(
                icon = Icons.Default.Settings,
                type = AppIconButtonType.Filled,
                onClick = {}
            )
            AppIconButton(
                icon = Icons.Default.Settings,
                type = AppIconButtonType.Filled,
                enabled = false,
                onClick = {}
            )

            // Standard buttons
            AppIconButton(
                icon = Icons.Default.Settings,
                type = AppIconButtonType.Standard,
                onClick = {}
            )
            AppIconButton(
                icon = Icons.Default.Settings,
                type = AppIconButtonType.Standard,
                enabled = false,
                onClick = {}
            )

            // Error buttons
            AppIconButton(
                icon = Icons.Default.Settings,
                type = AppIconButtonType.Error,
                onClick = {}
            )
            AppIconButton(
                icon = Icons.Default.Settings,
                type = AppIconButtonType.Error,
                enabled = false,
                onClick = {}
            )

            // OnPrimary buttons
            Box(
                modifier = Modifier.background(AppColors.OnPrimaryFixed)
            ) {
                AppIconButton(
                    icon = Icons.Default.Settings,
                    type = AppIconButtonType.OnPrimary,
                    onClick = {}
                )
            }
            Box(
                modifier = Modifier.background(AppColors.OnPrimaryFixed)
            ) {
                AppIconButton(
                    icon = Icons.Default.Settings,
                    type = AppIconButtonType.OnPrimary,
                    enabled = false,
                    onClick = {}
                )
            }
        }
    }
} 