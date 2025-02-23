// Import your custom DropdownMenuItem (the expect fun) from wherever you defined it
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.mamkin.spendless.ui.components.AppCard
import dev.mamkin.spendless.ui.theme.AppColors
import dev.mamkin.spendless.ui.theme.SpendLessTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencySelect(
    options: List<CurrencySelectOption>,
    selectedOption: CurrencySelectOption?,
    onOptionSelected: (CurrencySelectOption) -> Unit,
    modifier: Modifier = Modifier,
) {
    val textValue = remember(selectedOption) {
        selectedOption?.label ?: ""
    }

    var expanded by remember { mutableStateOf(false) }


    AppSelect(
        options = {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Row(
                            modifier = Modifier.fillMaxHeight(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CurrencyView(currency = option.icon)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = option.label,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                    trailingIcon = {
                        if (option == selectedOption) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                )
            }
        },
        selectedOption = {
            SelectedOptionCard(expanded = expanded) {
                selectedOption?.let { CurrencyView(currency = it.icon) }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = textValue,
                    modifier = Modifier
                        .weight(1f)
                )
            }
        },
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySelect(
    options: List<CategorySelectOption>,
    selectedOption: CategorySelectOption?,
    onOptionSelected: (CategorySelectOption) -> Unit,
    modifier: Modifier = Modifier,
) {
    val textValue = remember(selectedOption) {
        selectedOption?.label ?: ""
    }

    var expanded by remember { mutableStateOf(false) }

    AppSelect(
        options = {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option.label,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                    leadingIcon = {
                        EmojiView(emoji = option.icon)
                    },
                    trailingIcon = {
                        if (option == selectedOption) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                )
            }
        },
        selectedOption = {
            SelectedOptionCard(expanded = expanded) {
                selectedOption?.let { EmojiView(emoji = it.icon) }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    modifier = Modifier.weight(1f),
                    text = textValue,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExposedDropdownMenuBoxScope.SelectedOptionCard (
    modifier: Modifier = Modifier,
    expanded: Boolean,
    content: @Composable RowScope.() -> Unit,
    ) {
    AppCard(
        modifier = modifier
            .padding(vertical = 8.dp)
            .height(48.dp)
            .menuAnchor(MenuAnchorType.PrimaryNotEditable),
    ) {
        Row(
            Modifier
                .padding(MenuDefaults.DropdownMenuItemContentPadding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            content()
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier
                    .padding(12.dp)
                    .size(24.dp)
                    .rotate(if (expanded) 180f else 0f)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppSelect(
    options: @Composable ColumnScope.() -> Unit,
    selectedOption: @Composable ExposedDropdownMenuBoxScope.() -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier
    ) {
        selectedOption()
        // The dropdown menu itself
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
            shape = RoundedCornerShape(16.dp),
            // Material3 uses CardDefaults to provide elevation styling
            shadowElevation = 4.dp,
            // Customize the container color using MaterialTheme colorScheme
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        ) {
            Column(
                modifier = Modifier
                    .heightIn(max = 240.dp)
                    .verticalColumnScrollbar(scrollState)
                    .verticalScroll(scrollState)
                ,
                content = options
            )
        }
    }
}

@Composable
private fun EmojiView(
    modifier: Modifier = Modifier,
    emoji: String
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(AppColors.PrimaryFixed)
        ,
        contentAlignment = Alignment.Center
    ) {
        Text(
            style = MaterialTheme.typography.titleLarge,
            text = emoji
        )
    }
}

@Composable
private fun CurrencyView(
    modifier: Modifier = Modifier,
    currency: String
) {
    Text(
        style = MaterialTheme.typography.titleMedium,
        text = currency
    )
}

data class CurrencySelectOption(
    val id: String,
    val label: String,
    val icon: String,
)

data class CategorySelectOption(
    val id: Int,
    val label: String,
    val icon: String,
)

@Composable
fun Modifier.verticalColumnScrollbar(
    scrollState: ScrollState,
    width: Dp = 4.dp,
    showScrollBarTrack: Boolean = true,
    scrollBarTrackColor: Color = Color.Transparent,
    scrollBarColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
    scrollBarCornerRadius: Float = 4f,
    endPadding: Dp = 4.dp
): Modifier {
    return drawWithContent {
        // Draw the column's content
        drawContent()
        // Dimensions and calculations
        val viewportHeight = this.size.height
        val totalContentHeight = scrollState.maxValue.toFloat() + viewportHeight
        val scrollValue = scrollState.value.toFloat()
        // Compute scrollbar height and position
        val scrollBarHeight =
            (viewportHeight / totalContentHeight) * viewportHeight
        val scrollBarStartOffset =
            (scrollValue / totalContentHeight) * viewportHeight
        val xOffset = size.width - width.toPx() - endPadding.toPx()

        // Draw the track (optional)
        if (showScrollBarTrack) {
            drawRoundRect(
                cornerRadius = CornerRadius(scrollBarCornerRadius),
                color = scrollBarTrackColor,
                topLeft = Offset(xOffset, 0f),
                size = Size(width.toPx(), viewportHeight),
            )
        }
        // Draw the scrollbar
        drawRoundRect(
            cornerRadius = CornerRadius(scrollBarCornerRadius),
            color = scrollBarColor,
            topLeft = Offset(xOffset, scrollBarStartOffset),
            size = Size(width.toPx(), scrollBarHeight)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    val options = listOf(
        CategorySelectOption(id = 0, label = "Clothing & Accessories", icon = "\uD83D\uDC54"),
        CategorySelectOption(id = 1, label = "Food & Dining", icon = "\uD83C\uDF54"),
        CategorySelectOption(id = 2, label = "Health & Fitness", icon = "\uD83D\uDCAA"),
        CategorySelectOption(id = 3, label = "Home", icon = "\uD83C\uDFE0"),
        CategorySelectOption(id = 4, label = "Transportation", icon = "\uD83D\uDE8C"),
        CategorySelectOption(id = 5, label = "Utilities", icon = "\uD83D\uDD0B"),
        CategorySelectOption(id = 6, label = "Entertainment", icon = "\uD83C\uDFAC"),
        CategorySelectOption(id = 7, label = "Education", icon = "\uD83C\uDF93"),
        CategorySelectOption(id = 8, label = "Gifts & Donations", icon = "\uD83C\uDF81"),
    )

    val options2 = listOf(
        CurrencySelectOption(id = "0", label = "US Dollar (USD)", icon = "$"),
        CurrencySelectOption(id = "1", label = "Euro (EUR)", icon = "€"),
        CurrencySelectOption(id = "2", label = "British Pound (GBP)", icon = "£"),
        CurrencySelectOption(id = "3", label = "Japanese Yen (JPY)", icon = "¥"),
        CurrencySelectOption(id = "4", label = "Australian Dollar (AUD)", icon = "$"),
        CurrencySelectOption(id = "5", label = "Canadian Dollar (CAD)", icon = "$"),
        CurrencySelectOption(id = "6", label = "Swiss Franc (CHF)", icon = "CHF"),
    )


    var selectedOption by remember { mutableStateOf(options[0]) }
    var selectedOption2 by remember { mutableStateOf(options2[0]) }

    SpendLessTheme {
        Scaffold {
            Column(
                modifier = Modifier.padding(it)
            ) {
                CategorySelect(
                    options = options,
                    selectedOption = selectedOption,
                    onOptionSelected = { selectedOption = it },
                )
                CurrencySelect(
                    options = options2,
                    selectedOption = selectedOption2,
                    onOptionSelected = { selectedOption2 = it },
                )
            }

        }

    }


}