package dev.mamkin.spendless.features.registration.preferences

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DefaultPreferencesComponent(
    componentContext: ComponentContext,
    val onBack: () -> Unit,
    val onSave: (Preferences) -> Unit
) : PreferencesComponent, ComponentContext by componentContext {
    private val _state = MutableStateFlow(PreferencesComponent.State())
    override val state: StateFlow<PreferencesComponent.State> = _state.asStateFlow()

    override fun onEvent(event: PreferencesComponent.UiEvent) {
        when (event) {
            PreferencesComponent.UiEvent.Back -> onBack()
            is PreferencesComponent.UiEvent.ChangeExpensesFormat -> updateExpensesFormat(event.expensesFormat)
            is PreferencesComponent.UiEvent.ChangeDecimalSeparator -> updateDecimalSeparator(event.decimalSeparator)
            is PreferencesComponent.UiEvent.ChangeThousandsSeparator -> updateThousandsSeparator(event.thousandsSeparator)
            is PreferencesComponent.UiEvent.ChangeCurrency -> updateCurrency(event.currency)
            is PreferencesComponent.UiEvent.Save -> onSave(_state.value.preferences)
            else -> {}
        }
    }

    private fun updateExpensesFormat(expensesFormat: ExpensesFormat) {
        _state.update { it.copy(preferences = it.preferences.copy(expensesFormat = expensesFormat)) }
    }

    private fun updateDecimalSeparator(decimalSeparator: DecimalSeparator) {
        _state.update { it.copy(preferences = it.preferences.copy(decimalSeparator = decimalSeparator)) }
    }

    private fun updateThousandsSeparator(thousandsSeparator: ThousandsSeparator) {
        _state.update { it.copy(preferences = it.preferences.copy(thousandsSeparator = thousandsSeparator)) }
    }

    private fun updateCurrency(currency: Currency) {
        _state.update { it.copy(preferences = it.preferences.copy(currency = currency)) }
    }

}