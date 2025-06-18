package com.example.sellingserviceapp.ui.screen.createService.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ServiceTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(fontSize = 20.sp, color = MaterialTheme.colorScheme.onBackground),
    suffix: @Composable() (() -> Unit)? = null,
    trailingIcon: @Composable() (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isSingleLine: Boolean = true,
    isEnabled: Boolean = true,
    isError: Boolean = false,
    isReadOnly: Boolean = false,
    colors: TextFieldColors = TextFieldDefaults.colors(
        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
        unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
        unfocusedIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        disabledContainerColor = Color.Gray,
        disabledPlaceholderColor = Color.Transparent,
        disabledTextColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        errorIndicatorColor = Color.Transparent
    )
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = colors,
        textStyle = textStyle,
        label = {
            Text(label)
        },
        enabled = isEnabled,
        singleLine = isSingleLine,
        isError = isError,
        suffix = suffix,
        readOnly = isReadOnly,
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions
    )
}