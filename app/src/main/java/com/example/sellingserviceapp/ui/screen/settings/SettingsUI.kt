package com.example.sellingserviceapp.ui.screen.settings

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.R
import com.example.sellingserviceapp.ui.component.card.FieldModel
import com.example.sellingserviceapp.ui.component.card.ProfileCard
import com.example.sellingserviceapp.ui.component.circularProgressIndicator.FullScreenCircularProgressIndicator
import com.example.sellingserviceapp.ui.screen.settings.clientProfile.ClientProfileUI
import com.example.sellingserviceapp.ui.screen.settings.specialistProfile.SpecialistProfileUI
import com.example.sellingserviceapp.ui.screen.settings.specialistProfile1.ScreenState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsUI(
    paddingValues: PaddingValues,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    var isOpen by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    if(isOpen) {
        ModalBottomSheet(
            modifier = Modifier,
            containerColor = MaterialTheme.colorScheme.background,
            sheetState = sheetState,
            onDismissRequest = {isOpen = false}
        ) {
            ClientProfileUI(paddingValues)
        }
    }
    Box(
        modifier = Modifier
            .padding(paddingValues)
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                    .fillMaxWidth()
                    .background(Color(0xFFFF7043)),
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 15.dp, bottom = 15.dp, ),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {

                    //region Имя, рейтинг, фото профиля(appBar)
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
                                text = "Никитин Даниил",
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
                        Button(
                            onClick = {
                                isOpen = true
                            },
                            modifier = Modifier
                                .size(60.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer
                            ),
                            shape = RoundedCornerShape(20.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxSize(),
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                    //endregion

                    //region Карусель уведомлений
                    val notifications = listOf(
                        Note("Системное уведомление", "У вас 3 новых чего-то там заюерите сейчас пока у вас не забрали и куча ошибок"),
                        Note("Системное уведомление", "У вас 3 новых чего-то там заюерите сейчас пока у вас не забрали и куча ошибок"),
                        Note("Системное уведомление", "У вас 3 новых чего-то там заюерите сейчас пока у вас не забрали и куча ошибок")
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
                            Card(
                                modifier = Modifier

                                    .fillMaxWidth()
                                    .height(120.dp),
                                shape = RoundedCornerShape(20.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                                )
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(15.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Notifications,
                                        contentDescription = null,
                                        modifier = Modifier.size(50.dp),
                                        tint = Color(0xFFF5F5F5)
                                    )
                                    Column {
                                        Text(
                                            text = notifications[page].title,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = MaterialTheme.colorScheme.onBackground
                                        )
                                        Text(
                                            text = notifications[page].text,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Normal,
                                            color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                        )
                                    }
                                }
                            }
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
                    //endregion

                }
            }

            //region 4 кнопки клиенты, специалисты, история, уведомления
            Column(
                modifier = Modifier.padding(horizontal = 15.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .weight(1f)
                            .height(100.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer
                        ),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Column(
                                modifier = Modifier.align(Alignment.Center),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.contacts),
                                    modifier = Modifier
                                        .size(40.dp),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = "Клиенты",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    }
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .weight(1f)
                            .height(100.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer
                        ),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Column(
                                modifier = Modifier.align(Alignment.Center),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.group),
                                    modifier = Modifier
                                        .size(40.dp),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = "Специалисты",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .weight(1f)
                            .height(100.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer
                        ),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Column(
                                modifier = Modifier.align(Alignment.Center),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.work_history),
                                    modifier = Modifier
                                        .size(40.dp),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = "История",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    }

                    Button(
                        onClick = {},
                        modifier = Modifier
                            .weight(1f)
                            .height(100.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer
                        ),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Column(
                                modifier = Modifier.align(Alignment.Center),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    modifier = Modifier
                                        .size(40.dp),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = "Уведомления",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    }
                }
            }
            //endregion

            //region Активные услуги
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.padding(end = 15.dp, start = 15.dp),
                    text = "Активные заказы",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(horizontal = 15.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    ),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.construction),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp),
                                tint = Color(0xFFFFFFFF)
                            )
                            Column() {
                                Text(
                                    modifier = Modifier,
                                    text = "Установка фасадов",
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    modifier = Modifier,
                                    text = "Ремонт•Сборка мебели",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                )
                            }
                        }
                        Icon(
                            painter = painterResource(R.drawable.arrow_forward_ios),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = Color(0xFFFFFFFF)
                        )
                    }

                }
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(horizontal = 15.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    ),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.construction),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp),
                                tint = Color(0xFFFFFFFF)
                            )
                            Column() {
                                Text(
                                    modifier = Modifier,
                                    text = "Установка фасадов",
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    modifier = Modifier,
                                    text = "Ремонт•Сборка мебели",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                )
                            }
                        }
                        Icon(
                            painter = painterResource(R.drawable.arrow_forward_ios),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = Color(0xFFFFFFFF)
                        )
                    }

                }
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(horizontal = 15.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    ),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.construction),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp),
                                tint = Color(0xFFFFFFFF)
                            )
                            Column() {
                                Text(
                                    modifier = Modifier,
                                    text = "Установка фасадов",
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    modifier = Modifier,
                                    text = "Ремонт•Сборка мебели",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                )
                            }
                        }
                        Icon(
                            painter = painterResource(R.drawable.arrow_forward_ios),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = Color(0xFFFFFFFF)
                        )
                    }

                }
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(horizontal = 15.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    ),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.construction),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp),
                                tint = Color(0xFFFFFFFF)
                            )
                            Column() {
                                Text(
                                    modifier = Modifier,
                                    text = "Установка фасадов",
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    modifier = Modifier,
                                    text = "Ремонт•Сборка мебели",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                )
                            }
                        }
                        Icon(
                            painter = painterResource(R.drawable.arrow_forward_ios),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = Color(0xFFFFFFFF)
                        )
                    }

                }
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(horizontal = 15.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    ),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.construction),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp),
                                tint = Color(0xFFFFFFFF)
                            )
                            Column() {
                                Text(
                                    modifier = Modifier,
                                    text = "Установка фасадов",
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    modifier = Modifier,
                                    text = "Ремонт•Сборка мебели",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                )
                            }
                        }
                        Icon(
                            painter = painterResource(R.drawable.arrow_forward_ios),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = Color(0xFFFFFFFF)
                        )
                    }

                }
            }
            //endregion

            //region Настройки
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Настройки",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.Transparent,
                        ),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(15.dp)
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Row(
                                modifier = Modifier,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.construction),
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp),
                                    tint = Color(0xFFFFFFFF)
                                )
                                Box {
                                    Column {
                                        Text(
                                            modifier = Modifier,
                                            text = "Установка фасадов",
                                            fontSize = 16.sp,
                                            color = MaterialTheme.colorScheme.onBackground
                                        )
                                        Text(
                                            modifier = Modifier,
                                            text = "Ремонт•Сборка мебели",
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                        )
                                    }
                                    // Подчеркивание
                                    HorizontalDivider(
                                        modifier = Modifier
                                            .align(Alignment.BottomStart)
                                            .fillMaxWidth()
                                            .padding(top = 50.dp), // Отступ от текста
                                        thickness = 1.dp,
                                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
                                    )
                                }
                            }
                            Icon(
                                painter = painterResource(R.drawable.arrow_forward_ios),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = Color(0xFFFFFFFF)
                            )
                        }
                    }
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.Transparent,
                        ),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(15.dp)
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Row(
                                modifier = Modifier,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.construction),
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp),
                                    tint = Color(0xFFFFFFFF)
                                )
                                Box {
                                    Column {
                                        Text(
                                            modifier = Modifier,
                                            text = "Установка фасадов",
                                            fontSize = 16.sp,
                                            color = MaterialTheme.colorScheme.onBackground
                                        )
                                        Text(
                                            modifier = Modifier,
                                            text = "Ремонт•Сборка мебели",
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                        )
                                    }
                                    // Подчеркивание
                                    HorizontalDivider(
                                        modifier = Modifier
                                            .align(Alignment.BottomStart)
                                            .fillMaxWidth()
                                            .padding(top = 50.dp), // Отступ от текста
                                        thickness = 1.dp,
                                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
                                    )
                                }
                            }
                            Icon(
                                painter = painterResource(R.drawable.arrow_forward_ios),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = Color(0xFFFFFFFF)
                            )
                        }
                    }
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.Transparent,
                        ),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(15.dp)
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Row(
                                modifier = Modifier,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.construction),
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp),
                                    tint = Color(0xFFFFFFFF)
                                )
                                Box {
                                    Column {
                                        Text(
                                            modifier = Modifier,
                                            text = "Установка фасадов",
                                            fontSize = 16.sp,
                                            color = MaterialTheme.colorScheme.onBackground
                                        )
                                        Text(
                                            modifier = Modifier,
                                            text = "Ремонт•Сборка мебели",
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                        )
                                    }
                                    // Подчеркивание
                                    HorizontalDivider(
                                        modifier = Modifier
                                            .align(Alignment.BottomStart)
                                            .fillMaxWidth()
                                            .padding(top = 50.dp), // Отступ от текста
                                        thickness = 1.dp,
                                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
                                    )
                                }
                            }
                            Icon(
                                painter = painterResource(R.drawable.arrow_forward_ios),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = Color(0xFFFFFFFF)
                            )
                        }
                    }
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.Transparent,
                        ),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(15.dp)
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Row(
                                modifier = Modifier,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.construction),
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp),
                                    tint = Color(0xFFFFFFFF)
                                )
                                Box {
                                    Column {
                                        Text(
                                            modifier = Modifier,
                                            text = "Установка фасадов",
                                            fontSize = 16.sp,
                                            color = MaterialTheme.colorScheme.onBackground
                                        )
                                        Text(
                                            modifier = Modifier,
                                            text = "Ремонт•Сборка мебели",
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                        )
                                    }
                                    // Подчеркивание
                                    HorizontalDivider(
                                        modifier = Modifier
                                            .align(Alignment.BottomStart)
                                            .fillMaxWidth()
                                            .padding(top = 50.dp), // Отступ от текста
                                        thickness = 1.dp,
                                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
                                    )
                                }
                            }
                            Icon(
                                painter = painterResource(R.drawable.arrow_forward_ios),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = Color(0xFFFFFFFF)
                            )
                        }
                    }
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.Transparent,
                        ),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(15.dp)
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Row(
                                modifier = Modifier,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.construction),
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp),
                                    tint = Color(0xFFFFFFFF)
                                )
                                Box {
                                    Column {
                                        Text(
                                            modifier = Modifier,
                                            text = "Установка фасадов",
                                            fontSize = 16.sp,
                                            color = MaterialTheme.colorScheme.onBackground
                                        )
                                        Text(
                                            modifier = Modifier,
                                            text = "Ремонт•Сборка мебели",
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                        )
                                    }
                                    // Подчеркивание
                                    HorizontalDivider(
                                        modifier = Modifier
                                            .align(Alignment.BottomStart)
                                            .fillMaxWidth()
                                            .padding(top = 50.dp), // Отступ от текста
                                        thickness = 1.dp,
                                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
                                    )
                                }
                            }
                            Icon(
                                painter = painterResource(R.drawable.arrow_forward_ios),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = Color(0xFFFFFFFF)
                            )
                        }
                    }
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.Transparent,
                        ),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(15.dp)
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Row(
                                modifier = Modifier,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.construction),
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp),
                                    tint = Color(0xFFFFFFFF)
                                )
                                Box {
                                    Column {
                                        Text(
                                            modifier = Modifier,
                                            text = "Установка фасадов",
                                            fontSize = 16.sp,
                                            color = MaterialTheme.colorScheme.onBackground
                                        )
                                        Text(
                                            modifier = Modifier,
                                            text = "Ремонт•Сборка мебели",
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                                        )
                                    }
                                    // Подчеркивание
                                    /*HorizontalDivider(
                                        modifier = Modifier
                                            .align(Alignment.BottomStart)
                                            .fillMaxWidth()
                                            .padding(top = 50.dp), // Отступ от текста
                                        thickness = 1.dp,
                                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
                                    )*/
                                }
                            }
                            Icon(
                                painter = painterResource(R.drawable.arrow_forward_ios),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = Color(0xFFFFFFFF)
                            )
                        }
                    }
                }
            }
            //endregion
        }
    }
}