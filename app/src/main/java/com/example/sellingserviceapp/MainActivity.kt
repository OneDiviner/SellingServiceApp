package com.example.sellingserviceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.sellingserviceapp.ui.SellingServiceApp
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
