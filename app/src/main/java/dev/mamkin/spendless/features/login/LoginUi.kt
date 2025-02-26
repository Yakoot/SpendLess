package dev.mamkin.spendless.features.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.mamkin.spendless.R
import dev.mamkin.spendless.features.login.LoginComponent.UiEvent
import dev.mamkin.spendless.ui.components.AppButton
import dev.mamkin.spendless.ui.components.AppButtonType
import dev.mamkin.spendless.ui.components.AppTextField
import dev.mamkin.spendless.ui.components.ErrorSnackbar

@Composable
fun LoginUi(component: LoginComponent) {
    val state by component.state.collectAsState()
    val usernameFocusRequester = remember { FocusRequester() }
    val pinFocusRequester = remember { FocusRequester() }


    // Optionally, get a reference to the keyboard controller
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        usernameFocusRequester.requestFocus()
        keyboardController?.show()
    }

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .padding(vertical = 36.dp, horizontal = 26.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                contentDescription = "Login",
                painter = painterResource(id = R.drawable.logo)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Welcome back!",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Enter you login details",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(36.dp))
            AppTextField(
                modifier = Modifier.focusRequester(usernameFocusRequester),
                value = state.username,
                placeholder = "Username",
                onValueChange = { component.onEvent(UiEvent.UsernameInput(it)) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
                keyboardActions = KeyboardActions(
                    onNext = { pinFocusRequester.requestFocus() }
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            AppTextField(
                modifier = Modifier.focusRequester(pinFocusRequester),
                value = state.pin,
                placeholder = "PIN",
                onValueChange = { component.onEvent(UiEvent.PinInput(it)) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        component.onEvent(UiEvent.Login)
                        keyboardController?.hide()
                    }
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            AppButton(
                modifier = Modifier.fillMaxWidth(),
                type = AppButtonType.Filled,
                text = "Log in",
                rightIcon = {
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                },
                enabled = state.buttonEnabled,
                onClick = { component.onEvent(UiEvent.Login) }
            )
            Spacer(modifier = Modifier.height(28.dp))
            AppButton(
                modifier = Modifier.fillMaxWidth(),
                type = AppButtonType.Text,
                text = "New to SpendLess?",
                onClick = { component.onEvent(UiEvent.Register) }
            )
        }
        state.error?.let {
            ErrorSnackbar(
                message = it,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }


}