package dev.mamkin.spendless.features.registration.newpin

import dev.mamkin.spendless.features.common.pincode.PinCodeComponent
import kotlinx.coroutines.flow.StateFlow

interface NewPinComponent {
    val state: StateFlow<State>
    val pinCodeComponent: PinCodeComponent

    data object State

    fun onEvent(event: UiEvent)

    sealed interface UiEvent {
        data object Back : UiEvent
    }
}
