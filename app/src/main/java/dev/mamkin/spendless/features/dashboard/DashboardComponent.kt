package dev.mamkin.spendless.features.dashboard

import kotlinx.coroutines.flow.StateFlow

interface DashboardComponent {
    val state: StateFlow<State>

    data class State(
        val test: String = ""
    )

    fun onEvent(event: UiEvent)

    sealed interface UiEvent {

    }
}
