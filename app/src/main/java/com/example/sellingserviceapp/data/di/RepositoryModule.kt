package com.example.sellingserviceapp.data.di

import com.example.sellingserviceapp.data.local.repository.IPriceTypesRepository
import com.example.sellingserviceapp.data.local.repository.ISubcategoriesRepository
import com.example.sellingserviceapp.data.local.repository.PriceTypesRepository
import com.example.sellingserviceapp.data.local.repository.SubcategoriesRepository
import com.example.sellingserviceapp.data.local.repository.categories.CategoriesRepository
import com.example.sellingserviceapp.data.local.repository.categories.ICategoriesRepository
import com.example.sellingserviceapp.data.local.repository.service.IServiceRepository
import com.example.sellingserviceapp.data.local.repository.service.ServiceRepository
import com.example.sellingserviceapp.data.local.repository.user.IUserRepository
import com.example.sellingserviceapp.data.local.repository.user.UserRepository
import com.example.sellingserviceapp.data.network.authorization.repository.AuthRepository
import com.example.sellingserviceapp.data.network.authorization.repository.AuthRepositoryImpl
import com.example.sellingserviceapp.data.network.booking.BookingRepository
import com.example.sellingserviceapp.data.network.booking.IBookingRepository
import com.example.sellingserviceapp.data.network.offer.repository.OfferRepository
import com.example.sellingserviceapp.data.network.offer.repository.OfferRepositoryImpl
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

    @Binds
    @Singleton
    abstract fun bindUserRepository(repository: UserRepository): IUserRepository

    @Binds
    @Singleton
    abstract fun bindCategoriesRepository(repository: CategoriesRepository): ICategoriesRepository

    @Binds
    @Singleton
    abstract fun bindServiceRepository(repository: ServiceRepository): IServiceRepository

    @Binds
    @Singleton
    abstract fun bindSubcategoriesRepository(repository: SubcategoriesRepository): ISubcategoriesRepository

    @Binds
    @Singleton
    abstract fun bindPriceTypesRepository(repository: PriceTypesRepository): IPriceTypesRepository

    @Binds
    @Singleton
    abstract fun bindBookingRepository(repository: BookingRepository): IBookingRepository
}