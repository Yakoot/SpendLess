package dev.mamkin.spendless.features.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import dev.mamkin.spendless.features.dashboard.DashboardComponent
import dev.mamkin.spendless.features.dashboard.DefaultDashboardComponent
import dev.mamkin.spendless.features.login.DefaultLoginComponent
import dev.mamkin.spendless.features.login.LoginComponent
import dev.mamkin.spendless.features.registration.DefaultRegistrationComponent
import dev.mamkin.spendless.features.registration.RegistrationComponent
import dev.mamkin.spendless.features.root.RootComponent.Child
import kotlinx.serialization.Serializable

class DefaultRootComponent(
    context: ComponentContext,
) : RootComponent, ComponentContext by context {

    private val navigation = StackNavigation<Config>()
    override val stack: Value<ChildStack<*, Child>> = childStack(
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
        is Config.Dashboard -> Child.Dashboard(dashboardComponent(childContext))
    }

    private fun loginComponent(componentContext: ComponentContext): LoginComponent {
        return DefaultLoginComponent(componentContext, onClickRegister = {
            navigation.replaceCurrent(Config.Registration)
        }, navigateToDashboard = {
            navigation.replaceCurrent(Config.Dashboard)
        })
    }

    @OptIn(DelicateDecomposeApi::class)
    private fun registrationComponent(componentContext: ComponentContext): RegistrationComponent {
        return DefaultRegistrationComponent(componentContext,
            navigateToLogin = {
                navigation.replaceCurrent(Config.Login)
            },
            navigateToDashboard = {
                navigation.replaceCurrent(Config.Dashboard)
            }
        )
    }

    private fun dashboardComponent(componentContext: ComponentContext): DashboardComponent {
        return DefaultDashboardComponent(componentContext)
    }


    @Serializable
    private sealed interface Config {
        @Serializable
        data object Login: Config
        @Serializable
        data object Registration: Config
        @Serializable
        data object Dashboard: Config
    }
}