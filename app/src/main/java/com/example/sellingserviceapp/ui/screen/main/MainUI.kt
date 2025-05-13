package com.example.sellingserviceapp.ui.screen.main

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sellingserviceapp.ui.screen.profile.Note
import com.example.sellingserviceapp.ui.screen.profile.ProfileUI
import com.example.sellingserviceapp.ui.screen.profile.component.HorizontalPagerItem
import com.example.sellingserviceapp.ui.screen.profile.component.ProfileIconButton
import kotlinx.coroutines.launch

@Composable
fun Dp.minus(other: Dp): Dp {
    val density = LocalDensity.current
    return with(density) {
        (this@minus.toPx() - other.toPx()).toDp()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainUI(
    navController: NavController
) {

    val localDensity = LocalDensity.current
    var columnHeightPx by remember { mutableIntStateOf(0) }
    var columnHeightDp by remember {
        mutableStateOf(0.dp)
    }
    val configuration = LocalWindowInfo.current
    val screenHeight by remember { mutableIntStateOf(configuration.containerSize.height) }
    val sheetPeekHeight  = with(localDensity) {(screenHeight - columnHeightPx).toDp() - 15.dp}

    //val sheetState = rememberStandardBottomSheetState()
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = SheetState(
            initialValue = SheetValue.Hidden,
            skipPartiallyExpanded = false,
            density = localDensity,
            confirmValueChange = { newValue ->
                newValue != SheetValue.Expanded
            },
            skipHiddenState = false
        )
    )

    BottomSheetScaffold(
        modifier = Modifier,
        sheetPeekHeight = sheetPeekHeight,
        containerColor = MaterialTheme.colorScheme.background,
        sheetDragHandle = null,
        scaffoldState = scaffoldState,
        sheetContent = {
            ProfileTestUI(PaddingValues())
        }
    ) {
        LazyColumn (
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .onGloballyPositioned { pos ->
                            columnHeightPx = pos.size.height
                        }
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                        )
                        .systemBarsPadding()
                ) {
                    Column(
                        modifier = Modifier.padding(bottom = 15.dp),
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
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
                                    text = "User Name", // Поле с именем пользователя
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
                            val scope = rememberCoroutineScope()
                            ProfileIconButton(
                                onClick = {
                                    scope.launch {
                                        scaffoldState.bottomSheetState.show()
                                    }

                                          },
                                photoBase64 = ""
                            )
                        }
                        val notifications = listOf(
                            Note("Системное уведомление", "У вас 3 новых чего-то там заюерите сейчас пока у вас не забрали и куча ошибок"),
                            Note("Новый заказ", "Вам пришел новый заказа от пользователя"),
                            Note("Имя записи", "Специалист подтвердил вашу запись")
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
                                HorizontalPagerItem(
                                    notifications = notifications,
                                    page = page
                                )
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
                    }
                }
            }
        }
    }
}