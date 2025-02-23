package dev.mamkin.spendless.features.login

import kotlinx.coroutines.flow.StateFlow

interface LoginComponent {
    val state: StateFlow<State>
    fun onEvent(event: UiEvent)

    data class State(
        val username: String = "",
        val pin: String = "",
        val buttonEnabled: Boolean = false,
        val error: String? = null
    )


    sealed interface UiEvent {
        data class UsernameInput(val value: String) : UiEvent
        data class PinInput(val value: String) : UiEvent
        data object Login : UiEvent
        data object Register : UiEvent
    }
}