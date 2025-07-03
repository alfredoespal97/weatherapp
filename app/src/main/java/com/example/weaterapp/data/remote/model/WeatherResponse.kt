package com.example.weaterapp.data.remote.model


data class WeatherResponse(
    val coord: CoordResponse,
    val weather: List<WeatherDescriptionResponse>,
    val base: String,
    val main: MainResponse,
    val visibility: Int,
    val wind: WindResponse,
    val clouds: CloudsResponse,
    val dt: Long,
    val sys: SysResponse,
    val timezone: Int,
    val id: Int,
    val name: String, // Nombre de la ciudad obtenida de la API
    val cod: Int
)

data class CoordResponse(
    val lon: Double,
    val lat: Double
)

data class WeatherDescriptionResponse(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class MainResponse(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int,
    val sea_level: Int?, // Puede ser nulo
    val grnd_level: Int?  // Puede ser nulo
)

data class WindResponse(
    val speed: Double,
    val deg: Int,
    val gust: Double? // Puede ser nulo
)

data class CloudsResponse(
    val all: Int
)

data class SysResponse(
    val country: String,
    val sunrise: Long,
    val sunset: Long
)