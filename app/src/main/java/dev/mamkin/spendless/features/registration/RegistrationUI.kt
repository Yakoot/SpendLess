package dev.mamkin.spendless.features.registration

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
import dev.mamkin.spendless.features.registration.newpin.NewPinUI
import dev.mamkin.spendless.features.registration.newuser.NewUserUI

@Composable
fun RegistrationUI(component: RegistrationComponent) {
    val stack by component.stack.subscribeAsState()

    Children(
        stack = stack,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.systemBars)
    ){ child ->
        when(val instance = child.instance){
            is RegistrationComponent.Child.NewPin -> NewPinUI(
                instance.component,
                title = "Create PIN",
                description = "Use PIN to login to your account"
            )
            is RegistrationComponent.Child.RepeatPin -> NewPinUI(
                instance.component,
                title = "Repeat Your PIN",
                description = "Enter your PIN again"
            )
            is RegistrationComponent.Child.NewUser -> NewUserUI(instance.component)
        }
    }
}