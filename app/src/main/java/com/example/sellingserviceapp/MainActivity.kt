package com.example.sellingserviceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.sellingserviceapp.ui.screen.authentication.AuthenticationSellingServiceApp
import com.example.sellingserviceapp.ui.screen.authentication.registration.RegistrationScreen
import com.example.sellingserviceapp.ui.theme.SellingServiceAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SellingServiceAppTheme() {
                //LogInScreen()
                //RegistrationScreen()
                AuthenticationSellingServiceApp()
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun Preview() {
    //LogInScreen()
    //RegistrationScreen()
    AuthenticationSellingServiceApp()
}