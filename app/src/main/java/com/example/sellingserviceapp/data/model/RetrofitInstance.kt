package com.example.sellingserviceapp.data.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.31.190:8080/") // Укажите ваш URL сервера
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}