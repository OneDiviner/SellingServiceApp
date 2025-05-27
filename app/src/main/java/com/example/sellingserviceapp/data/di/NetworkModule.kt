package com.example.sellingserviceapp.data.di


import com.example.sellingserviceapp.BuildConfig
import com.example.sellingserviceapp.data.network.authorization.request.RefreshAccessTokenRequest
import com.example.sellingserviceapp.data.network.authorization.AuthApiService
import com.example.sellingserviceapp.data.network.booking.BookingApiService
import com.example.sellingserviceapp.data.network.offer.OfferApiService
import dagger.Provides
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton



class TokenAuthenticator @Inject constructor(
    private val secureTokenStorage: SecureTokenStorage,
    @Named("refresh") private val authApiService: AuthApiService
): Authenticator {
    override fun authenticate(route: Route?, response: okhttp3.Response): Request? {
        val refreshToken = secureTokenStorage.getRefreshToken()?: return null
        val tokenResponse = runBlocking {
            try {
                authApiService.refreshAccessToken(RefreshAccessTokenRequest(refreshToken))
            } catch (e: Exception) {
                null
            }
        }?: return null
        if(!tokenResponse.isSuccessful) return null
        val newAccessToken = tokenResponse.body()?.access?.token?: "Error"

        secureTokenStorage.updateAccessToken(newAccessToken)

        return response.request.newBuilder()
            .header("Authorization", "Bearer $newAccessToken")
            .build()
    }
}

class TokenInterceptor(
    private val tokenProvider: () -> String?
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val needsAuth = originalRequest.header("Token") == "true"

        val requestBuilder = originalRequest.newBuilder()
        if(needsAuth) {
            val token = tokenProvider()
            if(!token.isNullOrBlank()) {
                requestBuilder
                    .removeHeader("Token")
                    .addHeader("Authorization", "Bearer $token")
            }
        }
        return chain.proceed(requestBuilder.build())
    }
}



@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = BuildConfig.BASE_URL
    private const val BASE_URL_ERROR = "http://192.168.31.19:8080/"
    private const val BASE_URL_IXMALMONK = "http://26.35.149.166:8080/"
    private const val BASE_URL_RANDOM = "http://192.168.69.53:8080/"
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    @Named("refresh_token_client")
    fun provideRefreshTokenOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @Named("main_client")
    fun provideOkHttpClient(
        tokenInterceptor: TokenInterceptor,
        tokenAuthenticator: TokenAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(tokenInterceptor)
            .authenticator(tokenAuthenticator)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @Named("main")
    fun provideRetrofit(@Named("main_client") client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    @Named("refresh")
    fun provideRefreshTokenRetrofit(@Named("refresh_token_client") client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApiService(@Named("main" )retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideOfferApiService(@Named("main")retrofit: Retrofit): OfferApiService {
        return retrofit.create(OfferApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideBookingApiService(@Named("main")retrofit: Retrofit): BookingApiService {
        return retrofit.create(BookingApiService::class.java)
    }

    @Provides
    @Singleton
    @Named("refresh")
    fun provideRefreshTokenAuthApiService(@Named("refresh" )retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideTokenInterceptor(secureTokenStorage: SecureTokenStorage): TokenInterceptor {
        return TokenInterceptor {
            runBlocking { secureTokenStorage.getAccessToken() }
        }
    }
}