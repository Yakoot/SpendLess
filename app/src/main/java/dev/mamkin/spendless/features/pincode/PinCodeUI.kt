package dev.mamkin.spendless.features.pincode

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import dev.mamkin.spendless.ui.components.AppBackButton
import dev.mamkin.spendless.ui.components.PinBullets
import dev.mamkin.spendless.ui.components.PinNumPad
import dev.mamkin.spendless.ui.theme.SpendLessTheme
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun NewPinUI(
    component: PinCodeComponent,
    title: String = "Create PIN",
    description: String = "Use PIN to login to your account"
) {
    val state by component.state.collectAsState()

    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
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
                text = title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(36.dp))
            PinBullets(
                modifier = Modifier.height(56.dp),
                value = state.pin
            )
            Spacer(modifier = Modifier.height(32.dp))
            PinNumPad(
                onDigitClick = {
                    component.onEvent(PinCodeComponent.UiEvent.DigitPressed(it))
                },
                onBackspaceClick = {
                    component.onEvent(PinCodeComponent.UiEvent.BackspacePressed)
                }
            )
        }
        AppBackButton(
            modifier = Modifier.align(Alignment.TopStart),
            onClick = { component.onEvent(PinCodeComponent.UiEvent.Back) }
        )
    }
}

@Preview
@Composable
fun RegistrationUIPreview() {
    SpendLessTheme {
        NewPinUI(object : PinCodeComponent {
            override val state = MutableStateFlow(PinCodeComponent.State())
            override fun onEvent(event: PinCodeComponent.UiEvent) {}
        })
    }
}