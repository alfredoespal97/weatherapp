package com.example.weaterapp.ui.weather

import com.example.weaterapp.domain.model.Weather


/**
 * Define los posibles estados de la interfaz de usuario para la pantalla del tiempo.
 * Es un 'sealed class' para asegurar que todos los estados posibles se manejen explícitamente.
 */
sealed class WeatherUiState {
    /**
     * Estado inicial: la pantalla está vacía o esperando una entrada.
     */
    data object Idle : WeatherUiState()

    /**
     * Estado de carga: se está realizando una operación de red o lógica pesada.
     */
    data object Loading : WeatherUiState()

    /**
     * Estado de éxito: los datos del tiempo se han obtenido correctamente y están listos para mostrar.
     * @param weather La entidad Weather que contiene los datos a mostrar.
     */
    data class Success(val weather: Weather) : WeatherUiState()

    /**
     * Estado de error: ha ocurrido un problema durante la obtención de datos o la lógica.
     * @param message El mensaje de error a mostrar al usuario.
     */
    data class Error(val message: String) : WeatherUiState()
}