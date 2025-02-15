package dev.mamkin.spendless.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mamkin.spendless.R
import dev.mamkin.spendless.ui.theme.AppColors
import dev.mamkin.spendless.ui.theme.SpendLessTheme

@Composable
fun PinNumPad(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onDigitClick: (String) -> Unit = {},
    onBackspaceClick: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            DigitButton(
                onClick = { onDigitClick("1") },
                digit = "1",
                enabled = enabled
            )
            DigitButton(
                onClick = { onDigitClick("2") },
                digit = "2",
                enabled = enabled
            )
            DigitButton(
                onClick = { onDigitClick("3") },
                digit = "3",
                enabled = enabled
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            DigitButton(
                onClick = { onDigitClick("4") },
                digit = "4",
                enabled = enabled
            )
            DigitButton(
                onClick = { onDigitClick("5") },
                digit = "5",
                enabled = enabled
            )
            DigitButton(
                onClick = { onDigitClick("6") },
                digit = "6",
                enabled = enabled
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            DigitButton(
                onClick = { onDigitClick("7") },
                digit = "7",
                enabled = enabled
            )
            DigitButton(
                onClick = { onDigitClick("8") },
                digit = "8",
                enabled = enabled
            )
            DigitButton(
                onClick = { onDigitClick("9") },
                digit = "9",
                enabled = enabled
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Spacer(modifier = Modifier.width(108.dp))
            DigitButton(
                onClick = { onDigitClick("0") },
                digit = "0",
                enabled = enabled
            )
            BackspaceButton(
                onClick = onBackspaceClick,
                enabled = enabled
            )
        }
    }
}

@Composable
private fun HapticButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    backgroundColor: Color,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    // Получаем экземпляр для тактильной отдачи
    val haptic = LocalHapticFeedback.current

    Box(
        modifier = modifier
            .size(108.dp)
            .clip(RoundedCornerShape(32.dp))
            .alpha(if (enabled) 1f else 0.3f)
            .clickable(
                enabled = enabled,
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onClick()
                }
            )
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
private fun DigitButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    digit: String,
    enabled: Boolean = true
) {
    HapticButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        backgroundColor = AppColors.PrimaryFixed
    ) {
        Text(
            text = digit,
            color = AppColors.OnPrimaryFixed,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Composable
private fun BackspaceButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    HapticButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        backgroundColor = AppColors.PrimaryFixed.copy(alpha = 0.3f)
    ) {
        Icon(
            modifier = Modifier.size(40.dp),
            painter = painterResource(id = R.drawable.backspace),
            contentDescription = null,
            tint = AppColors.OnPrimaryFixed
        )
    }
}

@Preview
@Composable
private fun PinNumPadButtonPreview() {
    SpendLessTheme {
        Row {
            DigitButton(
                digit = "1",
                onClick = { },
            )
            BackspaceButton(
                onClick = { },
            )
        }

    }
}



@Preview
@Composable
private fun Preview() {
    SpendLessTheme {
        PinNumPad()
    }
}

@Preview
@Composable
private fun DisabledPreview() {
    SpendLessTheme {
        PinNumPad(enabled = false)
    }
}