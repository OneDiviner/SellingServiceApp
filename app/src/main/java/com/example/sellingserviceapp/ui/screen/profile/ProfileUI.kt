package com.example.sellingserviceapp.ui.screen.profile

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.R
import com.example.sellingserviceapp.model.domain.UserDomain
import com.example.sellingserviceapp.ui.screen.profile.component.ActiveOrderItemButton
import com.example.sellingserviceapp.ui.screen.profile.component.Order
import com.example.sellingserviceapp.ui.screen.profile.component.SectionButton
import com.example.sellingserviceapp.ui.screen.profile.component.Setting
import com.example.sellingserviceapp.ui.screen.profile.component.SettingItemButton
import com.example.sellingserviceapp.ui.screen.profile.editProfile.EditProfileUI
import com.example.sellingserviceapp.util.extension.imagePicker.ImageContent
import com.example.sellingserviceapp.util.extension.imagePicker.pickImageLauncher

sealed class ProfileSheetState {
    data object EditProfile: ProfileSheetState()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileUI(
    viewModel: ProfileViewModel = hiltViewModel(),
    onMyServiceButtonClick: () -> Unit
) {

    var isOpen by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    if(isOpen) {
        ModalBottomSheet(
            modifier = Modifier
                .displayCutoutPadding(),
            containerColor = MaterialTheme.colorScheme.background,
            sheetState = sheetState,
            onDismissRequest = {isOpen = false},
            scrimColor = Color.Black.copy(0.6f),
            dragHandle = null
        ) {
            AnimatedContent(
                targetState = viewModel.profileSheetState,
                transitionSpec = {
                    fadeIn(tween(500)) togetherWith fadeOut(tween(500))
                }
            ) {
                when(it) {
                    is ProfileSheetState.EditProfile -> {
                        EditProfileUI(
                            onBackButtonClick = {
                                isOpen = false
                            },
                            onSaveButtonClick = {
                                isOpen = false
                            }
                        )
                    }
                }
            }
        }
    }

    val user by viewModel.userFLow.collectAsState(initial = UserDomain.EMPTY)

    val pickImageLauncher = pickImageLauncher {
        viewModel.onPhotoSelected(it)
    }

    val activeOrderList by remember {
        mutableStateOf(
            listOf(
                Order(
                    tittle = "Ремонт и обслуживание АКПП",
                    category = "Ремонт",
                    subcategory = "Ремонт автомобилей",
                    icon = R.drawable.car_gear
                ),
                Order(
                    tittle = "Курс младшего механика",
                    category = "Образование",
                    subcategory = "Курс",
                    icon = R.drawable.school
                ),
                Order(
                    tittle = "Комплексный ремонт и обслуживание автомобилей",
                    category = "Ремонт",
                    subcategory = "Ремонт автомобилей",
                    icon = R.drawable.car_gear
                ),
            )
        )
    }

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .padding(bottom = 15.dp)
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.5f)
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Column(
                        modifier = Modifier
                            .zIndex(3f)
                            .align(Alignment.BottomStart)
                            .padding(15.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text("${user.firstName} ${user.secondName}", fontSize = 32.sp, color = MaterialTheme.colorScheme.onBackground)
                        Text(user.email, fontSize = 14.sp,color = MaterialTheme.colorScheme.onBackground.copy(0.7f))
                    }

                    ImageContent(
                        photoBase64 = user.avatar?: "",
                        onEditButtonClick = {
                            viewModel.profileSheetState = ProfileSheetState.EditProfile
                            isOpen = true
                        },
                        onMoreButtonClick = {},
                        onPickImageButtonClick = pickImageLauncher
                    )
                }
            }

            item {
                Text(
                    modifier = Modifier.padding(end = 15.dp, start = 15.dp),
                    text = "Активные заказы",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            items(activeOrderList) { order ->
                ActiveOrderItemButton(
                    onClick = {},
                    order = order
                )
            }

            item {
                Row(
                    modifier = Modifier.padding(horizontal = 15.dp),
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    SectionButton(
                        modifier = Modifier.weight(1f),
                        onClick = onMyServiceButtonClick,
                        icon = painterResource(R.drawable.design_services),
                        title = "Мои услуги"
                    )
                    SectionButton(
                        modifier = Modifier.weight(1f),
                        onClick = {},
                        icon = painterResource(R.drawable.book),
                        title = "Мои записи"
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier.padding(horizontal = 15.dp),
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    SectionButton(
                        modifier = Modifier.weight(1f),
                        onClick = {},
                        icon = painterResource(R.drawable.work_history),
                        title = "История"
                    )
                    SectionButton(
                        modifier = Modifier.weight(1f),
                        onClick = {},
                        icon = painterResource(R.drawable.notifications),
                        title = "Уведомления"
                    )
                }
            }

            item {
                var settingsList by remember { mutableStateOf(
                    listOf(
                        Setting(
                            tittle = "Маркетинг",
                            description = "Отзывы•Напоминания•Сертификаты"
                        ),
                        Setting(
                            tittle = "Поддержка",
                            description = "Ответим на ваши вопросы"
                        ),
                        Setting(
                            tittle = "Профиль",
                            description = "Управление профилем"
                        ),
                    )
                )
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                        .fillMaxWidth()
                ) {
                    Column {
                        Text(
                            modifier = Modifier.padding(top = 15.dp, start = 15.dp, end = 15.dp),
                            text = "Настройки",
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        settingsList.forEach { setting ->
                            SettingItemButton(
                                onClick = {},
                                setting = setting,
                                divider = setting != settingsList.last()
                            )
                        }
                    }
                }
            }

            item {
                SettingItemButton(
                    onClick = {viewModel.logout()},
                    setting = Setting(
                        tittle = "Выйти",
                        description = "Выход из профиля",
                        icon = R.drawable.logout
                    ),
                    divider = false
                )
            }
        }
    }

    //region Карусель уведомлений
    /*
    val notifications = listOf(
        Note("Системное уведомление", "У вас 3 новых чего-то там заюерите сейчас пока у вас не забрали и куча ошибок"),
        Note("Новый заказ", "Вам пришел новый заказа от пользователя"),
        Note("Имя записи", "Специалист подтвердил вашу запись")
    )
    val pagerState = rememberPagerState(initialPage = 0, pageCount = {notifications.size})
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        HorizontalPager(
            pageSpacing = 5.dp,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 15.dp),
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            HorizontalPagerItem(
                notifications = notifications,
                page = page
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(notifications.size) { index ->
                val size by animateDpAsState(
                    targetValue = if (pagerState.currentPage == index) 10.dp else 8.dp
                )
                val color by animateColorAsState(
                    targetValue = if (pagerState.currentPage == index)
                        MaterialTheme.colorScheme.onBackground
                    else
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )

                Box(
                    modifier = Modifier
                        .size(size)
                        .background(color, CircleShape)
                        .padding(2.dp)
                )
                if (index < notifications.size - 1) Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }*/
    //endregion
}