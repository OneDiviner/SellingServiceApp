package com.example.sellingserviceapp.ui.component.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchTextField(
    value: String,
    onValueChanged: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    TextField(
        value = value,
        onValueChange = onValueChanged,
        modifier = Modifier
            .systemBarsPadding()
            .padding(top = 15.dp)
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.onBackground.copy(0.1f),
            focusedContainerColor = MaterialTheme.colorScheme.onBackground.copy(0.2f),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = { focusManager.clearFocus() }
        ),
        textStyle = LocalTextStyle.current.copy(
            fontSize = 16.sp,
            lineHeight = 20.sp
        ),
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
        },
        placeholder = {
            Text("Поиск", fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground.copy(0.5f))
        }
    )
}