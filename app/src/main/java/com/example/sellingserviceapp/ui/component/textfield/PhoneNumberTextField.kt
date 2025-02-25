package com.example.sellingserviceapp.ui.component.textfield

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.w3c.dom.Text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneNumberTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    placeholder: String
) {
    val borderColor = if (isSystemInDarkTheme()) {
        Color.White.copy(alpha = 0.3f)
    } else {
        Color.Black.copy(alpha = 0.3f)
    }

    OutlinedTextField(
        value = value,
        textStyle = MaterialTheme.typography.bodyMedium,
        onValueChange = { newText ->
            if (newText.text.all { it.isDigit() }) {
                onValueChange(newText)
            }
        },
        placeholder = {
            Text(
                placeholder,
                color = borderColor,
                style = MaterialTheme.typography.bodyMedium)
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Phone
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = borderColor,
            focusedIndicatorColor = borderColor
        )
    )
}

/**
 * Функция форматирования номера телефона в формате: +7 (XXX) XXX-XX-XX
 */
fun formatPhoneNumber(input: TextFieldValue): TextFieldValue {
    val digits = input.text.filter { it.isDigit() } // Оставляем только цифры
    val countryCode = "+"
   // val cursorPosition = input.selection.start // Запоминаем позицию курсора

    var formattedText = when {
        digits.length < 1 -> countryCode
        digits.length <= 1 -> countryCode
        digits.length <= 4 -> "$countryCode (${digits.substring(1)}"
        digits.length <= 7 -> "$countryCode (${digits.substring(1, 4)}) ${digits.substring(4)}"
        digits.length <= 9 -> "$countryCode (${digits.substring(1, 4)}) ${digits.substring(4, 7)}-${digits.substring(7)}"
        digits.length <= 11 -> "$countryCode (${digits.substring(1, 4)}) ${digits.substring(4, 7)}-${digits.substring(7, 9)}-${digits.substring(9)}"
        else -> "$countryCode (${digits.substring(1, 4)}) ${digits.substring(4, 7)}-${digits.substring(7, 9)}-${digits.substring(9, 11)}"
    }

    /*// Вычисляем новую позицию курсора после форматирования
    val newCursorPosition = when {
        cursorPosition >= formattedText.length -> formattedText.length
        cursorPosition < 0 -> 0
        else -> cursorPosition
    }*/

    return TextFieldValue(
        text = formattedText
    )
}