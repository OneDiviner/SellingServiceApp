package com.example.sellingserviceapp.ui.screen.createService.newService.newServiceState

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.R
import com.example.sellingserviceapp.ui.component.button.LargeButton
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonModel
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonState
import com.example.sellingserviceapp.ui.screen.createService.CreateServiceViewModel
import com.example.sellingserviceapp.ui.screen.createService.newService.NewServiceUIState
import com.example.sellingserviceapp.ui.screen.createService.newService.NewServiceViewModel
import com.example.sellingserviceapp.ui.screen.profile.PickTime
import com.example.sellingserviceapp.ui.screen.profile.PickTimeState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParametersUI(
    viewModel: NewServiceViewModel = hiltViewModel(),
    createServiceViewModel: CreateServiceViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit
) {

    val error by viewModel.error.collectAsState()
    var showTimePicker by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding()
    ) {
        if (showTimePicker) {
            var enabled by remember { mutableStateOf(false) }
            PickTime(
                title = "Длительность услуги",
                initHour = viewModel.hour,
                initMinute = viewModel.minute,
                onTimeSelected = { hour, minute ->
                    viewModel.duration = hour * 60 + minute
                    if (viewModel.duration >= 15) {
                        enabled = true
                        viewModel.hour = hour.toString()
                        viewModel.minute = minute.toString()
                        viewModel.newService.copy(duration = viewModel.duration.toString())
                    } else {

                        enabled = false
                    }
                },
                onDismissRequest = { showTimePicker = false },
                onDismissButtonCLick = { showTimePicker = false },
                onConfirmButtonClick = {
                    if (viewModel.duration >= 15) {
                        viewModel.newService = viewModel.newService.copy(duration = viewModel.duration.toString())
                        Log.d("DURATION", viewModel.duration.toString())
                        showTimePicker = false
                    }
                },
                isButtonEnabled = enabled
            )
        }
        LazyColumn (
            verticalArrangement = Arrangement.spacedBy(20.dp)
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
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBackButtonClick,
                        modifier = Modifier
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier
                                .size(28.dp),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Text(viewModel.newService.tittle, fontSize = 32.sp, color = MaterialTheme.colorScheme.onBackground)
                }
            }
            item {
                Row(
                    modifier = Modifier.padding(horizontal = 0.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    TextField(
                        singleLine = true,
                        value = viewModel.newService.price,
                        onValueChange = {
                            viewModel.onPriceChanged(it)
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
                        label = {
                            Text("Цена")
                        },
                        suffix = {Text("₽")},
                        enabled = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    val options = viewModel.priceTypes
                    var expanded by remember { mutableStateOf(false) }
                    var selectedOptionText by remember { mutableStateOf(options[0].name) }

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
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(20.dp))
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        "Длительность услуги",
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(15.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(start = 15.dp, end = 15.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            readOnly = true,
                            value = "${viewModel.hour} ч",
                            onValueChange = {},
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .weight(1f)
                                .clickable(enabled = false, onClick = {}),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = MaterialTheme.colorScheme.background.copy(0.7f),
                                focusedContainerColor = MaterialTheme.colorScheme.background.copy(0.7f),
                                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                disabledContainerColor = Color.Gray,
                                disabledPlaceholderColor = Color.Transparent,
                                disabledTextColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            )
                        )
                        Text(":", fontSize = 30.sp)
                        TextField(
                            readOnly = true,
                            value = "${viewModel.minute} мин",
                            onValueChange = {},
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .weight(1f)
                                .clickable(enabled = false, onClick = {}),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = MaterialTheme.colorScheme.background.copy(0.7f),
                                focusedContainerColor = MaterialTheme.colorScheme.background.copy(0.7f),
                                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                disabledContainerColor = Color.Gray,
                                disabledPlaceholderColor = Color.Transparent,
                                disabledTextColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            )
                        )
                    }
                    Button(
                        onClick = {showTimePicker = true},
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
                            .fillMaxWidth()
                            .height(44.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text ="Изменить",
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                        }
                    }
                }
            }
            item {
                val formatsIds = viewModel.newService.formatsIds.toMutableList()
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(20.dp))
                ) {
                    Text(
                        "Формат оказания услуги",
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(15.dp)
                    )
                    viewModel.formats.forEach { format ->
                        var isSelected by remember { mutableStateOf(false) }
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
                                .padding(horizontal = 15.dp, vertical = 5.dp)
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.background.copy(0.7f),
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
                                    .padding(start = 15.dp, end = 15.dp)
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(20.dp),
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                                singleLine = true,
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = MaterialTheme.colorScheme.background.copy(0.7f),
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
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
            item {
                Column(
                    modifier = Modifier.padding(top = 5.dp, start = 15.dp, end = 15.dp, bottom = 15.dp)
                ) {
                    error?.let { Text(it, fontSize = 14.sp, color = Color.Red, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) }
                    Button(
                        onClick = {
                            viewModel.createService()
                            createServiceViewModel.isOpen = false
                            viewModel.newServiceUIState = NewServiceUIState.Categories
                        },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        enabled = viewModel.newService.price.isNotBlank() && viewModel.newService.formatsIds != emptyList<Int>(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(0.5f)
                        )
                    ) {
                        Text("Создать услугу", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}