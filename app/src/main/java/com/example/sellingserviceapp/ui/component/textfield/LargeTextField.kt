package com.example.sellingserviceapp.ui.component.textfield

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.sellingserviceapp.R
import com.example.sellingserviceapp.ui.screen.authentication.state.TextFieldModel
import com.example.sellingserviceapp.ui.screen.authentication.state.TextFieldState

//TODO: Подумать над типом поля чтобы не создавать кучу разных TextField

@Composable
fun LargeTextField(
    iconID: Int? = null,
    model: TextFieldModel,
    onValueChange: (String) -> Unit
) {
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            trailingIcon = if (iconID != null) {
                {
                    Icon(
                        modifier = Modifier.size(28.dp),
                        painter = painterResource(id = iconID),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground.copy(0.7f)
                    )
                }
            } else {
                null
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