package com.example.sellingserviceapp.ui.screen.createService.newService.newServiceState

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.ui.component.button.LargeButton
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonModel
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonState
import com.example.sellingserviceapp.ui.screen.createService.CreateServiceViewModel
import com.example.sellingserviceapp.ui.screen.createService.newService.NewServiceUIState
import com.example.sellingserviceapp.ui.screen.createService.newService.NewServiceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParametersUI(
    viewModel: NewServiceViewModel = hiltViewModel(),
    createServiceViewModel: CreateServiceViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
    ) {
        LazyColumn (
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            item {
                Box(modifier = Modifier.fillMaxSize()) {
                    BottomSheetDefaults.DragHandle(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                    )
                }
            }
            item {
                Text(viewModel.newService.tittle, fontSize = 32.sp, color = MaterialTheme.colorScheme.onBackground)
            }
            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    TextField(
                        value = viewModel.newService.price,
                        onValueChange = {
                            viewModel.newService = viewModel.newService.copy(price = it)
                        },
                        textStyle = TextStyle(fontSize = 20.sp, color = MaterialTheme.colorScheme.onBackground),
                        modifier = Modifier
                            .height(56.dp)
                            .weight(1f),
                        shape = RoundedCornerShape(20.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            disabledContainerColor = Color.Gray,
                            disabledPlaceholderColor = Color.Transparent,
                            disabledTextColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        placeholder = {
                            Text("Цена", fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground.copy(0.7f))
                        },
                        suffix = {Text("₽")},
                        enabled = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    val options = viewModel.priceTypes
                    var expanded by remember { mutableStateOf(false) }
                    var selectedOptionText by remember { mutableStateOf(options[0].name) }
                    viewModel.newService = viewModel.newService.copy(priceTypeId = options[0].id)
                    ExposedDropdownMenuBox(
                        modifier = Modifier
                            .height(56.dp)
                            .wrapContentWidth(),
                        expanded = expanded,
                        onExpandedChange = {expanded = !expanded}
                    ) {
                        TextField(
                            readOnly = true,
                            value = "За $selectedOptionText",
                            onValueChange = {},
                            textStyle = TextStyle(fontSize = 18.sp),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expanded
                                )
                            },
                            modifier = Modifier
                                .width(150.dp)
                                .menuAnchor(MenuAnchorType.PrimaryEditable, true),
                            shape = RoundedCornerShape(20.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer.copy(0.5f),
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                errorContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                errorIndicatorColor = Color.Transparent,
                                cursorColor = Color.Transparent,
                                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                                unfocusedTextColor = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                                disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                errorTextColor = MaterialTheme.colorScheme.error
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = {expanded = false}
                        ) {
                            options.forEach{ selectedOption ->
                                DropdownMenuItem(
                                    text = { Text(text = selectedOption.name) },
                                    onClick = {
                                        selectedOptionText = selectedOption.name
                                        viewModel.newService = viewModel.newService.copy(priceTypeId = selectedOption.id)
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
            item {
                val options = viewModel.duration
                var expanded by remember { mutableStateOf(false) }
                var selectedOptionText by remember { mutableStateOf(options[0]) }
                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth(),
                    expanded = expanded,
                    onExpandedChange = {expanded = !expanded}
                ) {
                    TextField(
                        readOnly = true,
                        value = "$selectedOptionText минут",
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
                        options.forEach{ selectedOption ->
                            DropdownMenuItem(
                                text = { Text(text = selectedOption) },
                                onClick = {
                                    selectedOptionText = selectedOption
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
            item {
                val formatsIds = viewModel.newService.formatsIds.toMutableList()
                viewModel.formats.forEach { format ->
                    var isSelected by remember { mutableStateOf(false) }

                    Column {
                        Button(
                            onClick = {
                                isSelected = !isSelected
                                if (isSelected) {
                                    if (!formatsIds.contains(format.id)) {
                                        formatsIds.add(format.id)
                                    }
                                } else {
                                    formatsIds.remove(format.id)
                                }
                                viewModel.selectFormats(formatsIds)
                            },
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent
                            )
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(format.name, fontSize = 18.sp, fontWeight = FontWeight.Normal)
                                Icon(
                                    imageVector = if (isSelected) { Icons.Default.Check } else { Icons.Default.Add },
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        if(format.isPhysical && isSelected) {
                            TextField(
                                value = viewModel.newService.address,
                                onValueChange = {
                                    viewModel.newService = viewModel.newService.copy(address = it)
                                },
                                placeholder = {
                                    Text("Адресс", fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground.copy(0.7f))
                                },
                                modifier = Modifier
                                    .height(56.dp)
                                    .fillMaxWidth(),
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
                        }
                    }
                }
            }
            item {
                LargeButton(
                    model = ButtonModel(
                        text = "Создать услугу",
                        state = ButtonState.Ready
                    ),
                    onClick = {
                        viewModel.createService()
                        createServiceViewModel.isOpen = false
                        viewModel.newServiceUIState = NewServiceUIState.Categories
                    }
                )
            }
        }
    }
}