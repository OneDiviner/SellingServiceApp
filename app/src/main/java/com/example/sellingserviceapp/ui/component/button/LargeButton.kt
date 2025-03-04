package com.example.sellingserviceapp.ui.component.button

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonState

@Composable
fun LargeButton(
    state: ButtonState,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = when (state) {
            is ButtonState.Default -> state.isClickable
            else -> false // Кнопка неактивна в состояниях Loading и Error
        }
    ) {
        when (state) {
            is ButtonState.Default -> Text(
                state.text,
                style = MaterialTheme.typography.bodyLarge
            )
            is ButtonState.Loading -> CircularProgressIndicator(
                modifier = Modifier.size(28.dp),
                strokeWidth = 2.dp,
                color = MaterialTheme.colorScheme.onBackground
            )
            is ButtonState.Error -> state.error?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}