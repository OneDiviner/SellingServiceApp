package com.example.sellingserviceapp.ui.component.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sellingserviceapp.ui.screen.profile.model.SpecialistProfileDataModel

@Composable
fun SpecialistProfileDataCard(
    model: SpecialistProfileDataModel
) {

    val borderColor = if (isSystemInDarkTheme()) {
        Color.White.copy(alpha = 0.05f)
    } else {
        Color.Black.copy(alpha = 0.05f)
    }

    val shadowColor = if(isSystemInDarkTheme()) {
        Color.White.copy(alpha = 0.05f)
    } else {
        Color.Black.copy(alpha = 0.2f)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .graphicsLayer {
                shadowElevation = 8.dp.toPx()
                shape = RoundedCornerShape(8.dp)
                clip = true
                spotShadowColor = shadowColor
                ambientShadowColor = shadowColor
            },
        border = BorderStroke(1.dp, borderColor),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if(isSystemInDarkTheme()) Color(0xFF141414) else Color(0xFFF5F5F5)
        )
    ) {
        Column(
            Modifier.padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .border(
                            border = BorderStroke(1.dp, color = borderColor),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .background(
                            if(isSystemInDarkTheme()) Color(0xFF141414) else Color(0xFFF5F5F5),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .size(width = 80.dp, height = 100.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "AccountIcon",
                        modifier = Modifier
                            .matchParentSize(),
                        //.clickable { launcher.launch("image/*") },
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }

                Column() {
                    Text(
                        text = model.name,
                        modifier = Modifier,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        //style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Left
                    )
                    Text(
                        text = "Рейтинг специалсита",
                        modifier = Modifier.padding(top = 10.dp),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        //style = MaterialTheme.typography.titleLarge,
                        color = if(!isSystemInDarkTheme()) Color(0x80000000) else Color(0x80FFFFFF),
                        textAlign = TextAlign.Left
                    )
                    Row(
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star, // Используем иконку AccountCircle
                            contentDescription = "Account Rating", // Описание для доступности
                            modifier = Modifier.size(20.dp), // Размер иконки
                            tint = Color(0xFFFFFF00) // Цвет иконки (можно изменить)
                        )
                        Text(
                            text = model.rating,
                            fontSize = 14.sp,
                            //style = MaterialTheme.typography.titleLarge,
                            color = if(!isSystemInDarkTheme()) Color(0x80000000) else Color(0x80FFFFFF),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                /*Icon(
                    imageVector = Icons.Default.Edit, // Используем иконку AccountCircle
                    contentDescription = "Account Rating", // Описание для доступности
                    modifier = Modifier.size(20.dp), // Размер иконки
                    tint = MaterialTheme.colorScheme.onBackground // Цвет иконки (можно изменить)
                )*/ //TODO: Для профиля заказчика

                //TODO: Придумать как разместить кнопку раскрытия информации

            }
            Text(
                text = model.about,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Thin,
                //style = MaterialTheme.typography.titleLarge,
                color = if(!isSystemInDarkTheme()) Color(0x80000000) else Color(0x80FFFFFF),
                textAlign = TextAlign.Left,
                maxLines = 3, // Ограничение до 3 строк
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}