package com.example.weaterapp.domain.repository

import com.example.weaterapp.data.remote.model.WeatherResponse


interface WeatherRepository {
    suspend fun getWeather(latitude: Double, longitude: Double): Result<WeatherResponse>
}