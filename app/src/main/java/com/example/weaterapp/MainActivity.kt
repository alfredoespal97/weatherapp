package com.example.weaterapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.weaterapp.ui.WeatherScreen
import com.example.weaterapp.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint
import com.example.weaterapp.ui.theme.BlueAccent


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BlueAccent
                ) {
                    WeatherScreen()
                }
            }
        }
    }
}
