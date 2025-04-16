package com.example.sellingserviceapp.ui.component.textfield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.sellingserviceapp.ui.screen.authentication.state.TextFieldModel
import com.example.sellingserviceapp.ui.screen.authentication.state.TextFieldState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LargeDigitTextField(
    model: TextFieldModel,
    onValueChange: (String) -> Unit
) {
    Column {
        OutlinedTextField(
            value = model.value,
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = when (model.state) {
                is TextFieldState.Error -> Color.Red
                else -> Color.Unspecified
            }
            ),
            onValueChange = { newText ->
                if (newText.all { it.isDigit() }) {
                    onValueChange(newText)
                }
            },
            placeholder = {
                Text(
                    model.placeholder,
                    color = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                    style = MaterialTheme.typography.bodyMedium) //TODO: Сделать стиль текста для placeholder
            },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.background.copy(0.7f),
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            trailingIcon = {
                if(model.state is TextFieldState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onBackground.copy(0.7f)
                    )
                }
            }
        )
        if (model.state is TextFieldState.Error) {
            Text(
                modifier = Modifier
                    .padding(5.dp)
                ,
                text = model.state.error,
                color = Color.Red,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}