package dev.mamkin.spendless.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mamkin.spendless.ui.theme.SpendLessTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationInput(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String = "",
    onValueChange: (String) -> Unit = {}
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState()
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
                modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.08f)),
        visualTransformation = VisualTransformation.None,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        textStyle = MaterialTheme.typography.displayMedium.copy(textAlign = TextAlign.Center),
        enabled = true,
        singleLine = true
    ) {
        TextFieldDefaults.DecorationBox(
            value = value,
            visualTransformation = VisualTransformation.None,
            innerTextField = it,
            singleLine = true,
            enabled = true,
            interactionSource = interactionSource,
            shape = RoundedCornerShape(16.dp),
            placeholder = {
                if (!isFocused.value) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = placeholder,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.displayMedium.copy(textAlign = TextAlign.Center),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    )
                }
            },
            contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                top = 10.dp,
                bottom = 10.dp
            ),
            colors = TextFieldDefaults.colors().copy(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}

@Preview
@Composable
private fun RegistrationInputPreview() {
    SpendLessTheme {
        Column {
            RegistrationInput(value = "", placeholder = "username")
            Spacer(modifier = Modifier.height(16.dp))
            RegistrationInput(value = "test", placeholder = "username")
        }
    }
}