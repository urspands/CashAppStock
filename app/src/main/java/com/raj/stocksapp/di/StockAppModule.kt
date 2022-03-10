package com.raj.stocksapp.di

import com.raj.stocksapp.api.StockApi
import com.raj.stocksapp.repository.DataRepository
import com.raj.stocksapp.repository.DataRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StockAppModule {

    @Provides
    @Singleton
    fun provideRepository(stockApi: StockApi): DataRepository =
        DataRepositoryImpl(stockApi)

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(StockApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideStockApi(retrofit: Retrofit): StockApi =
        retrofit.create(StockApi::class.java)
}