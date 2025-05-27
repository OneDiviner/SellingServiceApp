package com.example.sellingserviceapp.ui.screen.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.sellingserviceapp.R
import com.example.sellingserviceapp.model.domain.ServiceDomain
import com.example.sellingserviceapp.ui.screen.createService.CreateServiceUI
import com.example.sellingserviceapp.ui.screen.main.component.ServiceCardItem
import com.example.sellingserviceapp.ui.screen.main.service.ServiceUI
import com.example.sellingserviceapp.ui.screen.profile.component.ProfileIconButton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainUI(
    viewModel: MainViewModel = hiltViewModel(),
    onProfileButtonClick: () -> Unit,
    onUserServicesButtonClick: () -> Unit,
    onServiceButtonClick: (Int) -> Unit,
) {
    val user by viewModel.userFLow.collectAsState()
    val services by viewModel.servicesFlow.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
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
                            ProfileIconButton(
                                onClick = onProfileButtonClick,
                                photoBase64 = user.avatar ?: ""
                            )
                            Column(
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Text("${user.firstName} ${user.secondName}", fontSize = 24.sp, color = MaterialTheme.colorScheme.onBackground)
                                Text(user.email,fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground.copy(0.7f))
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                onClick = onUserServicesButtonClick,
                                shape = RoundedCornerShape(20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                ),
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier
                                    .width(120.dp)
                                    .height(32.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxSize().padding(horizontal = 5.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.design_services),
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp),
                                        tint = MaterialTheme.colorScheme.onBackground
                                    )
                                    Text(
                                        text ="Мои услуги",
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.onBackground,
                                    )
                                }

                            }
                            Button(
                                onClick = {

                                },
                                shape = RoundedCornerShape(20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                ),
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier
                                    .width(120.dp)
                                    .height(32.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxSize().padding(horizontal = 5.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.book),
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp),
                                        tint = MaterialTheme.colorScheme.onBackground
                                    )
                                    Text(
                                        text ="Мои записи",
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.onBackground,
                                    )
                                }

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
                    items(viewModel.categories) { category ->
                        var selected by remember { mutableStateOf(false) }
                        FilterChip(
                            modifier = Modifier.fillMaxHeight(),
                            selected = selected,
                            onClick = {
                                selected = !selected
                                viewModel.getServiceListByCategory(category.id)
                            },
                            label = {Text(category.name)},
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                            ),
                            shape = RoundedCornerShape(18.dp),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground.copy(0.15f))
                        )
                    }
                    items(viewModel.categories) { category ->
                        var selected by remember { mutableStateOf(false) }
                        FilterChip(
                            modifier = Modifier.fillMaxHeight(),
                            selected = selected,
                            onClick = {selected = !selected},
                            label = {Text(category.name)},
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                            ),
                            shape = RoundedCornerShape(14.dp),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground.copy(0.15f))
                        )
                    }
                }
            }
            items(services.take(2)) { service ->
                ServiceCardItem(
                    onClick = { onServiceButtonClick(service.id) },
                    title = service.tittle,
                    category = service.categoryName,
                    subcategory = service.subcategoryName,
                    photo = service.photo,
                    isRefreshing = viewModel.isRefreshing
                )
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Card(
                    modifier = Modifier.height(250.dp)
                ) {  }

            }
            items(services.drop(2)) { service ->
                ServiceCardItem(
                    onClick = { onServiceButtonClick(service.id) },
                    title = service.tittle,
                    category = service.categoryName,
                    subcategory = service.subcategoryName,
                    photo = service.photo,
                    isRefreshing = viewModel.isRefreshing
                )
            }
        }
    }
}