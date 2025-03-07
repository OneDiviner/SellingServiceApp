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
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.example.sellingserviceapp.R
import com.example.sellingserviceapp.ui.component.button.LargeButton
import com.example.sellingserviceapp.ui.component.text.TittleLarge
import com.example.sellingserviceapp.ui.component.text.TittleSmall
import com.example.sellingserviceapp.ui.component.textfield.DigitOutlinedTextField
import com.example.sellingserviceapp.ui.component.textfield.MailOutlinedTextField
import com.example.sellingserviceapp.ui.component.textfield.PasswordOutlinedTextField
import androidx.hilt.navigation.compose.hiltViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = hiltViewModel(),
    navController: NavController
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        viewModel.navigationEvents.collect { route ->
            navController.navigate(route) // Выполняем навигацию
        }
    }

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
                model = viewModel.email,
                onValueChange = { viewModel.onEmailChanged(it) }
            )

            PasswordOutlinedTextField(
                model = viewModel.password,
                onValueChange = { viewModel.onPasswordChanged(it) }
            )

            PasswordOutlinedTextField(
                model = viewModel.confirmPassword,
                onValueChange = { viewModel.onConfirmPasswordChanged(it) }
            )

            LargeButton(
                model = viewModel.nextButton,
                onClick = { viewModel.onNextButtonClick() }
            )

           if(viewModel.bottomSheetState) {
                ModalBottomSheet(onDismissRequest = { viewModel.bottomSheetStateUpdater(false) }) {
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
                            text = "Отправили код на почту ${viewModel.email.value}.\nПроверьте указанный почтовый адресс.",
                            padding = PaddingValues(top = 15.dp),
                            textAlign = TextAlign.Start
                        )

                        DigitOutlinedTextField(
                            model = viewModel.emailConfirmCode,
                            onValueChange = { viewModel.onEmailConfirmCodeChanged(it) }
                        )

                        LargeButton(
                            model = viewModel.requestNewCodeButton,
                            onClick = { viewModel.onRequestNewCodeButtonClick() }
                        )

                        TittleSmall(
                            text = "Запросить новый код можно через: ${viewModel.timeLeft} сек.",
                            padding = PaddingValues(0.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}


