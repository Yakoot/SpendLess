package dev.mamkin.spendless.features.registration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import dev.mamkin.spendless.features.registration.RegistrationComponent.Child.*
import dev.mamkin.spendless.features.registration.newpin.DefaultNewPinComponent
import dev.mamkin.spendless.features.registration.newpin.NewPinComponent
import dev.mamkin.spendless.features.registration.newuser.DefaultNewUserComponent
import dev.mamkin.spendless.features.registration.newuser.NewUserComponent
import kotlinx.serialization.Serializable

class DefaultRegistrationComponent(
    componentContext: ComponentContext,
) : RegistrationComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()
    override val stack: Value<ChildStack<*, RegistrationComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        handleBackButton = true,
        childFactory = ::createStack,
        initialConfiguration = Config.NewUser
    )

    private var username: String? = null

    private fun createStack(
        config: Config,
        childContext: ComponentContext
    ) = when (config) {
        is Config.NewPin -> NewPin(newPinComponent(childContext))
        is Config.NewUser -> NewUser(newUserComponent(childContext))
        is Config.RepeatPin -> RepeatPin(newPinComponent(childContext))
    }


    private fun newPinComponent(componentContext: ComponentContext): NewPinComponent {
        return DefaultNewPinComponent(componentContext, onBack = {
            navigation.pop()
        }, onPinEnter = { pin ->
            println("New pin: $pin")
            navigation.pushNew(Config.RepeatPin)
        })
    }

    private fun repeatPinComponent(componentContext: ComponentContext): NewPinComponent {
        return DefaultNewPinComponent(componentContext, onBack = {
            navigation.pop()
        }, onPinEnter = { pin ->
            println("New pin: $pin")
        })
    }

    private fun newUserComponent(componentContext: ComponentContext): NewUserComponent {
        return DefaultNewUserComponent(componentContext, onNext =
        { newUsername ->
            username = newUsername
            navigation.pushNew(Config.NewPin)
        })
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object NewUser: Config
        @Serializable
        data object NewPin: Config
        @Serializable
        data object RepeatPin: Config
    }
}
