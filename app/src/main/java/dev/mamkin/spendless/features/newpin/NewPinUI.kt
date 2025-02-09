package dev.mamkin.spendless.features.newpin

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
import dev.mamkin.spendless.ui.theme.SpendLessTheme
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun NewPinUI(component: NewPinComponent) {
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
                text = "Create PIN",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Use PIN to login to your account",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        AppBackButton(
            modifier = Modifier.align(Alignment.TopStart),
            onClick = { component.onEvent(NewPinComponent.UiEvent.Back) }
        )
    }
}

@Preview
@Composable
fun RegistrationUIPreview() {
    SpendLessTheme {
        NewPinUI(object : NewPinComponent {
            override val state = MutableStateFlow(NewPinComponent.State)
            override fun onEvent(event: NewPinComponent.UiEvent) {}
        })
    }
}