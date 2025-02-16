package dev.mamkin.spendless.features.registration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import dev.mamkin.spendless.data.repository.UserRepository
import dev.mamkin.spendless.features.pincode.DefaultPinCodeComponent
import dev.mamkin.spendless.features.pincode.PinCodeComponent
import dev.mamkin.spendless.features.registration.RegistrationComponent.Child.*
import dev.mamkin.spendless.features.registration.newuser.DefaultNewUserComponent
import dev.mamkin.spendless.features.registration.newuser.NewUserComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

class DefaultRegistrationComponent(
    componentContext: ComponentContext,
) : RegistrationComponent, ComponentContext by componentContext, KoinComponent {
    private val userRepository: UserRepository by inject()

    private val navigation = StackNavigation<Config>()
    override val stack: Value<ChildStack<*, RegistrationComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        handleBackButton = true,
        childFactory = ::createStack,
        initialConfiguration = Config.NewUser
    )

    private val _error = MutableStateFlow<String?>(null)
    override val error = _error.asStateFlow()

    private val scope = coroutineScope()


    private var username: String? = null
    private var pin: String? = null

    private fun createStack(
        config: Config,
        childContext: ComponentContext
    ) = when (config) {
        is Config.NewPin -> NewPin(newPinComponent(childContext))
        is Config.NewUser -> NewUser(newUserComponent(childContext))
        is Config.RepeatPin -> RepeatPin(repeatPinComponent(childContext))
    }


    private fun newPinComponent(componentContext: ComponentContext): PinCodeComponent {
        return DefaultPinCodeComponent(componentContext, onBack = {
            navigation.pop()
        }, onPinEnter = { pin ->
            this.pin = pin
            navigation.pushNew(Config.RepeatPin)
        })
    }

    private fun repeatPinComponent(componentContext: ComponentContext): PinCodeComponent {
        return DefaultPinCodeComponent(componentContext,
            onBack = {
                navigation.pop()
            },
            onPinEnter = { pin ->
                if (this.pin != pin) {
                    _error.value = "PINs don’t match. Try again"
                } else {
                    createUser(username!!, pin)
                }
            },
            hideError = {
                _error.value = null
            })
    }

    private fun newUserComponent(componentContext: ComponentContext): NewUserComponent {
        return DefaultNewUserComponent(componentContext, onNext =
        { newUsername ->
            username = newUsername
            navigation.pushNew(Config.NewPin)
        }, showError = {
            _error.value = it
        }, hideError = {
            _error.value = null
        })
    }

    private fun createUser(username: String, pin: String) {
        scope.launch {
            userRepository.createUser(username, pin)
        }
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object NewUser : Config

        @Serializable
        data object NewPin : Config

        @Serializable
        data object RepeatPin : Config
    }
}
