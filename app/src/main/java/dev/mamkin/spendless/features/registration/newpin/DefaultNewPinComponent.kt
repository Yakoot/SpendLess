package dev.mamkin.spendless.features.registration.newpin

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val PIN_LENGTH = 5

class DefaultNewPinComponent(
    componentContext: ComponentContext,
    val onBack: () -> Unit,
    val onPinEnter: (String) -> Unit
) : NewPinComponent, ComponentContext by componentContext {

    private val _state = MutableStateFlow(NewPinComponent.State())
    override val state = _state.asStateFlow()

    override fun onEvent(event: NewPinComponent.UiEvent) {
        when (event) {
            NewPinComponent.UiEvent.Back -> onBack()
            NewPinComponent.UiEvent.BackspacePressed -> onBackspacePressed()
            is NewPinComponent.UiEvent.DigitPressed -> onDigitPressed(event.digit)
        }
    }

    private fun onDigitPressed(digit: String) {
        if (_state.value.pin.length == PIN_LENGTH) return
        _state.update { it.copy(pin = it.pin + digit) }
        if (_state.value.pin.length == PIN_LENGTH) {
            _state.update { it.copy(pin = "") }
            onPinEnter(_state.value.pin)
        }
    }

    private fun onBackspacePressed() {
        _state.update { it.copy(pin = it.pin.dropLast(1)) }
    }

}
