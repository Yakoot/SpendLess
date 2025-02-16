package dev.mamkin.spendless.features.registration.preferences

import kotlinx.coroutines.flow.StateFlow

interface PreferencesComponent {
    val state: StateFlow<State>
    fun onEvent(event: UiEvent)

    data class State(
        val expensesFormat: String = "",
    )


    sealed interface UiEvent {
        data class Input(val value: String) : UiEvent
        data object Next : UiEvent
        data object Login : UiEvent
    }
}
