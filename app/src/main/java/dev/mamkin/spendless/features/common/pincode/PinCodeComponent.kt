package dev.mamkin.spendless.features.common.pincode

import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

interface PinCodeComponent {
    val state: StateFlow<State>

    fun onDigitPressed(digit: String)
    fun onBackspacePressed()

    @Serializable
    data class State(
        val pin: String
    )
}
