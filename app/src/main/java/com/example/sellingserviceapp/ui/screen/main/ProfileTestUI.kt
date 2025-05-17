package com.example.sellingserviceapp.ui.screen.main

import com.example.sellingserviceapp.ui.screen.profile.ProfileSheetState
import com.example.sellingserviceapp.ui.screen.profile.ProfileViewModel
import androidx.compose.animation.AnimatedContent
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
import com.example.sellingserviceapp.ui.screen.profile.component.Order
import com.example.sellingserviceapp.ui.screen.profile.component.SectionButton
import com.example.sellingserviceapp.ui.screen.profile.component.Setting
import com.example.sellingserviceapp.ui.screen.profile.component.SettingItemButton
import com.example.sellingserviceapp.ui.screen.profile.editProfile.EditProfileUI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTestUI(
    paddingValues: PaddingValues,
    //viewModel: ProfileViewModel = hiltViewModel()
) {
    var isOpen by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    //val user by viewModel.userFLow.collectAsState(initial = UserDomain.EMPTY)



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
                            Setting(
                                tittle = "Выйти",
                                description = "Выход из профиля",
                                icon = R.drawable.logout
                            ),
                        )
                    )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
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
            }
        }
