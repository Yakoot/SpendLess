package dev.mamkin.spendless.features.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import dev.mamkin.spendless.features.login.LoginUi
import dev.mamkin.spendless.features.registration.RegistrationUI


@Composable
fun RootUi(component: RootComponent) {

    val stack by component.stack.subscribeAsState()

    Children(
        stack = stack,
    ){ child ->
        when(val instance = child.instance){
            is RootComponent.Child.Login -> LoginUi(instance.component)
            is RootComponent.Child.Registration -> RegistrationUI(instance.component)
        }
    }
}