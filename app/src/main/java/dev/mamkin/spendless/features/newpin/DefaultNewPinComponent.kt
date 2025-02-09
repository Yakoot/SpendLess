package dev.mamkin.spendless.features.newpin

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DefaultNewPinComponent(
    componentContext: ComponentContext,
    val onBack: () -> Unit
) : NewPinComponent, ComponentContext by componentContext {

    private val _state = MutableStateFlow(NewPinComponent.State)
    override val state = _state.asStateFlow()

    override fun onEvent(event: NewPinComponent.UiEvent) {
        when (event) {
            NewPinComponent.UiEvent.Back -> onBack()
        }
    }

}
