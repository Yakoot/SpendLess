package dev.mamkin.spendless.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mamkin.spendless.ui.theme.SpendLessTheme

@Composable
fun PinBullets(
    modifier: Modifier = Modifier,
    value: String,
) {
    val activeColor = MaterialTheme.colorScheme.primary
    val inactiveColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.12f)
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        for (i in 0 until 5) {
            Box(modifier = Modifier
                .clip(CircleShape)
                .size(18.dp)
                .background(if (i < value.length) activeColor else inactiveColor))
        }
    }
}

@Preview
@Composable
private fun PinBulletsPreview() {
    SpendLessTheme {
        PinBullets(value = "1234")
    }
}