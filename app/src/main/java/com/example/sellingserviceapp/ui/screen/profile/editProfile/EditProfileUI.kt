package com.example.sellingserviceapp.ui.screen.profile.editProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.ui.component.button.LargeButton
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonModel
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonState
import com.example.sellingserviceapp.ui.screen.profile.ProfileSheetState
import com.example.sellingserviceapp.ui.screen.profile.ProfileViewModel
import com.example.sellingserviceapp.ui.screen.profile.editProfile.model.UserDataTextField


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileUI(
    viewModel: ProfileViewModel = hiltViewModel()
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
                }
            }

            item {
                IconButton(
                    onClick = {
                        viewModel.profileSheetState = ProfileSheetState.Profile
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

            item {
                Text("Редактирование", fontSize = 32.sp, color = MaterialTheme.colorScheme.onBackground)
            }
            item {
                UserDataTextField(
                    value = viewModel.newUserData.secondName?: "",
                    onValueChange = {
                        viewModel.onSecondNameChanged(it)
                    },
                    label = "Фамилия"
                )
            }
            item {
                UserDataTextField(
                    value = viewModel.newUserData.firstName?: "",
                    onValueChange = {
                        viewModel.onFirstNameChanged(it)
                    },
                    label = "Имя"
                )
            }
            item {
                UserDataTextField(
                    value = viewModel.newUserData.lastName?: "",
                    onValueChange = {
                        viewModel.onLastNameChanged(it)
                    },
                    label = "Отчество"
                )
            }
            item {
                TextField(
                    value = viewModel.newUserData.phoneNumber?: "",
                    onValueChange = {
                        viewModel.onPhoneNumberChanged(it)
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
                    label = {
                        Text("Телефон")
                    },
                    prefix = {
                        Text("+7 ")
                    },
                    enabled = true
                )
            }
            item {
                LargeButton(
                    model = ButtonModel(
                        text = "Сохранить изменения",
                        state = ButtonState.Ready
                    ),
                    onClick = {
                        viewModel.updateUser()
                        viewModel.profileSheetState = ProfileSheetState.Profile
                    }
                )
            }
        }
    }
}