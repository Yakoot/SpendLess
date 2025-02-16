package dev.mamkin.spendless.features.registration.newuser

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import dev.mamkin.spendless.data.repository.UserRepository
import dev.mamkin.spendless.features.registration.newuser.NewUserComponent.State
import dev.mamkin.spendless.features.registration.newuser.NewUserComponent.UiEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

class DefaultNewUserComponent(
    componentContext: ComponentContext,
    val showError: (String) -> Unit,
    val hideError: () -> Unit,
    val onNext: (String) -> Unit
) : NewUserComponent, ComponentContext by componentContext, KoinComponent {
    private val userRepository: UserRepository by inject()

    private val _state = MutableStateFlow(State())
    override val state = _state.asStateFlow()

    private val scope = coroutineScope()


    private fun onUsernameChanged(username: String) {
        hideError()
        _state.update { it.copy(username = username, buttonEnabled = username.isNotBlank()) }
    }

    private fun onNextClicked() {
        if (_state.value.buttonEnabled.not()) return
        val username = _state.value.username
        scope.launch {
            if (!validateUsername(username)) return@launch
            val exists = withContext(Dispatchers.IO) {
                userRepository.isUsernameExists(username)
            }
            if (exists) {
                showError("Username already exists")
                _state.update { it.copy(buttonEnabled = false) }
            } else {
                onNext(username)
            }
        }
    }

    private suspend fun validateUsername(username: String): Boolean {
        val regex = "^[a-zA-Z0-9]+$".toRegex()
        if (username.length < 3 || username.length > 14) {
            showError("Username should be 3-14 characters long")
            _state.update { it.copy(buttonEnabled = false) }
            return false
        } else if (!username.matches(regex)) {
            showError("Username should contain only letters and numbers")
            _state.update { it.copy(buttonEnabled = false) }
            return false
        } else {
            return true
        }
    }

    override fun onEvent(event: UiEvent) = when (event) {
        is UiEvent.Input -> onUsernameChanged(event.value)
        UiEvent.Next -> onNextClicked()
        UiEvent.Login -> {}
    }
}
