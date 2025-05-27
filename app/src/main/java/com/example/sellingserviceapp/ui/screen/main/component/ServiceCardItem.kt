package com.example.sellingserviceapp.ui.screen.main.component

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sellingserviceapp.R
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.Button
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.zIndex

@Composable
fun shimmerBrush(targetValue: Float = 300f, showShimmer: Boolean = true): Brush {
    return if (showShimmer) {
        val shimmerColors = listOf(
            MaterialTheme.colorScheme.surfaceContainer,
            MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.5f),
            MaterialTheme.colorScheme.surfaceContainer,
        )
        val transition = rememberInfiniteTransition(label = "shimmer_transition")
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(1000),
                repeatMode = RepeatMode.Restart
            ),
            label = "shimmer_translate_animation"
        )
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = translateAnimation.value, y = translateAnimation.value)
        )
    } else {
        Brush.linearGradient( // Обычный фон, если шиммер не нужен
            colors = listOf(Color.Transparent, Color.Transparent),
            start = Offset.Zero,
            end = Offset.Zero
        )
    }
}

@Composable
fun ServiceCardItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    title: String,
    category: String,
    subcategory: String,
    photo: String?,
    isRefreshing: Boolean = false
) {
    val imageBitmap = remember(photo) {
        if (!photo.isNullOrEmpty()) {
            try {
                val decodedBytes =
                    Base64.decode(photo, Base64.DEFAULT)
                BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)?.asImageBitmap()
            } catch (e: IllegalArgumentException) {
                // Ошибка декодирования Base64, например, если строка некорректна
                e.printStackTrace()
                null
            }
        } else {
            null
        }
    }
    val gradient = listOf(
        MaterialTheme.colorScheme.surfaceContainer,
        MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.5f),
        MaterialTheme.colorScheme.surfaceContainer,
    )
    Card(
        modifier = modifier,
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column(
            verticalArrangement =  if (isRefreshing) Arrangement.spacedBy(5.dp) else Arrangement.spacedBy(0.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(shimmerBrush(targetValue = 1000f, showShimmer = isRefreshing), shape = RoundedCornerShape(20.dp))
                    .fillMaxWidth()
                    .aspectRatio(0.75f)
            ) {
                if (imageBitmap != null ) {
                    Image(
                        bitmap = imageBitmap,
                        contentDescription = title, // Описание для доступности
                        modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(20.dp)), // Изображение заполняет Box
                        contentScale = ContentScale.Crop // Масштабирование, чтобы заполнить пространство, обрезая лишнее
                    )
                } else {
                    Image(
                        modifier = Modifier.size(80.dp), // Размер плейсхолдера
                        painter = painterResource(R.drawable.car_gear), // Ваш плейсхолдер
                        contentDescription = "Placeholder Image",
                        contentScale = ContentScale.Fit,
                        alpha = 0f
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = if (isRefreshing) 0.7f else 1f)
                    .wrapContentHeight()
                    .background(
                        brush = if (isRefreshing) Brush.linearGradient(colors = gradient) else Brush.linearGradient(colors = listOf(Color.Transparent, Color.Transparent)),
                        shape = RoundedCornerShape(20.dp)
                    )
            ) {
                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(0.85f),
                    modifier = Modifier
                        .padding(top = 5.dp)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(
                        brush = if (isRefreshing) Brush.linearGradient(colors = gradient) else Brush.linearGradient(colors = listOf(Color.Transparent, Color.Transparent)),
                        shape = RoundedCornerShape(20.dp)
                    )
            ) {
                Text(
                    text = "От 500Р за услугу",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = if (isRefreshing) 0.5f else 1f)
                    .wrapContentHeight()
                    .background(
                        brush = if (isRefreshing) Brush.linearGradient(colors = gradient) else Brush.linearGradient(colors = listOf(Color.Transparent, Color.Transparent)),
                        shape = RoundedCornerShape(20.dp))
            ) {
                Text(subcategory, fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground.copy(0.7f))
            }
            /*Button(
                modifier = Modifier.fillMaxWidth(0.95f).padding(top = 5.dp),
                onClick = {},
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Отркыть", fontSize = 18.sp)
            }*/
        }
    }
}