package com.example.nilpay_registration_android.core.di

import com.example.nilpay_registration_android.core.network.AuthInterceptor
import com.example.nilpay_registration_android.domain.utils.Constants.URL.URL_BASE
import com.example.nilpay_registration_android.core.network.RequestInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(okHttp: OkHttpClient): Retrofit {
        return Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create())
            client(okHttp)
            baseUrl(URL_BASE)
        }.build()
    }

    @Singleton
    @Provides
    fun provideOkHttp(
        requestInterceptor: AuthInterceptor,
        authInterceptor: RequestInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            addInterceptor(requestInterceptor)
            addInterceptor(authInterceptor)
            addInterceptor(loggingInterceptor())
        }.build()
    }

    @Provides
    fun provideRequestInterceptor(): RequestInterceptor {
        return RequestInterceptor()
    }

    private fun loggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level =
                HttpLoggingInterceptor.Level.BODY
        }
    }
}
