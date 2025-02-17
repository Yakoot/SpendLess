package com.octafx.uikit.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mamkin.spendless.ui.theme.AppColors
import dev.mamkin.spendless.ui.theme.SpendLessTheme

@Composable
fun AppSegmentedButton(
    modifier: Modifier = Modifier,
    items: List<String>,
    defaultSelectedItemIndex: Int = 0,
    onItemSelection: (selectedItemIndex: Int) -> Unit
) {
    val selectedIndex = remember { mutableStateOf(defaultSelectedItemIndex) }
    val itemIndex = remember { mutableStateOf(defaultSelectedItemIndex) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selectedIndex.value == itemIndex.value) {
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.08f)
            } else {
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.08f)
            }
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp)
                .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.08f)),
            horizontalArrangement = Arrangement.Center
        ) {
            items.forEachIndexed { index, item ->
                itemIndex.value = index
                Card(
                    modifier = modifier
                        .weight(1f),
                    onClick = {
                        selectedIndex.value = index
                        onItemSelection(selectedIndex.value)
                    },
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedIndex.value == index) {
                            MaterialTheme.colorScheme.surfaceContainerLowest
                        } else {
                            Color.Transparent
                        },
                        contentColor = if (selectedIndex.value == index)
                            MaterialTheme.colorScheme.primary
                        else
                            AppColors.OnPrimaryFixed
                    ),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Text(
                            text = item,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,

                            )
                    }
                }
            }
        }
    }

}



@Preview(name = "Light Theme")
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewSegmentedToggle() {
    var selectedIndex by remember { mutableStateOf(0) }

    SpendLessTheme {
        Surface {
            AppSegmentedButton(
                items = listOf("Option 1", "Option 2", "Option 3", "Option 4"),
                defaultSelectedItemIndex = selectedIndex,
                onItemSelection = { selectedIndex = it }
            )
        }
    }
}
