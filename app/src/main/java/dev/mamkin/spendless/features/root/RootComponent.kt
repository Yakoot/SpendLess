package dev.mamkin.spendless.features.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import dev.mamkin.spendless.features.login.LoginComponent
import dev.mamkin.spendless.features.registration.RegistrationComponent

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {
        data class Login(val component: LoginComponent) : Child
        data class Registration(val component: RegistrationComponent) : Child
    }
}