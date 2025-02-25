package com.example.sellingserviceapp.ui.component.text

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TittleSmall(
    text: String,
    textAlign: TextAlign,
    padding: PaddingValues
) {
    val color = if (isSystemInDarkTheme()) {
        Color.White.copy(alpha = 0.5f)
    } else {
        Color.Black.copy(alpha = 0.5f)
    }
    Text(
        modifier = Modifier
            .padding(padding)
            .fillMaxWidth(),
        text = text,
        style = MaterialTheme.typography.titleSmall,
        color = color,
        textAlign = textAlign
    )
}