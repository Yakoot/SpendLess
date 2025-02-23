package dev.mamkin.spendless.features.registration.preferences

import CurrencySelect
import CurrencySelectOption
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.octafx.uikit.components.AppSegmentedButton
import dev.mamkin.spendless.ui.components.AppBackButton
import dev.mamkin.spendless.ui.components.AppButton
import dev.mamkin.spendless.ui.components.AppButtonType
import dev.mamkin.spendless.ui.components.AppCard
import dev.mamkin.spendless.ui.theme.SpendLessTheme
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import kotlin.math.abs

@Composable
fun PreferencesUI(
    component: PreferencesComponent
) {
    val currencyOptions = remember { createCurrencySelectOptions() }
    val state by component.state.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        Row {
            AppBackButton(
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
                onClick = { component.onEvent(PreferencesComponent.UiEvent.Back) }
            )
        }
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Set SpendLess\n" +
                        "to your preferences",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineMedium,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "You can change it at any time in Settings",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.height(24.dp))
            ExpenseFormatCard(preferences = state.preferences)
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Expenses format",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.labelSmall,
            )
            Spacer(modifier = Modifier.height(4.dp))
            AppSegmentedButton(
                items = listOf("-\$10", "(\$10)"),
                defaultSelectedItemIndex = if (state.preferences.expensesFormat == ExpensesFormat.MINUS) 0 else 1,
                onItemSelection = {
                    val value = when (it) {
                        0 -> ExpensesFormat.MINUS
                        else -> ExpensesFormat.PARENTHESES
                    }
                    component.onEvent(
                        PreferencesComponent.UiEvent.ChangeExpensesFormat(
                            value
                        )
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Currency",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.labelSmall,
            )
            Spacer(modifier = Modifier.height(4.dp))
            CurrencySelect(
                options = currencyOptions,
                selectedOption = currencyOptions.first { it.id == state.preferences.currency.code },
                onOptionSelected = {
                    val value = Currency.entries.first { currency -> currency.code == it.id }
                    component.onEvent(
                        PreferencesComponent.UiEvent.ChangeCurrency(value)
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Decimal separator",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.labelSmall,
            )
            Spacer(modifier = Modifier.height(4.dp))
            AppSegmentedButton(
                items = listOf("1.00", "1,00"),
                defaultSelectedItemIndex = if (state.preferences.decimalSeparator == DecimalSeparator.DOT) 0 else 1,
                onItemSelection = {
                    val value = when (it) {
                        0 -> DecimalSeparator.DOT
                        else -> DecimalSeparator.COMMA
                    }
                    component.onEvent(
                        PreferencesComponent.UiEvent.ChangeDecimalSeparator(
                            value
                        )
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Thousands separator",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.labelSmall,
            )
            Spacer(modifier = Modifier.height(4.dp))
            AppSegmentedButton(
                items = listOf("1.000", "1,000", "1 000"),
                defaultSelectedItemIndex = when (state.preferences.thousandsSeparator) {
                    ThousandsSeparator.DOT -> 0
                    ThousandsSeparator.COMMA -> 1
                    else -> 2
                },
                onItemSelection = {
                    val value = when (it) {
                        0 -> ThousandsSeparator.DOT
                        1 -> ThousandsSeparator.COMMA
                        else -> ThousandsSeparator.SPACE
                    }
                    component.onEvent(
                        PreferencesComponent.UiEvent.ChangeThousandsSeparator(
                            value
                        )
                    )
                }
            )
            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 24.dp)
            )
            AppButton(
                modifier = Modifier.fillMaxWidth(),
                type = AppButtonType.Filled,
                text = "Start Tracking!",
                onClick = { component.onEvent(PreferencesComponent.UiEvent.Save) }
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

    }
}

@Composable
private fun ExpenseFormatCard(
    modifier: Modifier = Modifier,
    preferences: Preferences
) {
    AppCard(
        modifier = Modifier
            .height(110.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = formatExpense(-1234567.89, preferences),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "spend this month",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

fun formatExpense(number: Double, preferences: Preferences): String {
    val isNegative = number < 0
    // Используем Locale.US для получения базового формата:
    // groupingSeparator = ',' и decimalSeparator = '.'
    val defaultSymbols = DecimalFormatSymbols(Locale.US).apply {
        currencySymbol = preferences.currency.symbol
    }

    // Паттерн с универсальным символом валюты (\u00A4)
    val pattern = "\u00A4#,##0.00"
    val decimalFormat = DecimalFormat(pattern, defaultSymbols).apply {
        isGroupingUsed = true
    }

    // Форматируем число (например, "€1,234,567.89")
    val formattedDefault = decimalFormat.format(abs(number))

    // Первый шаг: заменяем точку на решётку, запятую на звёздочку
    var temp = formattedDefault.replace('.', '#').replace(',', '*')

    // Определяем желаемые символы из настроек
    val newGroupingSeparator = when (preferences.thousandsSeparator) {
        ThousandsSeparator.DOT -> '.'
        ThousandsSeparator.COMMA -> ','
        ThousandsSeparator.SPACE -> ' '
    }
    val newDecimalSeparator = when (preferences.decimalSeparator) {
        DecimalSeparator.DOT -> '.'
        DecimalSeparator.COMMA -> ','
    }

    // Второй шаг: заменяем временные символы на нужные разделители
    temp = temp.replace('#', newDecimalSeparator).replace('*', newGroupingSeparator)

    if (isNegative) {
        temp = when (preferences.expensesFormat) {
            ExpensesFormat.MINUS -> "-$temp"
            ExpensesFormat.PARENTHESES -> "($temp)"
        }
    }

    return temp
}

private fun createCurrencySelectOptions(): List<CurrencySelectOption> {
    return Currency.entries.map { item ->
        CurrencySelectOption(
            id = item.code,
            label = item.label,
            icon = item.symbol,
        )
    }
}

@Preview
@Composable
fun RegistrationUIPreview() {
    SpendLessTheme {
        PreferencesUI(PreviewPreferencesComponent())
    }
}