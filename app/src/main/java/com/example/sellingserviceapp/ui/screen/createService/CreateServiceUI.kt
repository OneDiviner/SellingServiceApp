package com.example.sellingserviceapp.ui.screen.createService

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.R
import com.example.sellingserviceapp.ui.component.button.LargeButton
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonModel
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonState
import com.example.sellingserviceapp.ui.screen.profile.specialistProfile1.ScreenState
import java.util.Objects

sealed class CreateServiceUIState {
    object Loading: CreateServiceUIState()
    object Success: CreateServiceUIState()
}

sealed class SheetContentState {
    object Categories: SheetContentState()
    object Subcategories: SheetContentState()
    object Description: SheetContentState()
    object Parametrs: SheetContentState()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateServiceUI(
    viewModel: CreateServiceViewModel = hiltViewModel()
) {
    val uiState by remember { mutableStateOf<CreateServiceUIState>(CreateServiceUIState.Loading) } //TODO: Вынести в ViewModel
    var sheetState by remember { mutableStateOf(false) }
    when(uiState) {
        is CreateServiceUIState.Loading -> {

        }
        is CreateServiceUIState.Success -> {
            
        }
    }
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .displayCutoutPadding()
                .padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Button(
                onClick = {},
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onBackground.copy(0.1f)
                ),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    Text("Поиск", fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground.copy(0.5f))
                }

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Услуги", fontSize = 32.sp)
                Button(
                    onClick = {
                        sheetState = true
                        //viewModel.getCategories()
                              },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .width(150.dp)
                        .height(32.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 15.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                        Text("Новая услуга", fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground)
                    }

                }
            }
        }
        var sheetContentState by remember { mutableStateOf<SheetContentState>(SheetContentState.Categories) }

        if(sheetState) {
            ModalBottomSheet(
                containerColor = MaterialTheme.colorScheme.background,
                onDismissRequest = {sheetState = false},
                modifier = Modifier.fillMaxSize()
            ) {
                when(sheetContentState) {
                    is SheetContentState.Categories -> {
                        Column(
                            modifier = Modifier.padding(15.dp),
                            verticalArrangement = Arrangement.spacedBy(15.dp)
                        ) {
                            Text("Категория", fontSize = 32.sp, color = MaterialTheme.colorScheme.onBackground)
                            viewModel.categories.forEach { category ->
                                Button(
                                    onClick = {
                                        sheetContentState = SheetContentState.Subcategories
                                        viewModel.getSubcategories(categoryId = category.id)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(70.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                                    ),
                                    shape = RoundedCornerShape(20.dp),
                                    contentPadding = PaddingValues(0.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .padding(15.dp)
                                            .fillMaxSize(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Row(
                                            modifier = Modifier,
                                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                painter = painterResource(R.drawable.construction),
                                                contentDescription = null,
                                                modifier = Modifier.size(30.dp),
                                                tint = Color(0xFFFFFFFF)
                                            )
                                            Box {
                                                Text(
                                                    modifier = Modifier,
                                                    text = category.name,
                                                    fontSize = 16.sp,
                                                    color = MaterialTheme.colorScheme.onBackground
                                                )
                                            }
                                        }
                                        Icon(
                                            painter = painterResource(R.drawable.arrow_forward_ios),
                                            contentDescription = null,
                                            modifier = Modifier.size(24.dp),
                                            tint = Color(0xFFFFFFFF)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    is SheetContentState.Subcategories -> {
                        Column(
                            modifier = Modifier.padding(15.dp),
                            verticalArrangement = Arrangement.spacedBy(15.dp)
                        ) {
                            Text("Подкатегория", fontSize = 32.sp, color = MaterialTheme.colorScheme.onBackground)
                            viewModel.subcategories.forEach { subcategory ->
                                Button(
                                    onClick = {
                                        sheetContentState = SheetContentState.Description
                                        viewModel.serviceData = viewModel.serviceData.copy(subcategoryId = subcategory.id)
                                        //viewModel.getSubcategories(categoryId = category.id)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(70.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                                    ),
                                    shape = RoundedCornerShape(20.dp),
                                    contentPadding = PaddingValues(0.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .padding(15.dp)
                                            .fillMaxSize(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Row(
                                            modifier = Modifier,
                                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                painter = painterResource(R.drawable.construction),
                                                contentDescription = null,
                                                modifier = Modifier.size(30.dp),
                                                tint = Color(0xFFFFFFFF)
                                            )
                                            Box {
                                                Text(
                                                    modifier = Modifier,
                                                    text = subcategory.name,
                                                    fontSize = 16.sp,
                                                    color = MaterialTheme.colorScheme.onBackground
                                                )
                                            }
                                        }
                                        Icon(
                                            painter = painterResource(R.drawable.arrow_forward_ios),
                                            contentDescription = null,
                                            modifier = Modifier.size(24.dp),
                                            tint = Color(0xFFFFFFFF)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    is SheetContentState.Description -> {
                        Column(
                            modifier = Modifier.padding(15.dp),
                            verticalArrangement = Arrangement.spacedBy(15.dp)
                        ) {
                            Text(viewModel.subcategories.firstOrNull{it.id == viewModel.serviceData.subcategoryId}?.name?: "", fontSize = 32.sp, color = MaterialTheme.colorScheme.onBackground)
                            TextField(
                                value = viewModel.serviceData.tittle,
                                onValueChange = {
                                    viewModel.serviceData = viewModel.serviceData.copy(tittle = it)
                                },
                                modifier = Modifier
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
                                placeholder = {
                                    Text("Название услуги")
                                },
                                enabled = true
                            )
                            TextField(
                                value = viewModel.serviceData.description,
                                onValueChange = {
                                    viewModel.serviceData = viewModel.serviceData.copy(description = it)
                                },
                                modifier = Modifier
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
                                placeholder = {
                                    Text("Описание")
                                },
                                enabled = true
                            )
                            Button(
                                onClick = {},
                                modifier = Modifier
                                    .size(100.dp),
                                shape = RoundedCornerShape(20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .fillMaxSize(),
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                            }
                            LargeButton(
                                model = ButtonModel("Продолжить", ButtonState.Ready)
                            ) {
                                sheetContentState = SheetContentState.Parametrs
                            }
                        }
                    }
                    is SheetContentState.Parametrs -> {
                        Column(
                            modifier = Modifier.padding(15.dp),
                            verticalArrangement = Arrangement.spacedBy(15.dp)
                        ) {
                            Text(viewModel.serviceData.tittle, fontSize = 32.sp, color = MaterialTheme.colorScheme.onBackground)
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(15.dp)
                            ) {
                                TextField(
                                    value = viewModel.serviceData.price.toString(),
                                    onValueChange = {
                                        viewModel.serviceData = viewModel.serviceData.copy(price = it.toInt())
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
                                val options = viewModel.priceTypes
                                var expanded by remember { mutableStateOf(false) }
                                var selectedOptionText by remember { mutableStateOf(options[0].name) }
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
                                                text = {Text(text = selectedOption.name) },
                                                onClick = {
                                                    selectedOptionText = selectedOption.name
                                                    viewModel.serviceData = viewModel.serviceData.copy(priceTypeId = selectedOption.id)
                                                    expanded = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                            var locationTypeIds by remember { mutableStateOf(listOf<Int>()) }
                            viewModel.locationTypes.forEach { location ->
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
                                            Text(location.name)
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
                                            )
                                        )
                                    }
                                }
                            }
                            TextField(
                                value = viewModel.serviceData.duration.toString(),
                                onValueChange = {
                                    viewModel.serviceData = viewModel.serviceData.copy(duration = it.toInt())
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
                            LargeButton(
                                model = ButtonModel(
                                    text = "Создать услугу",
                                    state = ButtonState.Ready
                                ),
                                onClick = {
                                    viewModel.createService()
                                    sheetState = false
                                    sheetContentState = SheetContentState.Categories
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

//TODO: Сделать время кратным 15