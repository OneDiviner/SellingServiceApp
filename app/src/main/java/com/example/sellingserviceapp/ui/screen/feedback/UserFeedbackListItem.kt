package com.example.sellingserviceapp.ui.screen.feedback

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sellingserviceapp.R
import com.example.sellingserviceapp.model.FeedbackWithData
import com.example.sellingserviceapp.model.domain.BookingWithData
import com.example.sellingserviceapp.model.mapper.BookingStatusMapper
import com.example.sellingserviceapp.ui.screen.main.component.shimmerBrush
import com.example.sellingserviceapp.ui.screen.offer.ServiceIcon

@Composable
fun UserFeedbackListItem(
    offer: FeedbackWithData,
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    if (isLoading) {
        Box(
            modifier = Modifier
                .background(shimmerBrush(targetValue = 500f, showShimmer = true), shape = RoundedCornerShape(20.dp))
                .fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ServiceIcon(
                        photoBase64 = ""
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .height(25.dp)
                                .fillMaxWidth(0.8f)
                                .background(color = MaterialTheme.colorScheme.onBackground.copy(0.1f), shape = RoundedCornerShape(12.dp))
                        )
                        Box(
                            modifier = Modifier
                                .height(15.dp)
                                .fillMaxWidth(0.6f)
                                .background(color = MaterialTheme.colorScheme.onBackground.copy(0.1f), shape = RoundedCornerShape(12.dp))
                        )
                    }
                }
            }
        }
    } else {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(20.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ServiceIcon(
                        photoBase64 = offer.service.photo ?: ""
                    )
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.padding(end = 2.dp),
                                text = offer.service.tittle,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.Bold
                            )
                            if(offer.feedback.rating != 0) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = Color.Yellow,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    modifier = Modifier,
                                    text = offer.feedback.rating.toString(),
                                    fontSize = 15.sp,
                                    color = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                                    fontWeight = FontWeight.Normal
                                )
                            }
                        }
                        Text(
                            modifier = Modifier,
                            text = if (offer.feedback.comment == "") {
                                "Оставьте отзыв."
                            } else {
                                offer.feedback.comment
                            },
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                            fontWeight = FontWeight.Normal,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}