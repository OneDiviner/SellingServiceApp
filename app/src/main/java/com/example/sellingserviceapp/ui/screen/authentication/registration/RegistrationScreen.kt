package com.example.sellingserviceapp.ui.screen.authentication.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sellingserviceapp.R
import com.example.sellingserviceapp.ui.component.button.LargeButton
import com.example.sellingserviceapp.ui.component.text.TittleLarge
import com.example.sellingserviceapp.ui.component.text.TittleSmall
import com.example.sellingserviceapp.ui.component.textfield.DigitOutlinedTextField
import com.example.sellingserviceapp.ui.component.textfield.MailOutlinedTextField
import com.example.sellingserviceapp.ui.component.textfield.PasswordOutlinedTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = viewModel(),
    navController: NavController
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        Column(modifier = Modifier
            .padding(horizontal = 25.dp)
            .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            TittleLarge(
                text = stringResource(R.string.registration),
                textAlign = TextAlign.Start,
                padding = PaddingValues(0.dp)
            )

            MailOutlinedTextField(
                state = viewModel.emailState.value,
                placeholder = "Введите почту",
                onValueChange = { viewModel.onEmailChanged(it) }
            )

            PasswordOutlinedTextField(
                state = viewModel.passwordState.value,
                placeholder = stringResource(R.string.enter_password),
                onValueChange = { viewModel.onPasswordChanged(it) }
            )

            PasswordOutlinedTextField(
                state = viewModel.confirmPasswordState.value,
                placeholder = stringResource(R.string.confirm_password),
                onValueChange = { viewModel.onConfirmPasswordChanged(it) }
            )

            LargeButton(
                text = stringResource(R.string.next),
                enabled = viewModel.isNextButtonEnabled
            ) {
                viewModel.openConfirmEmailSheet()
            }

            if(viewModel.showEmailConfirmSheet) {
                LaunchedEffect(viewModel.remainingTime) {
                    viewModel.startGetNewCodeTimer()
                }
                ModalBottomSheet(onDismissRequest = { viewModel.closeConfirmEmailSheet() }) {
                    Column(
                        Modifier.padding(top = 15.dp, start = 25.dp, end = 25.dp, bottom = 50.dp),
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        TittleLarge(
                            text = "Подтверждение почты",
                            padding = PaddingValues(0.dp),
                            textAlign = TextAlign.Start
                        )

                        TittleSmall(
                            text = "Отправили код на почту ${viewModel.emailState.value.text}.\nПроверьте указанный почтовый адресс.",
                            padding = PaddingValues(top = 15.dp),
                            textAlign = TextAlign.Start
                        )

                        DigitOutlinedTextField(
                            state = viewModel.emailConfirmCodeState.value,
                            placeholder = "Введите код подтверждения",
                            onValueChange = {
                                viewModel.onEmailConfirmCodeChange(it)
                                if (viewModel.isEnteredCodeCorrect) {
                                    viewModel.closeConfirmEmailSheet()
                                    navController.navigate("userInfo")
                                }
                            }
                        )

                        LargeButton(
                            text = "Запросить новый код",
                            enabled = viewModel.isGetNewCodeButtonEnabled
                        ) {
                            viewModel.updateTimer()
                        }

                        TittleSmall(
                            text = "Запросить новый код можно через: ${viewModel.remainingTime} сек.",
                            padding = PaddingValues(0.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}


