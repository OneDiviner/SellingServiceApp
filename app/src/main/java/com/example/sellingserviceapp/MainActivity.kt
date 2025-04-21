package com.example.sellingserviceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.sellingserviceapp.ui.SellingServiceApp
import com.example.sellingserviceapp.ui.screen.settings.ProfileUI
import com.example.sellingserviceapp.ui.screen.settings.clientProfile.ClientProfileUI
import com.example.sellingserviceapp.ui.theme.SellingServiceAppTheme
import dagger.hilt.android.AndroidEntryPoint

//TODO: Продумать какая информация может быть в профиле пользователя как поставщика услуги

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SellingServiceAppTheme() {
                //AuthenticationSellingServiceApp()
                //SettingsSellingServiceApp()
                SellingServiceApp()
                //ProfileUI(PaddingValues())
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun Preview() {
    //AuthenticationSellingServiceApp()
    //SettingsSellingServiceApp()
}