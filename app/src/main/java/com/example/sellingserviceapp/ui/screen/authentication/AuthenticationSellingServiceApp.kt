package com.example.sellingserviceapp.ui.screen.authentication

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sellingserviceapp.ui.component.BackTopBar
import com.example.sellingserviceapp.ui.screen.authentication.login.LogInScreen
import com.example.sellingserviceapp.ui.screen.authentication.registration.RegistrationScreen
import com.example.sellingserviceapp.ui.screen.authentication.userinfo.UserInfoScreen

@Composable
fun AuthenticationSellingServiceApp(
    innerPadding: PaddingValues
) {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            BackTopBar(navController = navController)
                 },
        content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = MaterialTheme.colorScheme.background
            ) {
                NavHost(
                    navController,
                    startDestination = "login", // start screen must be login
                    enterTransition = { fadeIn() + slideInHorizontally(initialOffsetX = { it }) },
                    exitTransition = { fadeOut() + slideOutHorizontally(targetOffsetX = { -it }) },
                    popEnterTransition = { fadeIn() + slideInHorizontally(initialOffsetX = { -it }) },
                    popExitTransition = { fadeOut() + slideOutHorizontally(targetOffsetX = { it }) }
                ) {
                    composable("login") { LogInScreen(navController = navController, paddingValues = innerPadding) }
                    composable("registration") { RegistrationScreen(navController = navController) }
                    composable("userInfo") { UserInfoScreen(navController = navController) }
                }
            }
        }
    )
}