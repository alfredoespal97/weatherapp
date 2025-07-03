package com.example.weaterapp.ui

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weaterapp.domain.model.Weather
import com.example.weaterapp.ui.weather.WeatherUiState
import com.example.weaterapp.ui.weather.WeatherViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import timber.log.Timber
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.weaterapp.R
import com.example.weaterapp.ui.components.WeatherDisplayScreen
import com.example.weaterapp.ui.components.WeatherSkeleton
import com.example.weaterapp.ui.theme.BlueAccent
import com.example.weaterapp.ui.theme.WeatherAppTheme


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WeatherScreen(viewModel: WeatherViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var locationErrorMessage by remember { mutableStateOf<String?>(null) }
    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val cancellationTokenSource = remember { CancellationTokenSource() }

    @SuppressLint("MissingPermission")
    fun fetchLocationAndWeather() {
        if (locationPermissionsState.allPermissionsGranted) {
            viewModel.setLoading()
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource.token
            ).addOnSuccessListener { location ->
                if (location != null) {
                    Timber.tag("WeatherScreen")
                        .d("Latitud: ${location.latitude}, Longitud: ${location.longitude}")
                    viewModel.fetchWeatherByCoordinates(location.latitude, location.longitude)
                    locationErrorMessage = null
                } else {
                    locationErrorMessage =
                        "No se pudo obtener la ubicación actual. Inténtalo de nuevo."
                    viewModel.setError(locationErrorMessage!!)
                }
            }.addOnFailureListener { exception ->
                locationErrorMessage = "Error al obtener ubicación: ${exception.message}"
                viewModel.setError(locationErrorMessage!!)
            }
        } else {
            locationErrorMessage = "Permisos de ubicación no concedidos."
            viewModel.setError(locationErrorMessage!!)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        when (val currentState = uiState) {
            WeatherUiState.Idle, is WeatherUiState.Error -> {

                Image(
                    painter = painterResource(id = R.drawable.logo_carreras),
                    contentDescription = "Logo de la app",
                    modifier = Modifier.size(240.dp), // Ajusta el tamaño según tu preferencia
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(100.dp))
                Button(
                    onClick = {
                        locationErrorMessage = null
                        fetchLocationAndWeather()
                    },
                    enabled = uiState != WeatherUiState.Loading,
                    modifier = Modifier.fillMaxWidth (0.6f) // Ocupa el 60% del ancho de la pantalla
                        .height(60.dp),    // Altura del botón
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White, // Fondo blanco
                        contentColor = MaterialTheme.colorScheme.primary // Texto del color primario del tema
                    ),
                    shape = RoundedCornerShape(12.dp), // Esquinas redondeadas
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                ) {
                    Text(if (currentState is WeatherUiState.Error) "Reintentar" else "Comenzar",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                if (locationErrorMessage != null) {
                    Spacer(Modifier.height(8.dp))
                    Text(text = locationErrorMessage!!,
                        color = Color.Red,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold)
                } else if (locationPermissionsState.shouldShowRationale ||
                    (!locationPermissionsState.allPermissionsGranted)
                ) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Se necesitan permisos de ubicación para obtener el clima de tu posición actual.",
                        color = Color.DarkGray,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
                if (currentState is WeatherUiState.Error) {
                    Spacer(Modifier.height(8.dp))
                    Text("Error: ${currentState.message}",
                        color = Color.Red,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold)
                }
            }

            WeatherUiState.Loading -> {
                WeatherSkeleton()
            }

            is WeatherUiState.Success -> {
                WeatherDisplayScreen(
                    weather = currentState.weather,
                    onRefresh = {
                        locationErrorMessage = null
                        fetchLocationAndWeather()
                    }
                )
            }
        }
    }
}

@Composable
fun StartScreen(
    onComenzarClick: () -> Unit // Callback para cuando se pulsa el botón "Comenzar"
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BlueAccent // Fondo de la pantalla
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Centra el contenido verticalmente
        ) {
            // Logo "carreras"
            Image(
                painter = painterResource(id = R.drawable.logo_carreras),
                contentDescription = "Logo de la app",
                modifier = Modifier
                    .size(240.dp) // Ajusta el tamaño del logo
                    .padding(bottom = 16.dp), // Espacio debajo del logo
                contentScale = ContentScale.Fit // Escala el contenido para que se ajuste al tamaño del logo
            )

            Spacer(modifier = Modifier.height(100.dp)) // Espacio entre el logo y el botón

            // Botón "Comenzar"
            Button(
                onClick = onComenzarClick,
                modifier = Modifier
                    .fillMaxWidth(0.6f) // Ocupa el 60% del ancho de la pantalla
                    .height(60.dp),    // Altura del botón
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White, // Fondo blanco
                    contentColor = MaterialTheme.colorScheme.primary // Texto del color primario del tema
                ),
                shape = RoundedCornerShape(12.dp), // Esquinas redondeadas
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp) // Sombra
            ) {
                Text(
                    text = "Comenzar",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * Vista previa del Composable StartScreen.
 */
@Preview(showBackground = true)
@Composable
fun PreviewStartScreen() {
    WeatherAppTheme { // Envuelve la vista previa con tu tema de la aplicación
        StartScreen(onComenzarClick = { /* Do nothing for preview */ })
    }
}