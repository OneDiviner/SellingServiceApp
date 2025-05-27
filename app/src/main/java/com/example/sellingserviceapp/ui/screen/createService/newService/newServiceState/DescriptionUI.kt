package com.example.sellingserviceapp.ui.screen.createService.newService.newServiceState

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.ui.screen.createService.component.ServiceTextField
import com.example.sellingserviceapp.ui.screen.createService.newService.NewServiceUIState
import com.example.sellingserviceapp.ui.screen.createService.newService.NewServiceValidators.validateTitle
import com.example.sellingserviceapp.ui.screen.createService.newService.NewServiceViewModel

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
                    ServiceTextField(
                        value = viewModel.newService.tittle,
                        onValueChange = {
                            viewModel.newService = viewModel.newService.copy(tittle = it)
                            error = viewModel.newService.validateTitle()
                        },
                        label = "Название",
                        isError = error != null,
                    )
                    error?.let { Text(it, fontSize = 12.sp, color = Color.Red) }
                }

            }
            item {
                ServiceTextField(
                    modifier = Modifier.wrapContentHeight(),
                    value = viewModel.newService.description,
                    onValueChange = {
                        viewModel.newService = viewModel.newService.copy(description = it)
                    },
                    label = "Описание",
                    isSingleLine = false,
                    textStyle = TextStyle.Default.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
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