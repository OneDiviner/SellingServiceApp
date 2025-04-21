package com.example.sellingserviceapp.data.di


import com.example.sellingserviceapp.BuildConfig
import com.example.sellingserviceapp.data.network.AuthApiService
import dagger.Provides
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val BASE_URL = BuildConfig.BASE_URL
private const val BASE_URL_ERROR = "http://192.168.31.19:8080/"
private const val BASE_URL_IXMALMONK = "http://26.35.149.166:8080/"
private const val BASE_URL_RANDOM = "http://192.168.69.53:8080/"
val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}


class AuthInterceptor(
    private val token: String,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(request)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            //.addInterceptor(AuthInterceptor("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI1QG1haWwucnUiLCJyb2xlIjoiVXNlciIsInN0YXR1cyI6IkFjdGl2ZSIsInRva2VuX3R5cGUiOiJBY2Nlc3MiLCJpYXQiOjE3NDE2ODE1ODEsImV4cCI6MTc0MTY4MjQ4MX0.QUa7_cgWILJ2GHNBQcLJvJ6-3RSnwswHMRHNAH84BPg8tIymNik2E0xZdi6zF9YilhFMKox1J_Fk_FEak4tYZQ"))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }
}