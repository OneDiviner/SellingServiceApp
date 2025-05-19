package com.example.sellingserviceapp.ui.screen.createService.newService

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.room.util.TableInfo
import com.example.sellingserviceapp.ui.component.button.LargeButton
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonModel
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonState
import com.example.sellingserviceapp.ui.screen.createService.CreateServiceViewModel
import com.example.sellingserviceapp.ui.screen.createService.SheetContentState
import com.example.sellingserviceapp.ui.screen.createService.newService.NewServiceValidators.validateTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DescriptionUI(
    viewModel: NewServiceViewModel = hiltViewModel()
) {
    var error by remember { mutableStateOf<String?>(null) }
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
                Text(viewModel.newService.subcategoryName, fontSize = 32.sp, color = MaterialTheme.colorScheme.onBackground)
            }
            item {

                Column {
                    TextField(
                        value = viewModel.newService.tittle,
                        onValueChange = {
                            viewModel.newService = viewModel.newService.copy(tittle = it)
                            error = viewModel.newService.validateTitle()
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
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            errorTextColor = Color.Red
                        ),
                        placeholder = {
                            Text("Название услуги")
                        },
                        isError = error != null,
                        enabled = true
                    )
                    error?.let { Text(it, fontSize = 12.sp, color = Color.Red) }
                }

            }
            item {
                TextField(
                    value = viewModel.newService.description,
                    onValueChange = {
                        viewModel.newService = viewModel.newService.copy(description = it)
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
            }
            item {
                Button(
                    onClick = { viewModel.newServiceUIState = NewServiceUIState.Parameters },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    enabled = error == null,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = MaterialTheme.colorScheme.primary.copy(0.5f)
                    )
                ) {
                    Text("Продолжить", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}