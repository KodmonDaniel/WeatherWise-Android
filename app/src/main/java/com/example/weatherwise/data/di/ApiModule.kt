package com.example.weatherwise.data.di

import com.example.weatherwise.api.ApiDataSource
import com.example.weatherwise.api.ApiInterceptor
import com.example.weatherwise.api.ApiService
import com.example.weatherwise.base.Config
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ApiInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun retrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Config.baseUrl)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().build()
                )
            ).build()
    }

    @Provides
    @Singleton
    fun apiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiDataSource(apiService: ApiService): ApiDataSource {
        return ApiDataSource(apiService)
    }

}