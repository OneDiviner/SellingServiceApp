package com.example.sellingserviceapp.ui.component.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonState

@Composable
fun LargeButton(
    text: String,
    state: ButtonState,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = state.isClickable
    ) {
        Text(
            text = if(state.isLoading) "Загрузка" else text,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}