package dev.mamkin.spendless.features.registration.newuser

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow

interface NewUserComponent {
    val state: StateFlow<State>
    val usernameError: Channel<String>
    fun onEvent(event: UiEvent)

    data class State(
        val username: String = "",
        val buttonEnabled: Boolean = false,
        val usernameError: String? = null
    )


    sealed interface UiEvent {
        data class Input(val value: String) : UiEvent
        data object Next : UiEvent
        data object Login : UiEvent
    }
}
