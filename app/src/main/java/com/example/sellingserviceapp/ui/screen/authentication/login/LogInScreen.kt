package com.example.sellingserviceapp.ui.screen.authentication.login

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sellingserviceapp.R
import com.example.sellingserviceapp.ui.component.button.LargeButton
import com.example.sellingserviceapp.ui.component.button.BorderlessLargeButton
import com.example.sellingserviceapp.ui.component.text.TittleLarge
import com.example.sellingserviceapp.ui.component.text.TittleSmall
import com.example.sellingserviceapp.ui.component.textfield.LargeMailTextField
import com.example.sellingserviceapp.ui.component.textfield.LargePasswordTextField

sealed class BottomSheetState {
    object isEmail: BottomSheetState()
    object isCode: BottomSheetState()
    object isPassword: BottomSheetState()
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LogInScreen(
    paddingValues: PaddingValues,
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavController
) {
    var isOpen by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(),
        contentAlignment = Alignment.TopStart
    ) {
        Column(modifier = Modifier
            .padding(horizontal = 15.dp)
            .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            TittleLarge(
                text = stringResource(R.string.login),
                TextAlign.Start,
                padding = PaddingValues(0.dp)
            )

            LargeMailTextField(
                //iconID = R.drawable.visibility,
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                model = viewModel.email,
                onValueChange = { viewModel.onEmailChanged(it) },
                modifier = Modifier
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                LargePasswordTextField(
                    model = viewModel.password,
                    onValueChange = { viewModel.onPasswordChanged(it) },
                )
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    TextButton(
                        modifier = Modifier,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.onBackground.copy(0.7f)
                        ),
                        onClick = {isOpen = true}
                    ) {
                        Text(text = "Восстановить пароль")
                    }
                }
            }

            LargeButton(
                model = viewModel.loginButton,
                onClick = {viewModel.onLoginButtonClick()}
            )

            BorderlessLargeButton(
                text = "Зарегистрироваться"
            ) { navController.navigate("registration") }

        }

        if(isOpen) {
            ModalBottomSheet(
                modifier = Modifier,
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                sheetState = sheetState,
                onDismissRequest = {isOpen = false}
            ) {
                Column(
                    modifier = Modifier.padding(15.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    TittleLarge(
                        text = "Восстановить пароль",
                        TextAlign.Start,
                        padding = PaddingValues(0.dp)
                    )
                    when (viewModel.sheetState) {
                        is BottomSheetState.isEmail -> {
                            TittleSmall(
                                text = "Для восстановления пароля введите вашу почту",
                                textAlign = TextAlign.Start,
                                padding = PaddingValues(0.dp)
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                LargeMailTextField(
                                    //iconID = R.drawable.visibility,
                                    containerColor = MaterialTheme.colorScheme.background,
                                    model = viewModel.refreshPasswordEmail,
                                    onValueChange = { viewModel.onRefreshPasswordEmailChanged(it) },
                                    modifier = Modifier
                                        .weight(1f)
                                )
                                Button(
                                    onClick = {
                                        viewModel.onRefreshPasswordEmailButtonClick()
                                    },
                                    shape = RoundedCornerShape(14.dp),
                                    contentPadding = PaddingValues(0.dp),
                                    enabled = viewModel.isForwardButtonsEnabled,
                                    modifier = Modifier
                                        .height(56.dp)
                                        .width(36.dp)
                                ) {
                                    Icon(
                                        modifier = Modifier.size(28.dp),
                                        painter = painterResource(id = R.drawable.arrow_forward_ios),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onBackground
                                    )
                                }
                            }
                        }

                        is BottomSheetState.isCode -> {
                            TittleSmall(
                                text = "Если код не пришёл на ${viewModel.email.value} проверьте, правильность заполнения почты и попробуйте ещё раз",
                                textAlign = TextAlign.Start,
                                padding = PaddingValues(0.dp)
                            )
                            TittleSmall(
                                text = "Запросить новый код восстановления можно через 15с",
                                textAlign = TextAlign.Start,
                                padding = PaddingValues(0.dp)
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Button(
                                    onClick = {
                                        viewModel.sheetState = BottomSheetState.isEmail
                                    },
                                    shape = RoundedCornerShape(14.dp),
                                    contentPadding = PaddingValues(0.dp),
                                    enabled = true,
                                    modifier = Modifier
                                        .height(56.dp)
                                        .width(36.dp)
                                ) {
                                    Icon(
                                        modifier = Modifier.size(28.dp),
                                        painter = painterResource(id = R.drawable.arrow_backward_ios),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onBackground
                                    )
                                }
                                TextField(
                                    value = viewModel.refreshPasswordCode.value,
                                    onValueChange = { viewModel.onRefreshPasswordCodeChanged(it) },
                                    shape = RoundedCornerShape(20.dp),
                                    placeholder = {
                                        Text(text = "Код")
                                    },
                                    colors = TextFieldDefaults.colors(
                                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                                        focusedContainerColor = MaterialTheme.colorScheme.background.copy(0.7f),
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent
                                    ),
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(56.dp)
                                )
                                Button(
                                    onClick = {},
                                    shape = RoundedCornerShape(14.dp),
                                    contentPadding = PaddingValues(0.dp),
                                    enabled = false,
                                    modifier = Modifier
                                        .height(56.dp)
                                        .width(36.dp)
                                ) {
                                    Icon(
                                        modifier = Modifier.size(28.dp),
                                        painter = painterResource(id = R.drawable.refresh),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onBackground
                                    )
                                }
                            }
                        }

                        is BottomSheetState.isPassword -> {
                            TittleSmall(
                                text = "Введите новый пароль",
                                textAlign = TextAlign.Start,
                                padding = PaddingValues(0.dp)
                            )
                            TextField(
                                value = viewModel.newPassword.value,
                                onValueChange = {viewModel.onNewPasswordChanged(it)},
                                shape = RoundedCornerShape(20.dp),
                                placeholder = {
                                    Text(text = "Новый пароль")
                                },
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                                    focusedContainerColor = MaterialTheme.colorScheme.background.copy(0.7f),
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                modifier = Modifier
                                    .height(56.dp)
                                    .fillMaxWidth()
                            )
                            Button(
                                onClick = {
                                    viewModel.onSaveNewPasswordButtonClick()
                                    isOpen = false
                                },
                                shape = RoundedCornerShape(20.dp),
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier
                                    .height(56.dp)
                                    .fillMaxWidth()
                            ) {
                                Text("Сохранить пароль")
                            }
                        }
                    }
                }
            }
        }
    }
}