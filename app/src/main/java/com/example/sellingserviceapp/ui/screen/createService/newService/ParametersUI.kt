package com.example.sellingserviceapp.ui.screen.createService.newService

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.displayCutoutPadding
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.ui.component.button.LargeButton
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonModel
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonState
import com.example.sellingserviceapp.ui.screen.createService.CreateServiceViewModel
import com.example.sellingserviceapp.ui.screen.createService.SheetContentState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParametersUI(
    viewModel: CreateServiceViewModel = hiltViewModel()
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
                Text(viewModel.serviceData.tittle, fontSize = 32.sp, color = MaterialTheme.colorScheme.onBackground)
            }
            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    TextField(
                        value = viewModel.serviceData.price,
                        onValueChange = {
                            viewModel.serviceData = viewModel.serviceData.copy(price = it)
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
                        placeholder = {
                            Text("Цена")
                        },
                        enabled = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    val options = viewModel.priceTypeList
                    var expanded by remember { mutableStateOf(false) }
                    var selectedOptionText by remember { mutableStateOf(options[0].priceTypeName) }
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
                    var icon by remember { mutableStateOf<ImageVector>(Icons.Default.Add) }
                    Column {
                        Button(
                            onClick = {
                                icon = Icons.Default.Check
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
                                Text(location.locationName)
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        if(location.isPhysical) {
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
                TextField(
                    value = viewModel.serviceData.duration,
                    onValueChange = {
                        viewModel.serviceData = viewModel.serviceData.copy(duration = it)
                    },
                    //label = { Text("Выберите категорию") },
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
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            item {
                LargeButton(
                    model = ButtonModel(
                        text = "Создать услугу",
                        state = ButtonState.Ready
                    ),
                    onClick = {
                        viewModel.createService()
                        viewModel.isOpen = false
                        viewModel.sheetContentState = SheetContentState.Categories
                    }
                )
            }
        }
    }
}