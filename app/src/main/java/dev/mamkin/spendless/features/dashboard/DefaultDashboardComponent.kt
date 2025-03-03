package dev.mamkin.spendless.features.dashboard

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DefaultDashboardComponent(
    componentContext: ComponentContext
) : DashboardComponent {
    private val _state: MutableStateFlow<DashboardComponent.State> = MutableStateFlow(DashboardComponent.State())
    override val state: StateFlow<DashboardComponent.State> = _state.asStateFlow()

    override fun onEvent(event: DashboardComponent.UiEvent) {
        TODO("Not yet implemented")
    }
}