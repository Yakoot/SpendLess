package dev.mamkin.spendless.features.newpin

import kotlinx.coroutines.flow.StateFlow

interface NewPinComponent {
    val state: StateFlow<State>

    data object State

    fun onEvent(event: UiEvent)

    sealed interface UiEvent {
        data object Back : UiEvent
    }
}
