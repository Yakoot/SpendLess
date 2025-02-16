package dev.mamkin.spendless.features.pincode

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val PIN_LENGTH = 5

class DefaultPinCodeComponent(
    componentContext: ComponentContext,
    val onBack: () -> Unit,
    val onPinEnter: (String) -> Unit,
    val hideError: () -> Unit = {}
) : PinCodeComponent, ComponentContext by componentContext {

    private val _state = MutableStateFlow(PinCodeComponent.State())
    override val state = _state.asStateFlow()

    override fun onEvent(event: PinCodeComponent.UiEvent) {
        when (event) {
            PinCodeComponent.UiEvent.Back -> onBack()
            PinCodeComponent.UiEvent.BackspacePressed -> onBackspacePressed()
            is PinCodeComponent.UiEvent.DigitPressed -> onDigitPressed(event.digit)
        }
    }

    private fun onDigitPressed(digit: String) {
        hideError()
        if (_state.value.pin.length == PIN_LENGTH) return
        _state.update { it.copy(pin = it.pin + digit) }
        if (_state.value.pin.length == PIN_LENGTH) {
            onPinEnter(_state.value.pin)
            _state.update { it.copy(pin = "") }
        }
    }

    private fun onBackspacePressed() {
        _state.update { it.copy(pin = it.pin.dropLast(1)) }
    }

}
