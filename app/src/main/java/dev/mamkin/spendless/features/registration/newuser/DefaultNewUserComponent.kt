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
) : NewUserComponent, ComponentContext by componentContext, KoinComponent {
    private val userRepository: UserRepository by inject()

    private val _state = MutableStateFlow(State())
    override val state = _state.asStateFlow()

    override val usernameError = Channel<String>(Channel.BUFFERED)

    private val scope = coroutineScope()


    private fun onUsernameChanged(username: String) {
        _state.update { it.copy(username = username, buttonEnabled = username.isNotBlank()) }
    }

    private fun onNextClicked() {
        val username = _state.value.username
        scope.launch {
            val exists = withContext(Dispatchers.IO) {
                userRepository.isUsernameExists(username)
            }
            if (exists) {
                usernameError.send("Username already exists")
            }
        }
    }

    override fun onEvent(event: UiEvent) = when (event) {
        is UiEvent.Input -> onUsernameChanged(event.value)
        UiEvent.Next -> onNextClicked()
        UiEvent.Login -> {}
    }

}
