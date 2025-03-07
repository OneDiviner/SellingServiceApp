package com.example.sellingserviceapp.ui.screen.authentication.login

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sellingserviceapp.R
import com.example.sellingserviceapp.ui.component.button.LargeButton
import com.example.sellingserviceapp.ui.component.button.BorderlessLargeButton
import com.example.sellingserviceapp.ui.component.text.TittleLarge
import com.example.sellingserviceapp.ui.component.text.TittleSmall
import com.example.sellingserviceapp.ui.component.textfield.MailOutlinedTextField
import com.example.sellingserviceapp.ui.component.textfield.PasswordOutlinedTextField
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LogInScreen(
    viewModel: LoginViewModel = viewModel(),
    navController: NavController
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(horizontal = 25.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Column(modifier = Modifier
            .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            TittleLarge(
                text = stringResource(R.string.login),
                TextAlign.Start,
                padding = PaddingValues(all = 0.dp)
            )

            MailOutlinedTextField(
                model = viewModel.email,
                onValueChange = { viewModel.onEmailChanged(it) },
            )

            PasswordOutlinedTextField(
                model = viewModel.password,
                onValueChange = { viewModel.onPasswordChanged(it) },
            )

            TittleSmall(
                text = stringResource(R.string.forgot_password),
                textAlign = TextAlign.Start,
                padding = PaddingValues(bottom = 15.dp)
            )

            LargeButton(
                model = viewModel.loginButton
            ) { }

            BorderlessLargeButton(
                text = "Зарегистрироваться"
            ) { navController.navigate("registration") }

            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 50.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                TittleSmall(
                    text = stringResource(R.string.policy),
                    textAlign = TextAlign.Center,
                    padding = PaddingValues(0.dp)
                )
            }
        }
    }
}