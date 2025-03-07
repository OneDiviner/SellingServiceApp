package com.example.sellingserviceapp.ui.component.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonModel
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonState

@Composable
fun LargeButton(
    model: ButtonModel,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = when (model.state) {
            is ButtonState.Ready -> true
            is ButtonState.Default -> false
            is ButtonState.Loading -> false
            is ButtonState.Error -> false
        }
    ) {
        when (model.state) {
            is ButtonState.Ready -> Text(
                model.text,
                style = MaterialTheme.typography.bodyLarge
            )

            is ButtonState.Default -> Text(
                model.text,
                style = MaterialTheme.typography.bodyLarge
            )
            is ButtonState.Loading -> CircularProgressIndicator(
                modifier = Modifier.size(28.dp),
                strokeWidth = 2.dp,
                color = MaterialTheme.colorScheme.onBackground
            )
            is ButtonState.Error -> Text(
                    text = model.state.error,
                    color = Color.Red,
                    style = MaterialTheme.typography.titleSmall
                )
        }
    }
}