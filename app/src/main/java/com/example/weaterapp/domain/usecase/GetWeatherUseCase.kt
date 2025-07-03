package com.example.weaterapp.domain.usecase

import com.example.weaterapp.data.remote.model.WeatherResponse
import com.example.weaterapp.domain.model.Weather
import com.example.weaterapp.domain.repository.WeatherRepository
import javax.inject.Inject


class GetWeatherUseCase @Inject constructor(private val repository: WeatherRepository) {
    suspend operator fun invoke(latitude: Double, longitude: Double): Result<WeatherResponse> {
        return try {
            repository.getWeather(latitude, longitude)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}