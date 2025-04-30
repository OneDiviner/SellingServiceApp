package com.example.sellingserviceapp.data.di

import com.example.sellingserviceapp.data.repository.AuthRepository
import com.example.sellingserviceapp.data.repository.AuthRepositoryImpl
import com.example.sellingserviceapp.data.repository.OfferRepository
import com.example.sellingserviceapp.data.repository.OfferRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(repository: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindOfferRepository(repository: OfferRepositoryImpl): OfferRepository
}