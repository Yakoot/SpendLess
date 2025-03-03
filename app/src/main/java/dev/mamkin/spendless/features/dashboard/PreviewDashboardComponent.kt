package dev.mamkin.spendless.features.dashboard

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PreviewDashboardComponent : DashboardComponent {
    private val _state: MutableStateFlow<DashboardComponent.State> = MutableStateFlow(DashboardComponent.State())
    override val state: StateFlow<DashboardComponent.State> = _state.asStateFlow()

    override fun onEvent(event: DashboardComponent.UiEvent) {

    }
}