package dev.mamkin.spendless.features.registration.newuser

import com.arkivanov.decompose.ComponentContext
import dev.mamkin.spendless.features.registration.newuser.NewUserComponent.State
import dev.mamkin.spendless.features.registration.newuser.NewUserComponent.UiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DefaultNewUserComponent(
    componentContext: ComponentContext,
    val onNextClicked: () -> Unit
) : NewUserComponent, ComponentContext by componentContext {
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
