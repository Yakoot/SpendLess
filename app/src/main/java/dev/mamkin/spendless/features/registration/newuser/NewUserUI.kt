package dev.mamkin.spendless.features.registration.newuser

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mamkin.spendless.R
import dev.mamkin.spendless.features.registration.newuser.NewUserComponent.UiEvent
import dev.mamkin.spendless.ui.components.AppButton
import dev.mamkin.spendless.ui.components.AppButtonType
import dev.mamkin.spendless.ui.components.RegistrationInput
import dev.mamkin.spendless.ui.theme.SpendLessTheme
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun NewUserUI(component: NewUserComponent) {
    val state by component.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(Unit) {
        component.usernameError.receiveAsFlow().collect { error ->
            snackbarHostState.showSnackbar(error)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
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
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter),
            snackbar = { data ->
                // Задаем свой стиль для Snackbar
                FullWidthSnackbar(
                    snackbarData = data,
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError,
                )
            }
        )
    }
}

@Composable
fun FullWidthSnackbar(
    snackbarData: androidx.compose.material3.SnackbarData,
    containerColor: Color,
    contentColor: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .imePadding()
            .heightIn(min = 48.dp) // Устанавливаем минимальную высоту
            .background(containerColor)
    ) {
        Text(
            text = snackbarData.visuals.message,
            color = contentColor,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}


@Preview
@Composable
fun RegistrationUIPreview() {
    SpendLessTheme {
        NewUserUI(object : NewUserComponent {
            override val state = MutableStateFlow(NewUserComponent.State())
            override val usernameError: Channel<String> = Channel(Channel.BUFFERED)

            override fun onEvent(event: UiEvent) {}
        })
    }
}