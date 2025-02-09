package dev.mamkin.spendless.features.common.pincode

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.mamkin.spendless.ui.components.PinBullets
import dev.mamkin.spendless.ui.components.PinNumPad

@Composable
fun PinCodeUI(
    pinCodeComponent: PinCodeComponent,
    modifier: Modifier = Modifier
) {
    val state by pinCodeComponent.state.collectAsState()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PinBullets(
            modifier = Modifier.height(56.dp),
            value = state.pin
        )
        Spacer(modifier = Modifier.height(32.dp))
        PinNumPad(
            onDigitClick = pinCodeComponent::onDigitPressed,
            onBackspaceClick = pinCodeComponent::onBackspacePressed
        )
    }
}