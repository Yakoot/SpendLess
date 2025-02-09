package dev.mamkin.spendless.features.registration

import com.arkivanov.decompose.ComponentContext
import dev.mamkin.spendless.features.registration.RegistrationComponent.State
import dev.mamkin.spendless.features.registration.RegistrationComponent.UiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DefaultRegistrationComponent(
    componentContext: ComponentContext,
    val onNextClicked: () -> Unit
) : RegistrationComponent, ComponentContext by componentContext {
    private val _state = MutableStateFlow(State())
    override val state = _state.asStateFlow()

    private fun onUsernameChanged(username: String) {
        _state.update { it.copy(username = username, buttonEnabled = username.isNotBlank()) }
    }

    override fun onEvent(event: UiEvent) = when (event) {
        is UiEvent.Input -> onUsernameChanged(event.value)
        UiEvent.Next -> onNextClicked()
        UiEvent.Login -> {}
    }

}
