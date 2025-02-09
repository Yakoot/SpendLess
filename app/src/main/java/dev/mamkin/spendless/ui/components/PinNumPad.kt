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
import androidx.compose.ui.draw.clip
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
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            DigitButton(
                onClick = { onDigitClick("1") },
                digit = "1"
            )
            DigitButton(
                onClick = { onDigitClick("2") },
                digit = "2"
            )
            DigitButton(
                onClick = { onDigitClick("3") },
                digit = "3"
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            DigitButton(
                onClick = { onDigitClick("4") },
                digit = "4"
            )
            DigitButton(
                onClick = { onDigitClick("5") },
                digit = "5"
            )
            DigitButton(
                onClick = { onDigitClick("6") },
                digit = "6"
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            DigitButton(
                onClick = { onDigitClick("7") },
                digit = "7"
            )
            DigitButton(
                onClick = { onDigitClick("8") },
                digit = "8"
            )
            DigitButton(
                onClick = { onDigitClick("9") },
                digit = "9"
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Spacer(
                modifier = Modifier.width(108.dp)
            )
            DigitButton(
                onClick = { onDigitClick("0") },
                digit = "0"
            )
            BackspaceButton(
                onClick = onBackspaceClick
            )
        }
    }
}

@Composable
private fun DigitButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    digit: String
) {
    Box(
        modifier = modifier
            .size(108.dp)
            .clip(RoundedCornerShape(32.dp))
            .clickable(onClick = onClick)
            .background(AppColors.PrimaryFixed),
        contentAlignment = Alignment.Center
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
) {
    Box(
        modifier = modifier
            .size(108.dp)
            .clip(RoundedCornerShape(32.dp))
            .clickable(onClick = onClick)
            .background(AppColors.PrimaryFixed.copy(alpha = 0.3f)),
        contentAlignment = Alignment.Center
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