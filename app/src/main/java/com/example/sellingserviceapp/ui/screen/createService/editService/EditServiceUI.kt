package com.example.sellingserviceapp.ui.screen.createService.editService

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.ui.screen.createService.CreateServiceViewModel
import com.example.sellingserviceapp.ui.screen.createService.SheetContentState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditServiceUI(
    viewModel: CreateServiceViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            item {
                Box(modifier = Modifier.fillMaxSize()) {
                    BottomSheetDefaults.DragHandle(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                    )
                    IconButton(
                        onClick = {
                            //viewModel.sheetContentState = SheetContentState.Service
                        },
                        modifier = Modifier
                            .align(Alignment.TopStart)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier
                                .size(28.dp)
                        )
                    }
                }
            }
            item {
                Text("Редактирование", fontSize = 32.sp, color = MaterialTheme.colorScheme.onBackground)
            }
            item {
                TextField(
                    value = viewModel.service.tittle,
                    onValueChange = {

                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                        unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        disabledContainerColor = Color.Gray,
                        disabledPlaceholderColor = Color.Transparent,
                        disabledTextColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    placeholder = {
                        Text("Название услуги")
                    },
                    enabled = true
                )
            }
            item {
                val options = viewModel.categoryList
                var expanded by remember { mutableStateOf(false) }
                var selectedOptionText by remember { mutableStateOf(options[0].categoryName) }
                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth(),
                    expanded = expanded,
                    onExpandedChange = {expanded = !expanded}
                ) {
                    TextField(
                        readOnly = true,
                        value = selectedOptionText,
                        onValueChange = {},
                        //label = { Text("Выберите категорию") },
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
                                text = { Text(text = selectedOption.categoryName) },
                                onClick = {
                                    selectedOptionText = selectedOption.categoryName
                                    viewModel.getSubcategories(selectedOption.id)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
            item {
                val options = viewModel.subcategoryList
                var expanded by remember { mutableStateOf(false) }
                var selectedOptionText by remember { mutableStateOf("") }
                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth(),
                    expanded = expanded,
                    onExpandedChange = {expanded = !expanded},
                ) {
                    TextField(
                        readOnly = true,
                        value = selectedOptionText,
                        onValueChange = {},
                        label = { Text("Выберите категорию") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded
                            )
                        },
                        enabled = viewModel.subcategoryList.isNotEmpty(),
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
                                text = { Text(text = selectedOption.subcategoryName) },
                                onClick = {
                                    selectedOptionText = selectedOption.subcategoryName
                                    //viewModel.serviceData = viewModel.serviceData.copy(priceTypeId = selectedOption.id)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
            item {
                TextField(
                    value = viewModel.service.description,
                    onValueChange = {

                    },
                    textStyle = TextStyle.Default.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                        unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        disabledContainerColor = Color.Gray,
                        disabledPlaceholderColor = Color.Transparent,
                        disabledTextColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    label = { Text("Описание") },
                    placeholder = {
                        Text("Описание")
                    },
                    enabled = true
                )
            }
            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    val options = viewModel.priceTypeList
                    var expanded by remember { mutableStateOf(false) }
                    var selectedOptionText by remember { mutableStateOf(viewModel.service.priceType) }
                    TextField(
                        value = viewModel.service.price,
                        onValueChange = {
                            //viewModel.serviceData = viewModel.serviceData.copy(price = it)
                        },
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
                        label = {
                            Text("Цена")
                        },
                        suffix = { Text("₽ за $selectedOptionText") },
                        enabled = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    ExposedDropdownMenuBox(
                        modifier = Modifier
                            .height(56.dp)
                            .width(120.dp),
                        expanded = expanded,
                        onExpandedChange = {expanded = !expanded}
                    ) {
                        TextField(
                            readOnly = true,
                            value = selectedOptionText,
                            onValueChange = {},
                            //label = { Text("Выберите категорию") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expanded
                                )
                            },
                            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable, true),
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
                                    text = { Text(text = selectedOption.priceTypeName) },
                                    onClick = {
                                        selectedOptionText = selectedOption.priceTypeName
                                        viewModel.serviceData = viewModel.serviceData.copy(priceTypeId = selectedOption.id)
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
            item {
                var locationTypeIds by remember { mutableStateOf(listOf<Int>()) }

                viewModel.locationTypeList.forEach { location ->
                    var isSelected by remember { mutableStateOf(false) }
                    Column {
                        Button(
                            onClick = {
                                isSelected = !isSelected
                                locationTypeIds = locationTypeIds.toMutableList().apply { add(location.id) }
                                viewModel.serviceData = viewModel.serviceData.copy(locationTypeIds = locationTypeIds)
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
                                Text(location.code)
                                Icon(
                                    imageVector = if (isSelected) { Icons.Default.Check } else { Icons.Default.Add },
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        if(location.isPhysical && isSelected) {
                            TextField(
                                value = viewModel.serviceData.address,
                                onValueChange = {
                                    viewModel.serviceData = viewModel.serviceData.copy(address = it)
                                },
                                label = { Text("Укажите адресс") },
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
                val options by remember { mutableStateOf(
                    listOf(
                        "15 минут",
                        "30 минут",
                        "45 минут",
                        "1 час",
                        "1 час 15 минут",
                    )
                )
                }
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
                        value = selectedOptionText,
                        onValueChange = {},
                        //label = { Text("Выберите категорию") },
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
        }
    }
}