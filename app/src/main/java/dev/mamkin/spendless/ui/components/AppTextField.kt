package dev.mamkin.spendless.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    isError: Boolean = false,
    isEnabled: Boolean = true,
    supportingText: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    val textColor = if (isEnabled) {
        if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    }

    Column(modifier = modifier) {
        label?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.labelSmall,
                color = textColor,
                modifier = Modifier.padding(bottom = 7.dp)
            )
        }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = isEnabled,
            placeholder = {
                placeholder?.let {
                    Text(
                        text = it
                    )
                }
            },
            maxLines = 1,
            isError = isError,
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation,
            modifier = Modifier
                .fillMaxWidth()
        )
        supportingText?.let {
            Text(
                text = supportingText,
                style = MaterialTheme.typography.bodySmall,
                color = textColor,
                modifier = Modifier.padding(top = 7.dp)
            )
        }
    }
}




@Preview(showBackground = true)
@Composable
fun CustomTextFieldPreview_Normal() {
    MaterialTheme {
        AppTextField(
            label = "Label",
            placeholder = "Placeholder",
            supportingText = "Supporting text",
            value = "",
            onValueChange = {},
            isError = false,
            isEnabled = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomTextFieldPreview_Error() {
    MaterialTheme {
        AppTextField(
            label = "Label",
            placeholder = "Placeholder",
            supportingText = "Error message",
            value = "",
            onValueChange = {},
            isError = true,
            isEnabled = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomTextFieldPreview_Disabled() {
    MaterialTheme {
        AppTextField(
            label = "Label",
            placeholder = "Placeholder",
            supportingText = "Supporting text",
            value = "",
            onValueChange = {},
            isError = false,
            isEnabled = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomTextFieldPreview_WithText() {
    MaterialTheme {
        AppTextField(
            label = "Label",
            placeholder = "Placeholder",
            supportingText = "Supporting text",
            value = "User input",
            onValueChange = {},
            isError = false,
            isEnabled = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomTextFieldPreview_WithTextAndError() {
    MaterialTheme {
        AppTextField(
            label = "Label",
            placeholder = "Placeholder",
            supportingText = "Error message",
            value = "Invalid input",
            onValueChange = {},
            isError = true,
            isEnabled = true
        )
    }
}