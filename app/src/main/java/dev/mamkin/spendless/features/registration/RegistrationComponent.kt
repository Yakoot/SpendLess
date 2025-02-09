package dev.mamkin.spendless.features.registration

import kotlinx.coroutines.flow.StateFlow

interface RegistrationComponent {
    val state: StateFlow<State>
    fun onEvent(event: UiEvent)

    data class State(
        val username: String = "",
        val buttonEnabled: Boolean = false
    )


    sealed interface UiEvent {
        data class Input(val value: String) : UiEvent
        data object Next : UiEvent
        data object Login : UiEvent
    }
}
