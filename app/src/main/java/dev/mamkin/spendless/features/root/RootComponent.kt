package dev.mamkin.spendless.features.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import dev.mamkin.spendless.features.login.LoginComponent
import dev.mamkin.spendless.features.newpin.DefaultNewPinComponent
import dev.mamkin.spendless.features.newpin.NewPinComponent
import dev.mamkin.spendless.features.registration.DefaultRegistrationComponent
import dev.mamkin.spendless.features.registration.RegistrationComponent
import kotlinx.serialization.Serializable

class RootComponent(
    context: ComponentContext
) : ComponentContext by context {

    private val navigation = StackNavigation<Config>()
    val stack: Value<ChildStack<*, Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        handleBackButton = true,
        childFactory = ::createStack,
        initialConfiguration = Config.Registration
    )

    private fun createStack(
        config: Config,
        childContext: ComponentContext
    ) = when (config) {
        is Config.Login -> Child.Login(loginComponent(childContext))
        is Config.Registration -> Child.Registration(registrationComponent(childContext))
        is Config.NewPin -> Child.NewPin(newPinComponent(childContext))
    }

    private fun loginComponent(componentContext: ComponentContext): LoginComponent {
        return LoginComponent(componentContext)
    }

    @OptIn(DelicateDecomposeApi::class)
    private fun registrationComponent(componentContext: ComponentContext): RegistrationComponent {
        return DefaultRegistrationComponent(componentContext, onNextClicked = {
            navigation.pushNew(Config.NewPin)
        })
    }

    private fun newPinComponent(componentContext: ComponentContext): NewPinComponent {
        return DefaultNewPinComponent(componentContext, onBack = {
            navigation.pop()
        })
    }

    sealed interface Child {
        data class Login(val component: LoginComponent) : Child
        data class Registration(val component: RegistrationComponent) : Child
        data class NewPin(val component: NewPinComponent) : Child
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object Login: Config
        @Serializable
        data object Registration: Config
        @Serializable
        data object NewPin: Config
    }
}