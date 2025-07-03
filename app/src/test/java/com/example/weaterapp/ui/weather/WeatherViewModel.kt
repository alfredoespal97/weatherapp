package com.example.weaterapp.ui.weather

import app.cash.turbine.test
import app.cash.turbine.awaitItem
import com.example.weaterapp.data.remote.model.CloudsResponse
import com.example.weaterapp.data.remote.model.CoordResponse
import com.example.weaterapp.data.remote.model.MainResponse
import com.example.weaterapp.data.remote.model.SysResponse
import com.example.weaterapp.data.remote.model.WeatherDescriptionResponse
import com.example.weaterapp.data.remote.model.WeatherResponse
import com.example.weaterapp.data.remote.model.WindResponse
import com.example.weaterapp.domain.model.Clouds
import com.example.weaterapp.domain.model.Coord
import com.example.weaterapp.domain.model.Main
import com.example.weaterapp.domain.model.Sys
import com.example.weaterapp.domain.model.Weather
import com.example.weaterapp.domain.model.WeatherDescription
import com.example.weaterapp.domain.model.Wind
import com.example.weaterapp.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    private lateinit var getWeatherByCoordinatesUseCase: GetWeatherUseCase
    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setUp() {
        getWeatherByCoordinatesUseCase = mock(GetWeatherUseCase::class.java)
        viewModel = WeatherViewModel(getWeatherByCoordinatesUseCase)
    }

    @Test
    fun `fetchWeatherByCoordinates emite Success cuando el caso de uso retorna Weather`() =
        runTest {
            val weather = Weather(
                coord = Coord(
                    lon = 42.5,
                    lat = 0.5 // Example coordinates
                ),
                weather = arrayOf(
                    WeatherDescription(
                        id = 800,
                        main = "Clear",
                        description = "clear sky",
                        icon = "01d" // Example icon code
                    )
                ).toList(),
                base = "stations",
                main = Main(
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
                wind = Wind(
                    speed = 3.99,
                    deg = 186,
                    gust = 9.97 // Example wind gust
                ),
                clouds = Clouds(
                    all = 100 // Example cloudiness percentage
                ),
                dt = 1751440000, // Example timestamp
                sys = Sys(
                    country = "SO",
                    sunrise = 1751426090, // Example sunrise timestamp
                    sunset = 1751469549 // Example sunset timestamp
                ),
                timezone = 10800,
                id = 656,
                name = "Buur Gaabo", // Nombre de la ciudad obtenida de la API
                cod = 200 // Example cod value
            )
            val weatherResponse = WeatherResponse(
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
            whenever(getWeatherByCoordinatesUseCase.invoke(1.0, 2.0)).thenReturn(
                Result.success(
                    weatherResponse
                )
            )

            // viewModel.fetchWeatherByCoordinates(1.0, 2.0)

            viewModel.uiState.test {
                awaitItem() // Ignora Idle
                viewModel.fetchWeatherByCoordinates(1.0, 2.0)
                assertEquals(WeatherUiState.Loading, awaitItem())
                val success = awaitItem()
                assert(success is WeatherUiState.Success && (success as WeatherUiState.Success).weather == weather)
            }
        }

    @Test
    fun `fetchWeatherByCoordinates emite Error cuando el caso de uso retorna Result failure`() =
        runTest {
            whenever(getWeatherByCoordinatesUseCase.invoke(1.0, 2.0))
                .thenReturn(Result.failure(RuntimeException("Error")))

            viewModel.uiState.test {
                awaitItem() // Ignora Idle
                viewModel.fetchWeatherByCoordinates(1.0, 2.0)
                assertEquals(WeatherUiState.Loading, awaitItem())
                val error = awaitItem()
                assert(error is WeatherUiState.Error)
            }
        }

}