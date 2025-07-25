package com.example.sellingserviceapp.ui.screen.createService.userService.editService

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.model.domain.CategoryDomain
import com.example.sellingserviceapp.model.domain.FormatsDomain
import com.example.sellingserviceapp.model.domain.PriceTypeDomain
import com.example.sellingserviceapp.model.domain.ServiceDomain
import com.example.sellingserviceapp.ui.component.button.LargeButton
import com.example.sellingserviceapp.ui.component.circularProgressIndicator.FullScreenCircularProgressIndicator
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonModel
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonState
import com.example.sellingserviceapp.ui.screen.createService.component.ServiceTextField
import com.example.sellingserviceapp.ui.screen.createService.component.SheetStickyHeader

sealed class EditServiceState {
    data object EditService: EditServiceState()
    data object Loading: EditServiceState()
    data object Error: EditServiceState()
}

//TODO: Понять как работает LaunchedEffect и почему мои данные не успевают загрузиться, как сделать экран загрузки

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditServiceUI(
    service: ServiceDomain,
    categories: List<CategoryDomain>,
    formats: List<FormatsDomain>,
    priceTypes: List<PriceTypeDomain>,
    onBackButtonClick: () -> Unit,
    onEditButtonClick: () -> Unit,
    viewModel: EditServiceViewModel = hiltViewModel()
) {
    val subcategories by viewModel.subcategories.collectAsState(emptyList())
    val editService by viewModel.editService.collectAsState()
    val editServiceState by viewModel.editServiceState.collectAsState()

    LaunchedEffect(key1 = service,key2 = categories, key3 = formats) {
        viewModel.initializeEditor(service, categories, formats, priceTypes)
    }

    when(editServiceState) {
        is EditServiceState.Error -> {}
        is EditServiceState.Loading -> {
            FullScreenCircularProgressIndicator()
        }
        is EditServiceState.EditService -> {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    stickyHeader {
                        SheetStickyHeader(
                            title = "Редактирование",
                            onBackButtonClick = onBackButtonClick
                        )
                    }

                    item {
                        ServiceTextField(
                            label = "Название",
                            value = editService.tittle,
                            onValueChange = { viewModel.editTitle(it) }
                        )
                    } //EditTitle

                    item {
                        var expanded by remember { mutableStateOf(false) }
                        val options = viewModel.categories
                        var selectedOptionText by remember { mutableStateOf(options.find { it.code == service.categoryCode }?.name?: "") }

                        ExposedDropdownMenuBox(
                            modifier = Modifier
                                .height(56.dp)
                                .fillMaxWidth(),
                            expanded = expanded,
                            onExpandedChange = {expanded = !expanded}
                        ) {
                            ServiceTextField(
                                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable, true),
                                value = selectedOptionText,
                                onValueChange = {},
                                label = "Категория",
                                isReadOnly = true,
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = expanded
                                    )
                                }
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
                                            viewModel.selectCategory(selectedOption.id)
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    } //EditCategoryDropDown

                    item {
                        var expanded by remember { mutableStateOf(false) }
                        val options = subcategories
                        var selectedOptionText by remember { mutableStateOf(service.subcategoryName)}

                        ExposedDropdownMenuBox(
                            modifier = Modifier
                                .height(56.dp)
                                .fillMaxWidth(),
                            expanded = expanded,
                            onExpandedChange = {expanded = !expanded},
                        ) {
                            ServiceTextField(
                                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable, true),
                                value = selectedOptionText,
                                onValueChange = {},
                                label = "Подкатегория",
                                isReadOnly = true,
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = expanded
                                    )
                                }
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
                                            viewModel.selectSubcategory(selectedOption)
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    } //EditSubcategoryDropDown

                    item {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            ServiceTextField(
                                modifier = Modifier.weight(1f),
                                value = editService.price,
                                onValueChange = {
                                    viewModel.editPrice(it)
                                },
                                label = "Цена",
                                suffix = { Text("₽") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            val options = viewModel.priceTypes
                            var expanded by remember { mutableStateOf(false) }
                            var selectedOptionText by remember { mutableStateOf(options.find { it.id == editService.priceTypeId }?.name ?: "") }

                            ExposedDropdownMenuBox(
                                modifier = Modifier
                                    .height(56.dp)
                                    .wrapContentWidth(),
                                expanded = expanded,
                                onExpandedChange = {expanded = !expanded}
                            ) {
                                ServiceTextField(
                                    modifier = Modifier
                                        .width(150.dp)
                                        .menuAnchor(MenuAnchorType.PrimaryEditable, true),
                                    value = selectedOptionText,
                                    onValueChange = {},
                                    label = "За",
                                    isReadOnly = true,
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = expanded
                                        )
                                    },
                                    textStyle = TextStyle(fontSize = 18.sp),
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
                                                viewModel.selectPriceType(selectedOption)
                                                expanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    } //EditPriceTextField+PriceTypeDropDown

                    item {
                        val options by remember { mutableStateOf(
                            listOf(
                                "15",
                                "30",
                                "45",
                                "60",
                                "75",
                            )
                        )
                        }
                        var expanded by remember { mutableStateOf(false) }
                        var selectedOptionText by remember { mutableStateOf(editService.duration) }
                        ExposedDropdownMenuBox(
                            modifier = Modifier
                                .height(56.dp)
                                .fillMaxWidth(),
                            expanded = expanded,
                            onExpandedChange = {expanded = !expanded}
                        ) {
                            ServiceTextField(
                                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable, true),
                                value = "$selectedOptionText минут",
                                onValueChange = {},
                                label = "Длительность",
                                isReadOnly = true,
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = expanded
                                    )
                                }
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
                                            viewModel.selectDuration(selectedOption)
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    } //EditDurationDropDown

                    item {
                        ServiceTextField(
                            modifier = Modifier.wrapContentHeight(),
                            value = editService.description,
                            onValueChange = {
                                viewModel.editDescription(it)
                            },
                            label = "Описание",
                            isSingleLine = false,
                            textStyle = TextStyle.Default.copy(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal
                            )
                        )
                    } //EditDescription

                    item {
                        val formatsIds = editService.formatsIds.toMutableList()
                        viewModel.formats.forEach { format ->
                            var isSelected by remember { mutableStateOf(false) }

                            LaunchedEffect(format.id, formatsIds) {
                                if (formatsIds.contains(format.id)) {
                                    isSelected = true
                                } else {
                                    isSelected = false
                                }
                            }

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
                                    ServiceTextField(
                                        value = editService.address,
                                        onValueChange = {viewModel.editAddress(it)},
                                        label = "Адресс"
                                    )
                                }
                            }
                        }
                    }

                    item {
                        LargeButton(
                            model = ButtonModel(
                                text = "Изменить услугу",
                                state = ButtonState.Ready
                            ),
                            onClick = {
                                viewModel.updateService()
                                onEditButtonClick()
                            }
                        )
                    }
                }
            }
        }
    }




}