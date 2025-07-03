package com.example.weaterapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weaterapp.ui.theme.BlueAccentDark
import com.example.weaterapp.ui.theme.WeatherAppTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.tooling.preview.Preview
import com.example.weaterapp.ui.theme.BlueAccent
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun WeatherSkeleton() {

    val skeletonBackgroundColor = BlueAccent // Un azul vibrante
    val skeletonElementColor = BlueAccentDark   // Un azul ligeramente más oscuro

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = skeletonBackgroundColor // Color de fondo de toda la pantalla del esqueleto
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 24.dp), // Padding general para el contenido
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Elemento superior pequeño (simulando una barra de búsqueda o título pequeño)
            Spacer(modifier = Modifier.height(20.dp)) // Espacio para la barra de estado
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.4f) // Ocupa la mitad del ancho
                    .height(36.dp)      // Altura del elemento
                    .placeholder(
                        visible = true,
                        color = skeletonElementColor,
                        shape = RoundedCornerShape(8.dp),
                        highlight = PlaceholderHighlight.shimmer()
                    )
            )
            Spacer(modifier = Modifier.height(14.dp)) // Espacio entre el elemento superior y el primer bloque grande

            Box(
                modifier = Modifier
                    .fillMaxWidth() // Ocupa la mitad del ancho
                    .height(36.dp)      // Altura del elemento
                    .placeholder(
                        visible = true,
                        color = skeletonElementColor,
                        shape = RoundedCornerShape(8.dp),
                        highlight = PlaceholderHighlight.shimmer()
                    )
            )
            Spacer(modifier = Modifier.height(20.dp)) // Espacio entre el elemento superior y el primer bloque grande

            // Primer bloque de contenido grande
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp) // Altura considerable para el bloque
                    .placeholder(
                        visible = true,
                        color = skeletonElementColor,
                        shape = RoundedCornerShape(8.dp),
                        highlight = PlaceholderHighlight.shimmer()
                    )
            )
            Spacer(modifier = Modifier.height(20.dp)) // Espacio entre bloques
            // Segundo bloque de contenido ancho
            Box(
                modifier = Modifier
                    .fillMaxWidth() // Ocupa la mitad del ancho
                    .height(64.dp)      // Altura del elemento
                    .placeholder(
                        visible = true,
                        color = skeletonElementColor,
                        shape = RoundedCornerShape(8.dp),
                        highlight = PlaceholderHighlight.shimmer()
                    )
            )
            Spacer(modifier = Modifier.height(24.dp)) // Espacio entre bloques
            // Segundo bloque de contenido grande
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .placeholder(
                        visible = true,
                        color = skeletonElementColor,
                        shape = RoundedCornerShape(8.dp),
                        highlight = PlaceholderHighlight.shimmer()
                    )
            )
            Spacer(modifier = Modifier.height(24.dp)) // Espacio entre bloques

            // Tercer bloque de contenido grande
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .placeholder(
                        visible = true,
                        color = skeletonElementColor,
                        shape = RoundedCornerShape(8.dp),
                        highlight = PlaceholderHighlight.shimmer()
                    )
            )

            // Usamos Weight para empujar el elemento inferior al final
            Spacer(modifier = Modifier.weight(1f))

            // Barra inferior pequeña (simulando una barra de navegación o indicador)
            Box(
                modifier = Modifier
                    .width(120.dp) // Ancho específico para la barra inferior
                    .height(8.dp)  // Altura delgada
                    .placeholder(
                        visible = true,
                        color = skeletonElementColor,
                        shape = RoundedCornerShape(8.dp),
                        highlight = PlaceholderHighlight.shimmer()
                    )
            )
        }
    }
}

/**
 * Vista previa del Composable SkeletonHomeScreen.
 */
@Preview(showBackground = true, widthDp = 360, heightDp = 720)
@Composable
fun PreviewSkeletonHomeScreen() {
    WeatherAppTheme {
        WeatherSkeleton()
    }
}
