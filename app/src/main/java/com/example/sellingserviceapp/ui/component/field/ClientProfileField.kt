package com.example.sellingserviceapp.ui.component.field

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ClientProfileFieldModel(
    val value: String,
    val label: String? = null,
    val placeholder: String? = null,
    val icon: ImageVector? = null,
    val onValueChange: (String) -> Unit
)

@Composable
fun ClientProfileField(
    tittle: String? = null,
    fieldModel: List<ClientProfileFieldModel>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        if (tittle != null) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = tittle,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            fieldModel.forEachIndexed{ index, field ->
                var isFocused by remember { mutableStateOf(false) }
                val labelSize by animateDpAsState(
                    targetValue = if (isFocused && field.value.isBlank() || isFocused && field.value.isNotBlank() || !isFocused && field.value.isNotBlank())
                        12.dp
                    else
                        16.dp
                )
                OutlinedTextField(
                    textStyle = TextStyle(
                        fontSize = 18.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                        },
                    value = field.value,
                    onValueChange = field.onValueChange,
                    placeholder = if (field.placeholder != null) {
                        {
                            Text(
                                text = field.placeholder, //TODO: Заменить на текстовый ресурс
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Left
                            )
                        }
                    } else {
                        null
                    },
                    singleLine = true,
                    label =
                        {
                            Text(
                                text = field.label ?: "", //TODO: Заменить на текстовый ресурс
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = labelSize.value.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Left,
                            )
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                        focusedIndicatorColor = MaterialTheme.colorScheme.outline
                    ),
                    shape = RoundedCornerShape(12.dp),

                    leadingIcon = if (field.icon != null) { // Условный рендеринг leadingIcon
                        {
                            Icon(
                                imageVector = field.icon,
                                contentDescription = "SpecialistProfileImage",
                                modifier = Modifier.size(20.dp),
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    } else {
                        null // Если иконки нет, leadingIcon не передается
                    }
                )
            }
        }
    }
}