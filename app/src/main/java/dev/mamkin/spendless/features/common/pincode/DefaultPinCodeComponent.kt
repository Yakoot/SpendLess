package dev.mamkin.spendless.features.common.pincode

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.statekeeper.ExperimentalStateKeeperApi
import com.arkivanov.essenty.statekeeper.saveable
import dev.mamkin.spendless.features.common.pincode.PinCodeComponent.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DefaultPinCodeComponent(
    componentContext: ComponentContext,
    private val onPinCodeEntered: (String) -> Unit
) : PinCodeComponent, ComponentContext by componentContext {

    @OptIn(ExperimentalStateKeeperApi::class)
    private val _state: MutableStateFlow<State> by saveable(
        serializer = State.serializer(),
        state = { state.value }
    ) {
        MutableStateFlow(it ?: State(pin = ""))
    }

    override val state: StateFlow<State> = _state.asStateFlow()

    override fun onDigitPressed(digit: String) {
        _state.update { it.copy(pin = it.pin + digit) }
        if (_state.value.pin.length == 5) {
            onPinCodeEntered(_state.value.pin)
        }
    }

    override fun onBackspacePressed() {
        _state.update { it.copy(pin = it.pin.dropLast(1)) }
    }
}
