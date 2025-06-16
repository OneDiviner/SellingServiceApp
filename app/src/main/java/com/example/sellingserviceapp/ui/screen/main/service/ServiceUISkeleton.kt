package com.example.sellingserviceapp.ui.screen.main.service

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.example.sellingserviceapp.ui.screen.main.component.shimmerBrush
import com.example.sellingserviceapp.ui.screen.profile.component.ProfileIconButton

@Composable
fun ServiceUISkeleton() {
    val gradient = listOf(
        MaterialTheme.colorScheme.surfaceContainer,
        MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.5f),
        MaterialTheme.colorScheme.surfaceContainer,
    )
    LazyColumn(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
                    .background(shimmerBrush(targetValue = 1000f, showShimmer = true), shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
                contentAlignment = Alignment.BottomEnd
            ) {}
        }
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(horizontal = 15.dp)
            ) {
                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .fillMaxWidth(0.7f)
                        .background(
                            brush = Brush.linearGradient(colors = gradient),
                            shape = RoundedCornerShape(20.dp)
                        )
                )
                Box(
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .height(25.dp)
                        .fillMaxWidth(0.9f)
                        .background(
                            brush = Brush.linearGradient(colors = gradient),
                            shape = RoundedCornerShape(20.dp)
                        )
                )
                Box(
                    modifier = Modifier
                        .height(15.dp)
                        .fillMaxWidth(0.5f)
                        .background(
                            brush = Brush.linearGradient(colors = gradient),
                            shape = RoundedCornerShape(20.dp)
                        )
                )
                Box(
                    modifier = Modifier
                        .height(15.dp)
                        .fillMaxWidth(0.5f)
                        .background(
                            brush = Brush.linearGradient(colors = gradient),
                            shape = RoundedCornerShape(20.dp)
                        )
                )
                Box(
                    modifier = Modifier
                        .height(15.dp)
                        .fillMaxWidth(0.5f)
                        .background(
                            brush = Brush.linearGradient(colors = gradient),
                            shape = RoundedCornerShape(20.dp)
                        )
                )
                Box(
                    modifier = Modifier
                        .height(15.dp)
                        .fillMaxWidth(0.5f)
                        .background(
                            brush = Brush.linearGradient(colors = gradient),
                            shape = RoundedCornerShape(20.dp)
                        )
                )
            }
        }
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(horizontal = 15.dp)
            ) {
                Text(
                    text = "Описание",
                    color = MaterialTheme.colorScheme.onBackground
                )
                Box(
                    modifier = Modifier
                        .height(15.dp)
                        .fillMaxWidth(1f)
                        .background(
                            brush = Brush.linearGradient(colors = gradient),
                            shape = RoundedCornerShape(20.dp)
                        )
                )
                Box(
                    modifier = Modifier
                        .height(15.dp)
                        .fillMaxWidth(1f)
                        .background(
                            brush = Brush.linearGradient(colors = gradient),
                            shape = RoundedCornerShape(20.dp)
                        )
                )
                Box(
                    modifier = Modifier
                        .height(15.dp)
                        .fillMaxWidth(1f)
                        .background(
                            brush = Brush.linearGradient(colors = gradient),
                            shape = RoundedCornerShape(20.dp)
                        )
                )
                Box(
                    modifier = Modifier
                        .height(15.dp)
                        .fillMaxWidth(1f)
                        .background(
                            brush = Brush.linearGradient(colors = gradient),
                            shape = RoundedCornerShape(20.dp)
                        )
                )
                Box(
                    modifier = Modifier
                        .height(15.dp)
                        .fillMaxWidth(0.9f)
                        .background(
                            brush = Brush.linearGradient(colors = gradient),
                            shape = RoundedCornerShape(20.dp)
                        )
                )

            }
        }
        item {
            Column(
                modifier = Modifier.padding(horizontal = 15.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    ProfileIconButton(
                        onClick = {},
                        photoBase64 = ""
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .height(15.dp)
                                .fillMaxWidth()
                                .background(
                                    brush = Brush.linearGradient(colors = gradient),
                                    shape = RoundedCornerShape(20.dp)
                                )
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                imageVector = Icons.Default.Call,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                            Box(
                                modifier = Modifier
                                    .height(12.dp)
                                    .fillMaxWidth(0.5f)
                                    .background(
                                        brush = Brush.linearGradient(colors = gradient),
                                        shape = RoundedCornerShape(20.dp)
                                    )
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                imageVector = Icons.Default.Email,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                            Box(
                                modifier = Modifier
                                    .height(12.dp)
                                    .fillMaxWidth(0.5f)
                                    .background(
                                        brush = Brush.linearGradient(colors = gradient),
                                        shape = RoundedCornerShape(20.dp)
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}