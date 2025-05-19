package com.example.sellingserviceapp.ui.screen.createService.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
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
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ServiceDropDown(
    options: List<T>
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options[0]) }
    ExposedDropdownMenuBox(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth(),
        expanded = expanded,
        onExpandedChange = {expanded = !expanded}
    ) {
        TextField(
            readOnly = true,
            value = "$selectedOption минут",
            onValueChange = {},
            label = { Text("Длительность") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryEditable, true),
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledContainerColor = Color.Gray,
                disabledPlaceholderColor = Color.Transparent,
                disabledTextColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {expanded = false}
        ) {
            options.forEach{ option ->
                DropdownMenuItem(
                    text = { Text(text = option.toString()) },
                    onClick = {
                        selectedOption = option
                        expanded = false
                    }
                )
            }
        }
    }
}