package com.example.sellingserviceapp.ui.screen.main

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sellingserviceapp.ui.screen.main.component.ServiceCardItem

@Composable
fun MainUISkeleton() {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Box(
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.surfaceContainer,
                        shape = RoundedCornerShape(
                            bottomStart = 20.dp,
                            bottomEnd = 20.dp
                        )
                    )
                    .padding(15.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Button(
                        onClick = {

                        },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onBackground.copy(0.1f)
                        ),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .systemBarsPadding()
                            .padding(top = 15.dp)
                            .fillMaxWidth()
                            .height(44.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 15.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                            Text("Поиск", fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground.copy(0.5f))
                        }

                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .background(MaterialTheme.colorScheme.onBackground.copy(0.1f), shape = RoundedCornerShape(20.dp))
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
                                    .height(20.dp)
                                    .fillMaxWidth(0.6f)
                                    .background(color = MaterialTheme.colorScheme.onBackground.copy(0.1f), shape = RoundedCornerShape(12.dp))
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {},
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.onBackground.copy(0.1f)
                            ),
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier
                                .width(120.dp)
                                .height(32.dp)
                        ) {

                        }
                        Button(
                            onClick = {},
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.onBackground.copy(0.1f)
                            ),
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier
                                .width(120.dp)
                                .height(32.dp)
                        ) {

                        }
                        Button(
                            onClick = {},
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.onBackground.copy(0.1f)
                            ),
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier
                                .width(120.dp)
                                .height(32.dp)
                        ) {

                        }
                    }
                }
            }
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            LazyRow(
                modifier = Modifier
                    .wrapContentHeight()
                    .height(40.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
            ) {
                items(5) {
                    FilterChip(
                        modifier = Modifier.height(50.dp).width(80.dp),
                        selected = false,
                        onClick = {},
                        label = {Text("")},
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer,
                        ),
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground.copy(0.15f))
                    )
                }
            }
        }
        items(4) {
            ServiceCardItem(
                onClick = {  },
                title = "",
                price = "",
                subcategory = "",
                photo = "",
                isRefreshing = true
            )
        }
    }
}
