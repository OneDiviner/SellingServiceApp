package com.example.sellingserviceapp.ui.screen.profile.clientProfile

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.R
import com.example.sellingserviceapp.data.local.UserData
import com.example.sellingserviceapp.ui.component.button.LargeButton
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonModel
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonState
import com.example.sellingserviceapp.ui.screen.profile.specialistProfile1.ScreenState
import com.example.sellingserviceapp.util.extension.ImagePicker


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientProfileUI(
    paddingValues: PaddingValues,
    viewModel: ClientProfileViewModel = hiltViewModel()
) {
    val user by viewModel.userFlow.collectAsState(
        initial = UserData("", "", "", "", "")
    )

    val context = LocalContext.current

    // 1. Создаём менеджер выбора изображения
    val imagePicker = remember {
        ImagePicker(context) { base64 ->
            viewModel.onPhotoSelected(base64)
        }
    }

    // 2. Регистрация лаунчера
    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imagePicker.handleUri(uri)
    }



    Column(
        modifier = Modifier.padding(),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.5f)
                .background(MaterialTheme.colorScheme.surfaceContainer),
            contentAlignment = Alignment.BottomEnd
        ) {
            BottomSheetDefaults.DragHandle(
                modifier = Modifier
                    .displayCutoutPadding()
                    .zIndex(2f)
                    .align(Alignment.TopCenter)
            )
            if(!viewModel.photoBase64.isNullOrBlank()) {
                viewModel.photoBase64?.let {
                    val bitmap = remember(it) {
                        val bytes = Base64.decode(it, Base64.DEFAULT)
                        BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    }
                    bitmap?.let {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = "User photo",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            } else {
                Icon(
                    modifier = Modifier
                        .size(64.dp)
                        .align(Alignment.Center),
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            // 🔻 Затемнение сверху
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.background.copy(0.7f),
                                Color.Transparent
                            ),
                            startY = 0f,
                            endY = 300f
                        )
                    )
            )
            // 🔻 Затемнение снизу
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.background.copy(0.7f)
                            ),
                            startY = 300f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text("${user.firstName} ${user.middleName}", fontSize = 32.sp)
                Text(user.email, fontSize = 14.sp)
            }

            // Кнопка выбора фото
            Button(
                onClick = {imagePicker.pickImage(imageLauncher) },
                modifier = Modifier
                    .padding(end = 15.dp)
                    .offset(y = 18.dp)
                    .size(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    modifier = Modifier.size(22.dp),
                    painter = painterResource(R.drawable.add_photo_alternate),
                    contentDescription = "Изменить фото",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        Column(
            modifier = Modifier.padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Text(
                modifier = Modifier,
                text = "О вас",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            TextField(
                value = viewModel.middleName,
                onValueChange = {viewModel.onMiddleNameChange(it)},
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
                    Text("Фамилия")
                },
                enabled = viewModel.screenState != ScreenState.Loading
            )
            TextField(
                value = viewModel.firstName,
                onValueChange = {viewModel.onFirstNameChange(it)},
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
                    Text("Имя")
                },
                enabled = viewModel.screenState != ScreenState.Loading
            )
            TextField(
                value = viewModel.lastName,
                onValueChange = {viewModel.onLastNameChange(it)},
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
                    Text("Отчество")
                },
                enabled = viewModel.screenState != ScreenState.Loading
            )
        }
        Column(
            modifier = Modifier.padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Контакты",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            TextField(
                value = viewModel.email,
                onValueChange = {},
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
                    Text("Почта")
                },
                enabled = viewModel.screenState != ScreenState.Loading
            )
            TextField(
                value = viewModel.phoneNumber,
                onValueChange = {viewModel.onPhoneNumberChange(it)},
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
                    Text("Tелефон")
                },
                enabled = viewModel.screenState != ScreenState.Loading
            )
        }

        LargeButton(
            model = viewModel.saveChangesButton,
            onClick = { viewModel.onSaveChangesButtonClick() }
        )

        LargeButton(
            model = ButtonModel("Выйти", ButtonState.Ready),
            onClick = { viewModel.onExitButtonClick() }
        )

    }
}