package com.example.sellingserviceapp.ui.screen.profile

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.R
import com.example.sellingserviceapp.model.domain.UserDomain
import com.example.sellingserviceapp.ui.screen.profile.clientProfile.ClientProfileUI
import com.example.sellingserviceapp.ui.screen.profile.component.ActiveOrderItemButton
import com.example.sellingserviceapp.ui.screen.profile.component.HorizontalPagerItem
import com.example.sellingserviceapp.ui.screen.profile.component.Order
import com.example.sellingserviceapp.ui.screen.profile.component.ProfileIconButton
import com.example.sellingserviceapp.ui.screen.profile.component.SectionButton
import com.example.sellingserviceapp.ui.screen.profile.component.Setting
import com.example.sellingserviceapp.ui.screen.profile.component.SettingItemButton
import com.example.sellingserviceapp.ui.screen.profile.editProfile.EditProfileUI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileUI(
    paddingValues: PaddingValues,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    var isOpen by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
   val user by viewModel.userFLow.collectAsState(initial = UserDomain.EMPTY)

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
                    is ProfileSheetState.Profile -> {
                        ClientProfileUI(paddingValues)
                    }
                    is ProfileSheetState.EditProfile -> {
                        EditProfileUI(
                            onBackButtonClick = {
                                viewModel.profileSheetState = ProfileSheetState.Profile
                            },
                            onSaveButtonClick = {
                                viewModel.profileSheetState = ProfileSheetState.Profile
                            }
                        )
                    }
                }
            }
        }
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
    LazyColumn (
        modifier = Modifier
            .padding(bottom = paddingValues.calculateBottomPadding())
            .blur( if (sheetState.isVisible) 3.dp else 0.dp )
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                    )
                    .systemBarsPadding()
            ) {
                Column(
                    modifier = Modifier.padding(bottom = 15.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 15.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Text(
                                text = "${user.firstName} ${user.secondName}", // Поле с именем пользователя
                                fontSize = 28.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Card(
                                modifier = Modifier,
                                shape = RoundedCornerShape(20.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                                )
                            ) {
                                Row(
                                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp),
                                        tint = MaterialTheme.colorScheme.onBackground
                                    )
                                    Text(
                                        text = "5.00",
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                }
                            }
                        }
                        ProfileIconButton(
                            onClick = {isOpen = true},
                            photoBase64 = user.avatar?: ""
                        )
                    }
                    val notifications = listOf(
                        Note("Системное уведомление", "У вас 3 новых чего-то там заюерите сейчас пока у вас не забрали и куча ошибок"),
                        Note("Новый заказ", "Вам пришел новый заказа от пользователя"),
                        Note("Имя записи", "Специалист подтвердил вашу запись")
                    )
                    val pagerState = rememberPagerState(initialPage = 0, pageCount = {notifications.size})
                    // Карусель уведомлений
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
                    }
                }
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
                    icon = painterResource(R.drawable.contacts),
                    title = "Клиенты"
                )
                SectionButton(
                    modifier = Modifier.weight(1f),
                    onClick = {},
                    icon = painterResource(R.drawable.group),
                    title = "Специалисты"
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