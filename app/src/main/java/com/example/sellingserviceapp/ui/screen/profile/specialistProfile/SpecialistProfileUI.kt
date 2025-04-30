package com.example.sellingserviceapp.ui.screen.profile.specialistProfile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.ui.component.card.specialistProfile.CommentCard
import com.example.sellingserviceapp.ui.component.card.specialistProfile.CommentModel
import com.example.sellingserviceapp.ui.component.card.specialistProfile.ContactCard
import com.example.sellingserviceapp.ui.component.card.specialistProfile.ContactType
import com.example.sellingserviceapp.ui.component.card.specialistProfile.ServiceAndCost
import com.example.sellingserviceapp.ui.component.card.specialistProfile.ServiceAndCostCard
import com.example.sellingserviceapp.ui.component.card.specialistProfile.ServiceFormat
import com.example.sellingserviceapp.ui.component.card.specialistProfile.ServiceFormatCard
import com.example.sellingserviceapp.ui.component.circularProgressIndicator.FullScreenCircularProgressIndicator
import com.example.sellingserviceapp.ui.screen.profile.specialistProfile1.ScreenState

@Composable
fun SpecialistProfileUI(
    viewModel: SpecialistProfileViewModel = hiltViewModel()
) {
    when(viewModel.screenState) {
        is ScreenState.Error -> {
            Button(
                onClick = {}
            ) { }
        }
        is ScreenState.Loading -> {
            FullScreenCircularProgressIndicator()
        }
        is ScreenState.Success -> {
            //LazyColumn {
                //item {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                            .padding(horizontal = 25.dp, vertical = 15.dp),
                        contentAlignment = Alignment.TopStart
                    ) {
                        Column(
                            modifier = Modifier
                                .verticalScroll(rememberScrollState())
                                .background(MaterialTheme.colorScheme.background),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(20.dp),
                        ) {
                            Column ( //Аватарка, Фио, Рейтинг, Описание
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Card(
                                        modifier = Modifier
                                            .size(width = 80.dp, height = 100.dp)
                                            .graphicsLayer {
                                                shadowElevation = 2.dp.toPx()
                                                shape = RoundedCornerShape(12.dp)
                                                clip = true
                                            },
                                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                                        shape = RoundedCornerShape(12.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = MaterialTheme.colorScheme.surfaceContainer
                                        )
                                    ) {
                                        Box(
                                            modifier = Modifier.fillMaxSize()
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Person,
                                                contentDescription = "SpecialistProfileImage",
                                                modifier = Modifier.matchParentSize(),
                                                tint = MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                    }

                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(100.dp)
                                            .graphicsLayer {
                                                shadowElevation = 2.dp.toPx()
                                                shape = RoundedCornerShape(12.dp)
                                                clip = true
                                            },
                                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                                        shape = RoundedCornerShape(12.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = MaterialTheme.colorScheme.surfaceContainer
                                        )
                                    ) {
                                        Box (
                                            modifier = Modifier.fillMaxSize()
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .align(Alignment.CenterStart) // Выравнивание по центру слева
                                                    .padding(start = 15.dp), // Отступ слева
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Column {
                                                    Text(
                                                        text = "Никитин Данила\nИгоревич",
                                                        color = MaterialTheme.colorScheme.onBackground,
                                                        fontSize = 18.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        textAlign = TextAlign.Left
                                                    )
                                                    Text(
                                                        text = "Рейтинг 4,85",
                                                        color = MaterialTheme.colorScheme.onSurface,
                                                        fontSize = 14.sp,
                                                        fontWeight = FontWeight.SemiBold,
                                                        textAlign = TextAlign.Left
                                                    )
                                                }
                                            }
                                            Icon(
                                                modifier = Modifier
                                                    .align(Alignment.TopEnd)
                                                    .padding(top = 15.dp, end = 15.dp),
                                                imageVector = Icons.Default.MoreVert,
                                                contentDescription = "SpecialistProfileImage",
                                                tint = MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                    }
                                }
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                        .graphicsLayer {
                                            shadowElevation = 2.dp.toPx()
                                            shape = RoundedCornerShape(12.dp)
                                            clip = true
                                        },
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                                    )
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "Я — специалист по ремонту с многолетним опытом работы в сфере бытового и технического обслуживания. Моя специализация включает ремонт электроники, бытовой техники, сантехники и мелкие строительные работы. Я всегда стремлюсь к качественному выполнению задач, используя современные инструменты и материалы. Гарантирую оперативность, надёжность и индивидуальный подход к каждому клиенту. Ваше доверие — моя главная награда. Обращайтесь, и я помогу решить любые бытовые проблемы!",
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(15.dp)
                                                .padding(end = 30.dp), // Отступ справа для иконки
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            textAlign = TextAlign.Left,
                                            maxLines = 3, // Ограничение до 3 строк
                                            overflow = TextOverflow.Ellipsis
                                        )

                                        IconButton(
                                            onClick = {  },
                                            modifier = Modifier
                                                .align(Alignment.BottomEnd)
                                                .padding(bottom = 2.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.ArrowDropDown, // Иконка стрелки вниз
                                                contentDescription = "Expand",
                                                tint = MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                    }
                                }
                            }

                            val serviceFormat = listOf(
                                ServiceFormat(format = "Работает дистанционно"),
                                ServiceFormat(format = "Выезжает к вам"),
                                ServiceFormat(format = "Работает у себя", address = "г. Брянск, ул. Никитина, д. 45, кв. 104"),
                            )
                            ServiceFormatCard(serviceFormat)

                            val contactType = listOf(
                                ContactType(
                                    type = "phone",
                                    contact = "+7(975)295-95-93"
                                ),
                                ContactType(
                                    type = "email",
                                    contact = "dan.nikitin@mail.ru"
                                )
                            )
                            ContactCard(contactType)

                            val serviceAndCost = listOf(
                                ServiceAndCost(
                                    serviceType = "Ремонт",
                                    serviceSubType = "Ремонт",
                                    cost = "Цена договорная"
                                ),
                                ServiceAndCost(
                                    serviceType = "Ремонт",
                                    serviceSubType = "Установка кондиционеров",
                                    cost = "От 15000 рублей"
                                ),
                                ServiceAndCost(
                                    serviceType = "Ремонт",
                                    serviceSubType = "Покраска стен",
                                    cost = "От 5000 рублей"
                                ),
                                ServiceAndCost(
                                    serviceType = "Ремонт",
                                    serviceSubType = "Замена сантехники",
                                    cost = "От 8000 рублей"
                                ),
                                ServiceAndCost(
                                    serviceType = "Ремонт",
                                    serviceSubType = "Электромонтажные работы",
                                    cost = "От 70000 руб"
                                )
                            )
                            ServiceAndCostCard(serviceAndCost)

                            Column( //InfoCardImages
                                verticalArrangement = Arrangement.spacedBy(15.dp)
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "Фотографии",
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Left
                                )
                                LazyRow {
                                    item {
                                        Card(
                                            modifier = Modifier
                                                .padding(end = 10.dp)
                                                .size(160.dp)
                                                .graphicsLayer {
                                                    shadowElevation = 3.dp.toPx()
                                                    shape = RoundedCornerShape(12.dp)
                                                    clip = true
                                                },
                                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                                            shape = RoundedCornerShape(12.dp),
                                            colors = CardDefaults.cardColors(
                                                containerColor = MaterialTheme.colorScheme.surfaceContainer
                                            )
                                        ) {
                                            Icon(
                                                modifier = Modifier.fillMaxSize(),
                                                imageVector = Icons.Default.Person, // Иконка стрелки вниз
                                                contentDescription = "Expand",
                                                tint = MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                        Card(
                                            modifier = Modifier
                                                .padding(end = 10.dp)
                                                .size(160.dp)
                                                .graphicsLayer {
                                                    shadowElevation = 3.dp.toPx()
                                                    shape = RoundedCornerShape(12.dp)
                                                    clip = true
                                                },
                                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                                            shape = RoundedCornerShape(12.dp),
                                            colors = CardDefaults.cardColors(
                                                containerColor = MaterialTheme.colorScheme.surfaceContainer
                                            )
                                        ) {
                                            Icon(
                                                modifier = Modifier.fillMaxSize(),
                                                imageVector = Icons.Default.Person, // Иконка стрелки вниз
                                                contentDescription = "Expand",
                                                tint = MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                        Card(
                                            modifier = Modifier
                                                .padding(end = 10.dp)
                                                .size(160.dp)
                                                .graphicsLayer {
                                                    shadowElevation = 3.dp.toPx()
                                                    shape = RoundedCornerShape(12.dp)
                                                    clip = true
                                                },
                                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                                            shape = RoundedCornerShape(12.dp),
                                            colors = CardDefaults.cardColors(
                                                containerColor = MaterialTheme.colorScheme.surfaceContainer
                                            )
                                        ) {
                                            Icon(
                                                modifier = Modifier.fillMaxSize(),
                                                imageVector = Icons.Default.Person, // Иконка стрелки вниз
                                                contentDescription = "Expand",
                                                tint = MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                        Card(
                                            modifier = Modifier
                                                .padding(end = 10.dp)
                                                .size(160.dp)
                                                .graphicsLayer {
                                                    shadowElevation = 3.dp.toPx()
                                                    shape = RoundedCornerShape(12.dp)
                                                    clip = true
                                                },
                                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                                            shape = RoundedCornerShape(12.dp),
                                            colors = CardDefaults.cardColors(
                                                containerColor = MaterialTheme.colorScheme.surfaceContainer
                                            )
                                        ) {
                                            Icon(
                                                modifier = Modifier.fillMaxSize(),
                                                imageVector = Icons.Default.Person, // Иконка стрелки вниз
                                                contentDescription = "Expand",
                                                tint = MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                        Card(
                                            modifier = Modifier
                                                .padding(end = 10.dp)
                                                .size(160.dp)
                                                .graphicsLayer {
                                                    shadowElevation = 3.dp.toPx()
                                                    shape = RoundedCornerShape(12.dp)
                                                    clip = true
                                                },
                                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                                            shape = RoundedCornerShape(12.dp),
                                            colors = CardDefaults.cardColors(
                                                containerColor = MaterialTheme.colorScheme.surfaceContainer
                                            )
                                        ) {
                                            Icon(
                                                modifier = Modifier.fillMaxSize(),
                                                imageVector = Icons.Default.Person, // Иконка стрелки вниз
                                                contentDescription = "Expand",
                                                tint = MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                        Card(
                                            modifier = Modifier
                                                .padding(end = 10.dp)
                                                .size(160.dp)
                                                .graphicsLayer {
                                                    shadowElevation = 3.dp.toPx()
                                                    shape = RoundedCornerShape(12.dp)
                                                    clip = true
                                                },
                                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                                            shape = RoundedCornerShape(12.dp),
                                            colors = CardDefaults.cardColors(
                                                containerColor = MaterialTheme.colorScheme.surfaceContainer
                                            )
                                        ) {
                                            Icon(
                                                modifier = Modifier.fillMaxSize(),
                                                imageVector = Icons.Default.Person, // Иконка стрелки вниз
                                                contentDescription = "Expand",
                                                tint = MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                    }
                                }
                            }

                            val commentList = listOf(
                                CommentModel(
                                    userName = "Иванов Пётр Ильич",
                                    comment = "Все на высшем уровне! Спасибо за работу, сделано качественно и быстро",
                                    commentDate = "13 октября 2025",
                                    serviceType = "Ремонт • Замена сантехники",
                                    grade = 5
                                ),
                                CommentModel(
                                    userName = "Иванов Пётр Ильич",
                                    comment = "Обратился к специалисту для замены смесителя и устранения протечки в ванной. Мастер приехал вовремя, работал аккуратно и профессионально. Все работы выполнил быстро и качественно, использовал современные инструменты и материалы. После замены смесителя протечка полностью устранена, всё работает идеально. Отдельно хочу отметить вежливость и внимательность к деталям. Цены адекватные, результат превзошёл ожидания. Рекомендую этого специалиста для любых сантехнических работ!",
                                    commentDate = "13 октября 2025",
                                    serviceType = "Ремонт • Замена сантехники",
                                    grade = 4
                                ),
                                CommentModel(
                                    userName = "Иванов Пётр Ильич",
                                    comment = "Все на высшем уровне! Спасибо за работу, сделано качественно и быстро",
                                    commentDate = "13 октября 2025",
                                    serviceType = "Ремонт • Замена сантехники",
                                    grade = 3
                                )
                            )
                            CommentCard(commentList)
                        }
                    }
               // }
           // }
        }
    }
}