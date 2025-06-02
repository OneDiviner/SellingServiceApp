package com.example.sellingserviceapp.ui.screen.profile.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sellingserviceapp.R
import com.example.sellingserviceapp.data.network.booking.Booking
import com.example.sellingserviceapp.ui.screen.createService.model.Subcategory

data class Order(
    val tittle: String,
    val category: String,
    val subcategory: String,
    @DrawableRes val icon: Int = R.drawable.construction
) {
    companion object {
        val EMPTY = Order(
            tittle = "tittle",
            category = "category",
            subcategory = "subcategory",
            icon = R.drawable.construction
        )
    }
}

@Composable
fun ActiveOrderItemButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    booking: Booking = Booking.EMPTY
) {
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
                    painter = painterResource(R.drawable.book),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                    tint = Color(0xFFFFFFFF)
                )
                Column() {
                    Text(
                        text = booking.status,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 1,
                        overflow = TextOverflow.Clip,
                        modifier = Modifier
                            .fillMaxWidth()
                            .basicMarquee(
                                animationMode = MarqueeAnimationMode.Immediately,
                                repeatDelayMillis = 2000,
                                initialDelayMillis = 1000,
                                iterations = Int.MAX_VALUE,
                                spacing = MarqueeSpacing(10.dp),
                                velocity = 20.dp,
                            )
                    )
                    Text(
                        modifier = Modifier,
                        text = "${booking.startDateTime}•${booking.statusReason}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                    )
                }
            }
        }

    }
}