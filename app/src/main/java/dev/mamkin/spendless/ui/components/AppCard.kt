package dev.mamkin.spendless.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.mamkin.spendless.ui.theme.SpendLessTheme

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    elevation: Dp = 4.dp,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        // You can customize the card’s shape as desired
        shape = RoundedCornerShape(16.dp),
        // Material3 uses CardDefaults to provide elevation styling
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        // Customize the container color using MaterialTheme colorScheme
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        )
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun CustomMaterial3CardPreview() {
    SpendLessTheme {
        AppCard(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Custom Material3 Card",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "This card demonstrates custom elevation.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}