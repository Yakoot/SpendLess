package dev.mamkin.spendless.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mamkin.spendless.ui.theme.SpendLessTheme

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    text: String,
    type: AppButtonType = AppButtonType.Filled,
    leftIcon: @Composable (() -> Unit)? = null,
    rightIcon: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val color = when (type) {
        AppButtonType.Filled -> MaterialTheme.colorScheme.primary
        AppButtonType.Text -> MaterialTheme.colorScheme.onPrimary
    }
    CompositionLocalProvider(LocalContentColor provides color) {
        when (type) {
            AppButtonType.Filled -> {
                Button(
                    modifier = modifier.height(48.dp),
                    onClick = onClick,
                    enabled = enabled,
                    shape = RoundedCornerShape(16.dp),
                    contentPadding = PaddingValues(
                        start = if (leftIcon != null) 16.dp else 24.dp,
                        end = if (rightIcon != null) 16.dp else 24.dp,
                        top = 12.dp,
                        bottom = 12.dp
                    ),
                    content = {
                        ButtonContent(leftIcon, rightIcon, text)
                    }
                )
            }
            AppButtonType.Text -> {
                TextButton(
                    modifier = modifier.height(48.dp),
                    onClick = onClick,
                    shape = RoundedCornerShape(16.dp),
                    enabled = enabled,
                    contentPadding = PaddingValues(
                        start = if (leftIcon != null) 12.dp else 16.dp,
                        end = if (rightIcon != null) 12.dp else 16.dp,
                        top = 12.dp,
                        bottom = 12.dp
                    ),
                    content = {
                        ButtonContent(leftIcon, rightIcon, text)
                    }
                )
            }
        }
    }

}

@Composable
private fun ButtonContent(
    leftIcon: @Composable (() -> Unit)?,
    rightIcon: @Composable (() -> Unit)?,
    text: String
) {
    leftIcon?.let {
        it()
        Spacer(modifier = Modifier.width(8.dp))
    }
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
    )
    rightIcon?.let {
        Spacer(modifier = Modifier.width(8.dp))
        it()
    }
}

sealed interface AppButtonType {
    object Filled : AppButtonType
    object Text : AppButtonType
}

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
private fun AppButtonPreview() {
    SpendLessTheme {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            AppButton(text = "Button", onClick = {})
            AppButton(
                text = "Button",
                leftIcon = { Icon(Icons.Default.Add, contentDescription = null) },
                onClick = {}
            )
            AppButton(
                text = "Button",
                rightIcon = { Icon(Icons.Default.Add, contentDescription = null) },
                onClick = {}
            )
            AppButton(text = "Button", enabled = false, onClick = {})
            AppButton(
                text = "Button",
                type = AppButtonType.Text,
                onClick = {}
            )
            AppButton(
                text = "Button",
                type = AppButtonType.Text,
                leftIcon = { Icon(Icons.Default.Add, contentDescription = null) },
                onClick = {}
            )
            AppButton(
                text = "Button",
                type = AppButtonType.Text,
                rightIcon = { Icon(Icons.Default.Add, contentDescription = null) },
                onClick = {}
            )
            AppButton(
                text = "Button",
                type = AppButtonType.Text,
                enabled = false,
                onClick = {}
            )
        }
    }
}