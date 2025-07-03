package com.example.weaterapp.domain.usecase

import com.example.weaterapp.data.remote.model.CloudsResponse
import com.example.weaterapp.data.remote.model.CoordResponse
import com.example.weaterapp.data.remote.model.MainResponse
import com.example.weaterapp.data.remote.model.SysResponse
import com.example.weaterapp.data.remote.model.WeatherDescriptionResponse
import com.example.weaterapp.data.remote.model.WeatherResponse
import com.example.weaterapp.data.remote.model.WindResponse
import com.example.weaterapp.domain.repository.WeatherRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class GetWeatherUseCaseTest {

    private lateinit var repository: WeatherRepository
    private lateinit var useCase: GetWeatherUseCase

    @Before
    fun setUp() {
        repository = mock(WeatherRepository::class.java)
        useCase = GetWeatherUseCase(repository)
    }

    @Test
    fun `invoke retorna Result success cuando el repositorio responde correctamente`() = runBlocking {
        val weather = WeatherResponse(
            coord = CoordResponse(
                lon = 42.5,
                lat = 0.5 // Example coordinates
            ),
            weather = arrayOf(
                WeatherDescriptionResponse(
                    id = 800,
                    main = "Clear",
                    description = "clear sky",
                    icon = "01d" // Example icon code
                )
            ).toList(),
            base = "stations",
            main = MainResponse(
                temp_max = 39.0,
                temp_min = 26.0,
                temp = 30.0, // Example temperature
                feels_like = 28.0, // Example feels like temperature
                pressure = 1018, // Example pressure
                humidity = 65, // Example humidity
                sea_level = null, // Example sea level (can be null)
                grnd_level = null // Example ground level (can be null)
            ),
            visibility = 10000,
            wind = WindResponse(
                speed = 3.99,
                deg = 186,
                gust = 9.97 // Example wind gust
            ),
            clouds = CloudsResponse(
                all = 100 // Example cloudiness percentage
            ),
            dt = 1751440000, // Example timestamp
            sys = SysResponse(
                country = "SO",
                sunrise = 1751426090, // Example sunrise timestamp
                sunset = 1751469549 // Example sunset timestamp
            ),
            timezone = 10800,
            id = 656,
            name = "Buur Gaabo", // Nombre de la ciudad obtenida de la API
            cod = 200 // Example cod value
        )
        `when`(repository.getWeather(1.0, 2.0)).thenReturn(Result.success(weather))

        val result = useCase(1.0, 2.0)

        assert(result.isSuccess)
        assertEquals(weather, result.getOrNull())
    }

    @Test
    fun `invoke retorna Result failure cuando el repositorio lanza excepción`() = runBlocking {
        `when`(repository.getWeather(1.0, 2.0)).thenThrow(RuntimeException("Error"))

        val result = useCase(1.0, 2.0)

        assert(result.isFailure)
        assert(result.exceptionOrNull()?.message == "Error")
    }
}