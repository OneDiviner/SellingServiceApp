package com.example.sellingserviceapp.ui.screen.authentication.userinfo

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.os.registerForAllProfilingResults
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import com.example.sellingserviceapp.R
import com.example.sellingserviceapp.ui.component.button.LargeButton
import com.example.sellingserviceapp.ui.component.text.TittleLarge
import com.example.sellingserviceapp.ui.component.text.TittleSmall
import com.example.sellingserviceapp.ui.component.textfield.DigitOutlinedTextField
import com.example.sellingserviceapp.ui.component.textfield.MailOutlinedTextField
import com.example.sellingserviceapp.ui.component.textfield.PasswordOutlinedTextField
import com.example.sellingserviceapp.ui.component.textfield.UserInfoTextField
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import com.example.sellingserviceapp.ui.component.textfield.PhoneNumberTextField

//TODO: Do a util file for CropImage

@Composable
fun UserInfoScreen(
    userInfoViewModel: UserInfoViewModel = viewModel(),
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

            //CropImage(userInfoViewModel) Uncomment if need it

            UserInfoTextField(
                value = userInfoViewModel.secondName,
                onValueChange = { userInfoViewModel.onSecondNameChanged(it) },
                placeholder = "Фамилия"
            )

            UserInfoTextField(
                value = userInfoViewModel.name,
                onValueChange = { userInfoViewModel.onNameChanged(it) },
                placeholder = "Имя"
            )

            UserInfoTextField(
                value = userInfoViewModel.lastName,
                onValueChange = { userInfoViewModel.onLastNameChanged(it) },
                placeholder = "Отчество"
            )

            PhoneNumberTextField(
                value = userInfoViewModel.phoneNumber,
                onValueChange = { userInfoViewModel.onPhoneNumberChanged(it) },
                placeholder = "Номер телефона"
            )

            LargeButton(
                text = "Завершить",
                enabled = userInfoViewModel.isFinishRegistrationButtonEnabled
            ) {

            }
        }
    }
}