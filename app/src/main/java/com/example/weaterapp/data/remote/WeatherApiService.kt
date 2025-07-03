package com.example.weaterapp.data.remote

// app/data/remote/WeatherApiService.kt
import com.example.weaterapp.data.remote.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double, // Ahora acepta latitud
        @Query("lon") longitude: Double, // Ahora acepta longitud
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric" // Para obtener la temperatura en Celsius
    ): WeatherResponse
}