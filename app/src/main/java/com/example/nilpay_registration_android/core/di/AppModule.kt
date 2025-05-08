package com.example.nilpay_registration_android.core.di

import com.example.nilpay_registration_android.data.datasource.local.CustomerDao
import com.example.nilpay_registration_android.data.datasource.remote.AppApi
import com.example.nilpay_registration_android.data.repository.AppRepositoryImpl
import com.example.nilpay_registration_android.domain.repository.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): AppApi {
        return retrofit.create(AppApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(
        appApi: AppApi,
    ): AppRepository {
        return AppRepositoryImpl(appApi)
    }
}