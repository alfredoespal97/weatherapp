package com.example.weaterapp.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weaterapp.data.mapper.toDomain
import com.example.weaterapp.domain.usecase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase // Caso de uso inyectado
) : ViewModel() {

    // MutableStateFlow privado para poder actualizar el estado internamente
    val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Idle)

    // StateFlow público e inmutable para que la UI lo observe
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    /**
     * Inicia la búsqueda del tiempo para las coordenadas dadas.
     * Actualiza el _uiState con los estados de Loading, Success o Error.
     * @param latitude La latitud de la ubicación.
     * @param longitude La longitud de la ubicación.
     */
    fun fetchWeatherByCoordinates(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading // Cambia el estado a Carga

            try {
                val result = getWeatherUseCase(latitude, longitude)
                if (result.isSuccess) {
                    _uiState.value = WeatherUiState.Success(result.getOrThrow().toDomain())
                } else {
                    _uiState.value = WeatherUiState.Error(
                        result.exceptionOrNull()?.message ?: "Error desconocido"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = WeatherUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun setLoading() {
        _uiState.value = WeatherUiState.Loading
    }

    fun setError(message: String) {
        _uiState.value = WeatherUiState.Error(message)
    }

}