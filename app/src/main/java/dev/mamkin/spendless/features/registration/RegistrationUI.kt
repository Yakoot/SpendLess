package dev.mamkin.spendless.features.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import dev.mamkin.spendless.features.pincode.NewPinUI
import dev.mamkin.spendless.features.registration.newuser.NewUserUI
import dev.mamkin.spendless.ui.components.ErrorSnackbar

@Composable
fun RegistrationUI(component: RegistrationComponent) {
    val stack by component.stack.subscribeAsState()
    val error by component.error.collectAsState()

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
    ) {
        Children(
            stack = stack,
            modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars)
        ) { child ->
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
        error?.let {
            ErrorSnackbar(
                message = it,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}