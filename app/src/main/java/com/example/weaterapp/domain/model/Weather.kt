package com.example.weaterapp.domain.model


data class Weather(
    val coord: Coord,
    val weather: List<WeatherDescription>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Int,
    val id: Int,
    val name: String, // Nombre de la ciudad obtenida de la API
    val cod: Int
)

data class Coord(
    val lon: Double,
    val lat: Double
)

data class WeatherDescription(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int,
    val sea_level: Int?, // Puede ser nulo
    val grnd_level: Int?  // Puede ser nulo
)

data class Wind(
    val speed: Double,
    val deg: Int,
    val gust: Double? // Puede ser nulo
)

data class Clouds(
    val all: Int
)

data class Sys(
    val country: String,
    val sunrise: Long,
    val sunset: Long
)