package com.example.sellingserviceapp.ui.screen.authentication.userinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.sellingserviceapp.ui.component.text.TittleLarge
import com.example.sellingserviceapp.ui.component.textfield.UserInfoTextField
import com.example.sellingserviceapp.ui.component.textfield.PhoneNumberTextField
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonState

//TODO: Do a util file for CropImage

@Composable
fun UserInfoScreen(
    viewModel: UserInfoViewModel = viewModel(),
    navController: NavController
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = Modifier
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

            //CropImage(viewModel) Uncomment if need it

            UserInfoTextField(
                state = viewModel.secondNameState.value,
                placeholder = "Фамилия",
                onValueChange = { viewModel.onSecondNameChanged(it) }
            )

            UserInfoTextField(
                state = viewModel.nameState.value,
                placeholder = "Имя",
                onValueChange = { viewModel.onNameChanged(it) }
            )

            UserInfoTextField(
                state = viewModel.lastNameState.value,
                placeholder = "Отчество",
                onValueChange = { viewModel.onLastNameChanged(it) }
            )

            PhoneNumberTextField(
                state = viewModel.phoneNumberState.value,
                placeholder = "Номер телефона",
                onValueChange = { viewModel.onPhoneNumberChanged(it) }
            )

            LargeButton(
                text = "Завершить",
                state = ButtonState(
                    isClickable = true,
                )
            ) {

            }
        }
    }
}