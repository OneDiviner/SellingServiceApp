package com.example.sellingserviceapp.ui.component.textfield


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.sellingserviceapp.R
import com.example.sellingserviceapp.ui.screen.authentication.state.TextFieldModel
import com.example.sellingserviceapp.ui.screen.authentication.state.TextFieldState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LargePasswordTextField(
    model: TextFieldModel,
    onValueChange: (String) -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column {
        TextField(
            value = model.value,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = when (model.state) {
                    is TextFieldState.Error -> Color.Red
                    else -> Color.Unspecified
                }
            ),
            onValueChange = onValueChange,
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
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer.copy(0.7f),
                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible) R.drawable.visibility_off else R.drawable.visibility
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        modifier = Modifier.size(28.dp),
                        painter = painterResource(id = image),
                        contentDescription = if (passwordVisible) "Скрыть пароль" else "Показать пароль",
                        tint = MaterialTheme.colorScheme.onBackground.copy(0.7f)
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
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