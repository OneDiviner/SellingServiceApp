package com.example.sellingserviceapp.data.di

import com.example.sellingserviceapp.data.manager.categoryData.CategoryDataRepository
import com.example.sellingserviceapp.data.manager.categoryData.ICategoryDataRepository
import com.example.sellingserviceapp.data.manager.feedbackData.FeedbackDataRepository
import com.example.sellingserviceapp.data.manager.feedbackData.IFeedbackDataRepository
import com.example.sellingserviceapp.data.manager.formatData.FormatDataRepository
import com.example.sellingserviceapp.data.manager.formatData.IFormatDataRepository
import com.example.sellingserviceapp.data.manager.priceTypeData.IPriceTypeDataRepository
import com.example.sellingserviceapp.data.manager.priceTypeData.PriceTypeDataRepository
import com.example.sellingserviceapp.data.manager.serviceData.IServiceDataRepository
import com.example.sellingserviceapp.data.manager.serviceData.ServiceDataRepository
import com.example.sellingserviceapp.data.manager.userServiceData.IUserServiceDataRepository
import com.example.sellingserviceapp.data.manager.userServiceData.UserServiceDataRepository
import com.example.sellingserviceapp.data.manager.subcategoryData.ISubcategoryDataRepository
import com.example.sellingserviceapp.data.manager.subcategoryData.SubcategoryDataRepository
import com.example.sellingserviceapp.data.manager.userData.IUserDataRepository
import com.example.sellingserviceapp.data.manager.userData.UserDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun bindUserDataRepository(binding: UserDataRepository): IUserDataRepository

    @Binds
    @Singleton
    abstract fun bindUserServiceDataRepository(binding: UserServiceDataRepository): IUserServiceDataRepository

    @Binds
    @Singleton
    abstract fun bindServiceDataRepository(binding: ServiceDataRepository): IServiceDataRepository

    @Binds
    @Singleton
    abstract fun bindCategoryDataRepository(binding: CategoryDataRepository): ICategoryDataRepository

    @Binds
    @Singleton
    abstract fun bindSubcategoryDataRepository(binding: SubcategoryDataRepository): ISubcategoryDataRepository

    @Binds
    @Singleton
    abstract fun bindFormatDataRepository(binding: FormatDataRepository): IFormatDataRepository

    @Binds
    @Singleton
    abstract fun bindPriceTypeDataRepository(binding: PriceTypeDataRepository): IPriceTypeDataRepository

    @Binds
    @Singleton
    abstract fun bindFeedbackDataRepository(binding: FeedbackDataRepository) : IFeedbackDataRepository
}