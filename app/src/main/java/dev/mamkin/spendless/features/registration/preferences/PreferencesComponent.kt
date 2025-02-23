package dev.mamkin.spendless.features.registration.preferences

import kotlinx.coroutines.flow.StateFlow

interface PreferencesComponent {
    val state: StateFlow<State>

    data class State(
        val preferences: Preferences = Preferences()
    )

    fun onEvent(event: UiEvent)

    sealed interface UiEvent {
        data object Back : UiEvent
        data class ChangeExpensesFormat(val expensesFormat: ExpensesFormat) : UiEvent
        data class ChangeDecimalSeparator(val decimalSeparator: DecimalSeparator) : UiEvent
        data class ChangeThousandsSeparator(val thousandsSeparator: ThousandsSeparator) : UiEvent
        data class ChangeCurrency(val currency: Currency) : UiEvent
        data object Save : UiEvent
    }
}

enum class ExpensesFormat {
    MINUS, PARENTHESES
}

enum class DecimalSeparator {
    DOT, COMMA
}

enum class ThousandsSeparator {
    SPACE, COMMA, DOT
}

enum class Currency(val symbol: String, val label: String, val code: String) {
    USD("$", "US Dollar", "USD"),
    EUR("€", "Euro", "EUR"),
    GBP("£", "British Pound Sterling", "GBP"),
    JPY("¥", "Japanese Yen", "JPY"),
    CHF("CHF", "Swiss Franc", "CHF"),
    CAD("C$", "Canadian Dollar", "CAD"),
    AUD("A$", "Australian Dollar", "AUD"),
    CNY("¥", "Chinese Yuan Renminbi", "CNY"),
    INR("₹", "Indian Rupee", "INR"),
    ZAR("R", "South African Rand", "ZAR")
}

data class Preferences(
    val currency: Currency = Currency.USD,
    val expensesFormat: ExpensesFormat = ExpensesFormat.MINUS,
    val decimalSeparator: DecimalSeparator = DecimalSeparator.DOT,
    val thousandsSeparator: ThousandsSeparator = ThousandsSeparator.SPACE
)

