package dev.mamkin.spendless.features.root

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import dev.mamkin.spendless.features.login.LoginUi
import dev.mamkin.spendless.features.newpin.NewPinUI
import dev.mamkin.spendless.features.registration.RegistrationUI


@Composable
fun RootUi(component: RootComponent) {

    val stack by component.stack.subscribeAsState()

    Children(
        stack = stack,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.systemBars)
    ){ child ->
        when(val instance = child.instance){
            is RootComponent.Child.Login -> LoginUi(instance.component)
            is RootComponent.Child.Registration -> RegistrationUI(instance.component)
            is RootComponent.Child.NewPin -> NewPinUI(instance.component)
        }
    }
}