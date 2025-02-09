package dev.mamkin.spendless.features.registration

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mamkin.spendless.R
import dev.mamkin.spendless.features.registration.RegistrationComponent.UiEvent
import dev.mamkin.spendless.ui.components.AppButton
import dev.mamkin.spendless.ui.components.AppButtonType
import dev.mamkin.spendless.ui.components.RegistrationInput
import dev.mamkin.spendless.ui.theme.SpendLessTheme
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun RegistrationUI(component: RegistrationComponent) {
    val state by component.state.collectAsState()

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .padding(vertical = 36.dp, horizontal = 26.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            contentDescription = "Registration",
            painter = painterResource(id = R.drawable.logo)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Welcome to SpendLess! How can we address you?",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Create unique username",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(36.dp))
        RegistrationInput(
            value = state.username,
            placeholder = "username",
            onValueChange = { component.onEvent(UiEvent.Input(it)) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        AppButton(
            modifier = Modifier.fillMaxWidth(),
            type = AppButtonType.Filled,
            text = "Next",
            rightIcon = {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
            },
            enabled = state.buttonEnabled,
            onClick = { component.onEvent(UiEvent.Next) }
        )
        Spacer(modifier = Modifier.height(28.dp))
        AppButton(
            modifier = Modifier.fillMaxWidth(),
            type = AppButtonType.Text,
            text = "Already have an account?",
            onClick = { component.onEvent(UiEvent.Login) }
        )
    }
}

@Preview
@Composable
fun RegistrationUIPreview() {
    SpendLessTheme {
        RegistrationUI(object : RegistrationComponent {
            override val state = MutableStateFlow(RegistrationComponent.State())
            override fun onEvent(event: UiEvent) {}
        })
    }
}