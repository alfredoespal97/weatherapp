package com.example.weaterapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.weaterapp.data.remote.WeatherApiService
import javax.inject.Singleton
import com.example.weaterapp.BuildConfig
import com.example.weaterapp.data.repository.WeatherRepositoryImpl
import com.example.weaterapp.domain.repository.WeatherRepository
import com.example.weaterapp.domain.usecase.GetWeatherUseCase

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherApiService(retrofit: Retrofit): WeatherApiService {
        return retrofit.create(WeatherApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideWeatherRepository(apiService: WeatherApiService): WeatherRepository {
        return WeatherRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideGetWeatherUseCase(weatherRepository: WeatherRepository): GetWeatherUseCase {
        return GetWeatherUseCase(weatherRepository)
    }
}