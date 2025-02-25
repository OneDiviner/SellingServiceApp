package com.example.sellingserviceapp.ui.component.textfield

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfoTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    errorMessage: String = ""
) {
    val borderColor = if (isSystemInDarkTheme()) {
        Color.White.copy(alpha = 0.3f)
    } else {
        Color.Black.copy(alpha = 0.3f)
    }

    Column {
        OutlinedTextField(
            value = value,
            textStyle = MaterialTheme.typography.bodyMedium,
            onValueChange = { newText ->
                val filteredText = newText.filter { it.isLetter() || it.isWhitespace() }
                onValueChange(filteredText)
            },
            placeholder = {
                Text(
                    placeholder,
                    color = borderColor,
                    style = MaterialTheme.typography.bodyMedium)
            },
            shape = RoundedCornerShape(8.dp),
            modifier = modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = borderColor,
                focusedIndicatorColor = borderColor
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        if (errorMessage.isNotEmpty()) {
            Text(
                modifier = Modifier
                    .padding(5.dp)
                ,
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}