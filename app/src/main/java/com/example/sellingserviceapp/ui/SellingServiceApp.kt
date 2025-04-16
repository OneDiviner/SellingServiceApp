package com.example.sellingserviceapp.ui


import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sellingserviceapp.ui.component.BottomNav
import com.example.sellingserviceapp.ui.screen.authentication.AuthenticationSellingServiceApp
import com.example.sellingserviceapp.ui.screen.settings.ScaffoldBottomSheetTest
import com.example.sellingserviceapp.ui.screen.settings.SettingsSellingServiceApp
import com.example.sellingserviceapp.ui.screen.settings.SettingsUI


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellingServiceApp() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            BottomNav(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "auth",
            enterTransition = { fadeIn() + slideInHorizontally(initialOffsetX = { it }) },
            exitTransition = { fadeOut() + slideOutHorizontally(targetOffsetX = { -it }) },
            popEnterTransition = { fadeIn() + slideInHorizontally(initialOffsetX = { -it }) },
            popExitTransition = { fadeOut() + slideOutHorizontally(targetOffsetX = { it }) }
        ) {
            composable("auth") { AuthenticationSellingServiceApp(innerPadding)}
            composable("service") { ServiceScreen() }
            composable("createService") { CreateService() }
            composable("createBooking") { CreateBooking() }
            composable("settings") { SettingsUI(innerPadding) }
        }
    }
}
@Composable
fun ServiceScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp)
    ) {
        Text("Service Screen")
    }
}

@Composable
fun CreateService() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp)
    ) {
        Text("Create Service Screen")
    }
}

@Composable
fun CreateBooking() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp)
    ) {
        Text("Settings Screen")
    }
}