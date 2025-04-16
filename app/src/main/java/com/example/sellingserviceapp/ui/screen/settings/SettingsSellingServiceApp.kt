package com.example.sellingserviceapp.ui.screen.settings

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sellingserviceapp.ui.component.BackTopBar
import com.example.sellingserviceapp.ui.component.SettingsTopBar
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsSellingServiceApp(innerPadding: PaddingValues) {


    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())


    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize(),
        /*topBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            MediumTopAppBar(
                modifier = Modifier,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.background
                ),
                title = {
                    Button(
                        modifier = Modifier
                            .height(48.dp)
                            .width(200.dp),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer
                        ),
                        onClick = {}
                    ) {
                        Row(
                            modifier = Modifier.padding(5.dp),
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Card(
                                modifier = Modifier
                                    .size(40.dp),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                                shape = RoundedCornerShape(100.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.background
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null, // Описание для доступности
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .fillMaxSize(),
                                    tint = Color(0xFFF5F5F5)
                                )
                            }
                            Text(
                                text = "Никитин Даниил",
                                color = Color(0xFFF5F5F5),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Left,
                            )
                        }
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },*/
        content = { paddingValues ->
            SettingsUI(paddingValues)
            //ScrollContent(innerPadding = paddingValues)
        }
    )
           /* Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = MaterialTheme.colorScheme.background
            ) { }*//*
                *//*NavHost(
                    navController,
                    startDestination = "login", // start screen must be login
                    enterTransition = { fadeIn() + slideInHorizontally(initialOffsetX = { it }) },
                    exitTransition = { fadeOut() + slideOutHorizontally(targetOffsetX = { -it }) },
                    popEnterTransition = { fadeIn() + slideInHorizontally(initialOffsetX = { -it }) },
                    popExitTransition = { fadeOut() + slideOutHorizontally(targetOffsetX = { it }) }
                ) {
                    composable("login") { LogInScreen(navController = navController) }
                    composable("registration") { RegistrationScreen(navController = navController) }
                    composable("userInfo") { UserInfoScreen(navController = navController) }
                }*//*
                //UserProfileScreen()
                //SpecialistProfileUI()
                //ClientProfileUI()
                //ClientProfileUI()
                //SettingsUI()
                //ScrollContent(innerPadding = paddingValues)
                //SettingsUI(innerPadding)

        }
    )*/
}

@Composable
fun ScrollContent(innerPadding: PaddingValues) {
    val range = listOf("Search", "ProfileButton")
    val range2 = 1..100


    val scrollState = rememberLazyListState()
    val scrollOffset = remember { derivedStateOf { scrollState.firstVisibleItemScrollOffset.toFloat() } }



    LazyColumn(
        state = scrollState,
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(range2.count()) { index ->
            Button(
                modifier = Modifier
                    .height(90.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                ),
                onClick = {}
            ) {
                Text(text = "- List item number ${index + 1}")
            }
        }
    }
}