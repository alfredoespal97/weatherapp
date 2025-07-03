package com.example.weaterapp.data.repository

import com.example.weaterapp.BuildConfig
import com.example.weaterapp.data.mapper.toDomain
import com.example.weaterapp.data.remote.WeatherApiService
import com.example.weaterapp.data.remote.model.WeatherResponse
import com.example.weaterapp.domain.model.Weather
import com.example.weaterapp.domain.repository.WeatherRepository
import jakarta.inject.Inject

// app/data/repository/WeatherRepositoryImpl.kt
class WeatherRepositoryImpl (
    private val apiService: WeatherApiService
) : WeatherRepository {

    override suspend fun getWeather(latitude: Double, longitude: Double): Result<WeatherResponse> {
        return try {
            // Llama al nuevo método de la API con latitud y longitud
            val response = apiService.getCurrentWeather(latitude, longitude, BuildConfig.API_KEY)
            Result.success(response)
        } catch (e: Exception) {
            // Aquí podrías logear el error, o mapear excepciones de red específicas a errores de dominio.
            Result.failure(e)
        }
    }
}
