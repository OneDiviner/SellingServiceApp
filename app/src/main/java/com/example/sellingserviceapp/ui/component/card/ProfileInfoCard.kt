package com.example.sellingserviceapp.ui.component.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sellingserviceapp.ui.screen.profile.model.ProfileInfoItemModel


@Composable
fun ProfileInfoCard(
    title: String,
    items: List<ProfileInfoItemModel>
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

    val cardColor = if(isSystemInDarkTheme()) {
        Color(0xFF141414)
    } else {
        Color(0xFFF5F5F5)
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
        border = BorderStroke(1.dp, color = borderColor),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        )
    ) {
        Column(
            Modifier
                .padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 24.sp,
                //style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Left
            )
            items.forEach{ item ->
                Row(
                    modifier = Modifier.padding(vertical = 5.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Icon(
                        imageVector = item.icon, // Используем иконку AccountCircle
                        contentDescription = item.description, // Описание для доступности
                        modifier = Modifier.size(24.dp), // Размер иконки
                        tint = MaterialTheme.colorScheme.onBackground // Цвет иконки (можно изменить)
                    )
                    Text(
                        text = item.text,
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