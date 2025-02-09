package dev.mamkin.spendless.features.registration.newpin

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import dev.mamkin.spendless.features.common.pincode.DefaultPinCodeComponent
import dev.mamkin.spendless.features.common.pincode.PinCodeComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DefaultNewPinComponent(
    componentContext: ComponentContext,
    val onBack: () -> Unit
) : NewPinComponent, ComponentContext by componentContext {

    private val _state = MutableStateFlow(NewPinComponent.State)
    override val state = _state.asStateFlow()

    override val pinCodeComponent: PinCodeComponent = DefaultPinCodeComponent(
        componentContext = childContext(key = "pincode"),
        onPinCodeEntered = { }
    )

    override fun onEvent(event: NewPinComponent.UiEvent) {
        when (event) {
            NewPinComponent.UiEvent.Back -> onBack()
        }
    }

}
