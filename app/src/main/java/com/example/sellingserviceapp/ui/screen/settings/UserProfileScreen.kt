package com.example.sellingserviceapp.ui.screen.settings

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.sellingserviceapp.ui.component.button.LargeButton
import com.example.sellingserviceapp.ui.component.card.ProfileInfoCard
import com.example.sellingserviceapp.ui.component.card.SpecialistProfileDataCard
import com.example.sellingserviceapp.ui.component.circularProgressIndicator.FullScreenCircularProgressIndicator
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonModel
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonState
import com.example.sellingserviceapp.ui.screen.settings.model.ProfileInfoItemModel
import com.example.sellingserviceapp.ui.screen.settings.model.SpecialistProfileDataModel

@Composable
fun AvatarPicker(
    viewModel: UserProfileViewModel = hiltViewModel()
) {
    //var imageUri by remember { mutableStateOf<Uri?>(null) }
    val avatarUri by viewModel.avatarUri.collectAsState()
    val context = LocalContext.current

    // ActivityResultLauncher для выбора изображения из галереи
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.updateAvatar(context, uri)
        }
    }

    // Отрисовка аватарки
    if (avatarUri != null) {
        // Если изображение выбрано, показываем его
        Image(
            painter = rememberImagePainter(data = avatarUri),
            contentDescription = "User Avatar",
            modifier = Modifier
                .size(128.dp)
                .clip(RectangleShape)
                .clickable { launcher.launch("image/*") },
            contentScale = ContentScale.Crop
        )
    } else {
        // Если изображение не выбрано, показываем иконку по умолчанию
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Account Circle Icon",
            modifier = Modifier
                .size(128.dp)
                .clickable { launcher.launch("image/*") },
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}


@Composable
fun UserProfileScreen(
    viewModel: UserProfileViewModel = hiltViewModel()
) {
    /*val listState = rememberLazyListState()
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                if (visibleItems.isNotEmpty() && viewModel.userProfileState != UserProfileState.Loading) {
                    val lastVisibleItem = visibleItems.last()
                    if (lastVisibleItem.index == visibleItems.size - 1) {
                        // Загрузка новых данных
                        viewModel.loadProfile()
                    }
                }
            }
    }*/ //TODO: Разобраться с обновлением при скроле

    val contacts = listOf(
        ProfileInfoItemModel(
            icon = Icons.Default.Phone,
            text = "+7(974)875-97-20",
            description = "Phone"
        ),
        ProfileInfoItemModel(
            icon = Icons.Default.Email,
            text = "petrov_alex@mail.ru",
            description = "Email"
        )
    )

    val serviceFormat = listOf(
        ProfileInfoItemModel(
            icon = Icons.Default.Phone,
            text = "Работает дистанционно",
            description = "Phone"
        ),
        ProfileInfoItemModel(
            icon = Icons.Default.Home,
            text = "Выезжает к вам",
            description = "Phone"
        ),
        ProfileInfoItemModel(
            icon = Icons.Default.LocationOn,
            text = "Работает у себя",
            description = "Phone"
        )
    )

    when(viewModel.userProfileState) {
        is UserProfileState.Loading -> FullScreenCircularProgressIndicator()
        is UserProfileState.Error -> {
            Button(
                onClick = {}
            ) { }
        }
        is UserProfileState.Success -> {
            LazyColumn {
                item {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                            .padding(horizontal = 25.dp, vertical = 50.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Column(modifier = Modifier
                            .background(MaterialTheme.colorScheme.background),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(15.dp)
                        ) {
                            //AvatarPicker()

                           SpecialistProfileDataCard(
                               SpecialistProfileDataModel(
                                   name = "Никитин Даниил",
                                   about = "Я — специалист по ремонту с многолетним опытом работы в сфере бытового и технического обслуживания. Моя специализация включает ремонт электроники, бытовой техники, сантехники и мелкие строительные работы. Я всегда стремлюсь к качественному выполнению задач, используя современные инструменты и материалы. Гарантирую оперативность, надёжность и индивидуальный подход к каждому клиенту. Ваше доверие — моя главная награда. Обращайтесь, и я помогу решить любые бытовые проблемы!",
                                   rating = "4,87"
                               )
                           )

                            ProfileInfoCard("Контакты",contacts)

                            ProfileInfoCard("Формат услуги", serviceFormat)

                            Column(
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Text(
                                    text = "Услуги и цены",
                                    modifier = Modifier.fillMaxWidth(),
                                    fontSize = 24.sp,
                                    //style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    textAlign = TextAlign.Left
                                )
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(60.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.background
                                    )
                                ) { }
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(60.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.background
                                    )
                                ) { }
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(60.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.background
                                    )
                                ) { }
                                LargeButton(
                                    model = ButtonModel(text = "Посмотреть все", state = ButtonState.Ready)
                                ) { }
                            }

                            Column(

                            ) {
                                Text(
                                    text = "Формат услуги",
                                    modifier = Modifier.fillMaxWidth(),
                                    fontSize = 24.sp,
                                    //style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    textAlign = TextAlign.Left
                                )
                                Row(
                                    modifier = Modifier.padding(vertical = 10.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Phone, // Используем иконку AccountCircle
                                        contentDescription = "Service", // Описание для доступности
                                        modifier = Modifier.size(24.dp), // Размер иконки
                                        tint = MaterialTheme.colorScheme.onBackground // Цвет иконки (можно изменить)
                                    )
                                    Text(
                                        text = "Специалист работает дистанционно",
                                        modifier = Modifier.fillMaxWidth(),
                                        fontSize = 14.sp,
                                        //style = MaterialTheme.typography.titleLarge,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        textAlign = TextAlign.Left
                                    )
                                }
                            }

                            Column(

                            ) {
                                Text(
                                    text = "Формат услуги",
                                    modifier = Modifier.fillMaxWidth(),
                                    fontSize = 24.sp,
                                    //style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    textAlign = TextAlign.Left
                                )
                                Row(
                                    modifier = Modifier.padding(vertical = 10.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Phone, // Используем иконку AccountCircle
                                        contentDescription = "Service", // Описание для доступности
                                        modifier = Modifier.size(24.dp), // Размер иконки
                                        tint = MaterialTheme.colorScheme.onBackground // Цвет иконки (можно изменить)
                                    )
                                    Text(
                                        text = "Специалист работает дистанционно",
                                        modifier = Modifier.fillMaxWidth(),
                                        fontSize = 14.sp,
                                        //style = MaterialTheme.typography.titleLarge,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        textAlign = TextAlign.Left
                                    )
                                }
                            }

                            Column(

                            ) {
                                Text(
                                    text = "Формат услуги",
                                    modifier = Modifier.fillMaxWidth(),
                                    fontSize = 24.sp,
                                    //style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    textAlign = TextAlign.Left
                                )
                                Row(
                                    modifier = Modifier.padding(vertical = 10.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Phone, // Используем иконку AccountCircle
                                        contentDescription = "Service", // Описание для доступности
                                        modifier = Modifier.size(24.dp), // Размер иконки
                                        tint = MaterialTheme.colorScheme.onBackground // Цвет иконки (можно изменить)
                                    )
                                    Text(
                                        text = "Специалист работает дистанционно",
                                        modifier = Modifier.fillMaxWidth(),
                                        fontSize = 14.sp,
                                        //style = MaterialTheme.typography.titleLarge,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        textAlign = TextAlign.Left
                                    )
                                }
                            }

                            Column(

                            ) {
                                Text(
                                    text = "Формат услуги",
                                    modifier = Modifier.fillMaxWidth(),
                                    fontSize = 24.sp,
                                    //style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    textAlign = TextAlign.Left
                                )
                                Row(
                                    modifier = Modifier.padding(vertical = 10.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Phone, // Используем иконку AccountCircle
                                        contentDescription = "Service", // Описание для доступности
                                        modifier = Modifier.size(24.dp), // Размер иконки
                                        tint = MaterialTheme.colorScheme.onBackground // Цвет иконки (можно изменить)
                                    )
                                    Text(
                                        text = "Специалист работает дистанционно",
                                        modifier = Modifier.fillMaxWidth(),
                                        fontSize = 14.sp,
                                        //style = MaterialTheme.typography.titleLarge,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        textAlign = TextAlign.Left
                                    )
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}