package dev.mamkin.spendless.features.registration.newpin

import kotlinx.coroutines.flow.StateFlow

interface NewPinComponent {
    val state: StateFlow<State>

    data class State(
        val pin: String = ""
    )

    fun onEvent(event: UiEvent)

    sealed interface UiEvent {
        data object Back : UiEvent
        data class DigitPressed(val digit: String) : UiEvent
        data object BackspacePressed : UiEvent
    }
}
