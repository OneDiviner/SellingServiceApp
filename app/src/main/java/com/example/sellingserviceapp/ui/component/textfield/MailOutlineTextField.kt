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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.sellingserviceapp.ui.screen.authentication.state.TextFieldState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MailOutlinedTextField(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    placeholder: String,
    onValueChange: (String) -> Unit
) {
    val borderColor = if (isSystemInDarkTheme()) {
        Color.White.copy(alpha = 0.3f)
    } else {
        Color.Black.copy(alpha = 0.3f)
    }

    Column {
        OutlinedTextField(
            value = state.text,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color =
                if (state.error.isNotEmpty()) Color.Red
                else Color.Unspecified
            ),
            onValueChange = onValueChange,
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        if (state.error.isNotEmpty()) {
            Text(
                modifier = Modifier
                    .padding(5.dp)
                ,
                text = state.error,
                color = Color.Red,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}