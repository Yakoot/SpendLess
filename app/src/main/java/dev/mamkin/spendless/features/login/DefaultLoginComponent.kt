package dev.mamkin.spendless.features.login

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import dev.mamkin.spendless.data.PinCodeEncryption
import dev.mamkin.spendless.data.repository.UserRepository
import dev.mamkin.spendless.features.login.LoginComponent.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

class DefaultLoginComponent(
    componentContext: ComponentContext,
    val onClickRegister: () -> Unit
) : LoginComponent, ComponentContext by componentContext, KoinComponent {
    private val userRepository: UserRepository by inject()
    private val pinCodeEncryption: PinCodeEncryption by inject()


    private val _state = MutableStateFlow(State())
    override val state = _state.asStateFlow()

    private val scope = coroutineScope()


    override fun onEvent(event: LoginComponent.UiEvent) {
        when (event) {
            is LoginComponent.UiEvent.UsernameInput -> onUsernameChanged(event.value)
            is LoginComponent.UiEvent.PinInput -> onPinChanged(event.value)
            is LoginComponent.UiEvent.Login -> onLoginClicked()
            is LoginComponent.UiEvent.Register -> onClickRegister()
        }
    }

    private fun onUsernameChanged(username: String) {
        _state.update { state -> state.copy(
            username = username,
            buttonEnabled = username.isNotBlank() && state.pin.isNotBlank(),
            error = null
        ) }
    }

    private fun onPinChanged(pin: String) {
        if (pin.length > 5) return
        _state.update { state -> state.copy(
            pin = pin,
            buttonEnabled = pin.isNotBlank() && state.username.isNotBlank(),
            error = null
        ) }
    }

    private fun onLoginClicked() {
        if (_state.value.buttonEnabled.not()) return
        val username = _state.value.username
        val pin = _state.value.pin

        scope.launch(Dispatchers.IO) {
            val user = userRepository.findUserByUsername(username)
            if (user == null) {
                _state.update { state -> state.copy(error = "Username is incorrect") }
                return@launch
            }
            if (!pinCodeEncryption.verifyPin(user.pin, pin)) {
                _state.update { state -> state.copy(error = "PIN is incorrect") }
            }
        }
    }

}
