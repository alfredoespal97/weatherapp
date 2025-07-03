package com.example.weaterapp.ui.components // Adjust the package according to your structure

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn // Using LocationOn for location icon
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.WaterDrop // For precipitation/humidity
import androidx.compose.material.icons.filled.Air // For wind
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.weaterapp.R
import com.example.weaterapp.domain.model.Clouds
import com.example.weaterapp.domain.model.Coord
import com.example.weaterapp.domain.model.Main
import com.example.weaterapp.domain.model.Sys
import com.example.weaterapp.domain.model.Weather // Make sure to import your Weather entity
import com.example.weaterapp.domain.model.WeatherDescription
import com.example.weaterapp.domain.model.Wind
import com.example.weaterapp.ui.theme.BlueAccent
import com.example.weaterapp.ui.theme.DarkBlueBackground
import com.example.weaterapp.ui.theme.CardBackground
import com.example.weaterapp.ui.theme.TextWhite
import com.example.weaterapp.ui.theme.TextLightGray
import com.example.weaterapp.ui.theme.WeatherAppTheme // Make sure to have your theme imported
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Main Composable for the weather display screen.
 * Displays weather data obtained from the API.
 *
 * @param weather The weather data to display.
 */
@Composable
fun WeatherDisplayScreen(weather: Weather, onRefresh: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BlueAccent // Screen background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            WeatherTopBar(cityName = weather.name, onRefresh = onRefresh)
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = SimpleDateFormat(
                    "dd 'de' MMMM | HH:mm",
                    Locale.getDefault()
                ).format(Date()), // Dynamic date/time
                color = TextLightGray,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Main Weather Info
            MainWeatherSection(weather = weather)
            Spacer(modifier = Modifier.height(24.dp))

            // Circular Indicators (Pressure, Clouds, Humidity)
            CircularIndicatorsSection(
                pressure = weather.main.pressure,
                cloudiness = weather.clouds.all,
                humidity = weather.main.humidity
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Wind Details Card
            WindDetailsCard(
                windSpeed = weather.wind.speed,
                windGust = weather.wind.gust,
                windDegrees = weather.wind.deg
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Sunrise/Sunset Card
            SunriseSunsetCard(
                sunriseTimestamp = weather.sys.sunrise,
                sunsetTimestamp = weather.sys.sunset
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

/**
 * Composable for the application's top bar.
 * Contains settings, search/location, and share icons.
 *
 * @param cityName The name of the city to display.
 */
@Composable
fun WeatherTopBar(cityName: String, onRefresh: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onRefresh) {
            Icon(Icons.Default.Settings, contentDescription = "Settings", tint = TextWhite)
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
                .background(DarkBlueBackground, RoundedCornerShape(24.dp)) // Search bar background
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.near_me), // Replace with your wind icon
                contentDescription = "Near icon",
                modifier = Modifier.size(20.dp) // Adjust size as needed
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = cityName, // Dynamic city name
                color = TextWhite,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.Search, contentDescription = "Search", tint = TextWhite)
        }
        IconButton(onClick = { /* TODO: Handle share click */ }) {
            Icon(Icons.Default.Share, contentDescription = "Share", tint = TextWhite)
        }
    }
}

/**
 * Composable for the main weather information section.
 * Displays temperature, description, wind speed, precipitation, etc.
 *
 * @param weather The weather data to display.
 */
@Composable
fun MainWeatherSection(weather: Weather) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left side: Wind Speed and Precipitation (using placeholder for precipitation as it's not directly in Weather model)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(verticalAlignment = Alignment.CenterVertically) {
//
                    Image(
                        painter = painterResource(id = R.drawable.west), // Replace with your wind icon
                        contentDescription = "Wind icon",
                        modifier = Modifier.size(20.dp) // Adjust size as needed
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${weather.wind.speed.toInt()} km/h",
                        color = TextLightGray,
                        fontSize = 14.sp
                    ) // Dynamic wind speed
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.rainy), // Replace with your wind icon
                        contentDescription = "Reany icon",
                        modifier = Modifier.size(20.dp) // Adjust size as needed
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "2,5mm",
                        color = TextLightGray,
                        fontSize = 14.sp
                    ) // Static precipitation (not in current Weather model)
                }
            }

            // Center: Weather Icon and Temperature
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.cielo_limpio2), // Replace with your logo or icon
                    contentDescription = "Cielo Image",
                    modifier = Modifier.size(55.dp) // Adjust size as needed
                )
                Text(
                    text = "${weather.main.temp.toInt()}°", // Dynamic temperature
                    color = TextWhite,
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Right side: Max/Min Temperature
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.vertical_align_top), // Replace with your wind icon
                        contentDescription = "Top icon",
                        modifier = Modifier.size(20.dp) // Adjust size as needed
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${weather.main.temp_max.toInt()}°",
                        color = TextLightGray,
                        fontSize = 14.sp
                    ) // Dynamic max temp
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.vertical_align_bottom), // Replace with your wind icon
                        contentDescription = "Bottom icon",
                        modifier = Modifier.size(20.dp) // Adjust size as needed
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${weather.main.temp_min.toInt()}°",
                        color = TextLightGray,
                        fontSize = 14.sp
                    ) // Dynamic min temp
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Sensación de ${weather.main.feels_like.toInt()}°", // Dynamic feels like temperature
            color = TextLightGray,
            fontSize = 16.sp
        )
        weather.weather.forEach(
            // Assuming weather.weather is a list of WeatherDescription
        ) { description ->
//            Text(
//                text = description.main.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }, // Main weather condition (first letter capitalized)
//                color = TextWhite,
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Bold
//            )
            Text(
                text = description.description.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }, // Weather description (first letter capitalized)
                color = TextWhite,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }


    }
}

/**
 * Composable to display a circular indicator with text.
 *
 * @param value The main value to display (e.g., 1018).
 * @param unit The unit of the value (e.g., hPa).
 * @param label The indicator label (e.g., Pressure).
 * @param percentage The percentage for the circle arc (0-100).
 * @param circleColor The color of the progress circle.
 */
@Composable
fun CircularIndicator(
    value: String,
    unit: String,
    label: String,
    percentage: Float,
    circleColor: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(90.dp) // Circle size
                .clip(CircleShape)
                .background(CardBackground), // Circle background
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                // Circle background
                drawCircle(
                    color = Color.Gray.copy(alpha = 0.3f), // Arc background color
                    radius = size.minDimension / 2f,
                    style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
                )
                // Progress arc
                drawArc(
                    color = circleColor,
                    startAngle = -90f,
                    sweepAngle = percentage * 3.6f, // 360 degrees * percentage
                    useCenter = false,
                    style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = value,
                    color = TextWhite,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = unit, color = TextLightGray, fontSize = 12.sp)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = label, color = TextLightGray, fontSize = 14.sp)
    }
}

/**
 * Composable that groups circular indicators.
 *
 * @param pressure The pressure value.
 * @param cloudiness The cloudiness percentage.
 * @param humidity The humidity percentage.
 */
@Composable
fun CircularIndicatorsSection(pressure: Int, cloudiness: Int, humidity: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularIndicator(
            value = pressure.toString(),
            unit = "hPa",
            label = "Presión",
            percentage = (pressure / 1020f) * 100f,
            circleColor = Color(0xFF64B5F6)
        ) // Dynamic pressure, percentage adjusted for typical range
        CircularIndicator(
            value = "$cloudiness%",
            unit = "",
            label = "Nubes",
            percentage = cloudiness.toFloat(),
            circleColor = Color(0xFFEF5350)
        ) // Dynamic cloudiness
        CircularIndicator(
            value = "$humidity%",
            unit = "",
            label = "Humedad",
            percentage = humidity.toFloat(),
            circleColor = Color(0xFF4CAF50)
        ) // Dynamic humidity
    }
}

/**
 * Composable for the wind details card.
 *
 * @param windSpeed The wind speed.
 * @param windGust The wind gust speed (can be null).
 * @param windDegrees The wind direction in degrees.
 */
@Composable
fun WindDetailsCard(windSpeed: Double, windGust: Double?, windDegrees: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = Color.White,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = BlueAccent),
            shape = RoundedCornerShape(12.dp)

        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Air, // Wind icon
                        contentDescription = "Wind icon",
                        tint = TextWhite,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Viento",
                        color = TextWhite,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = windSpeed.toInt().toString(),
                                color = TextWhite,
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Bold
                            ) // Dynamic wind speed
                            Spacer(modifier = Modifier.width(4.dp))
                            Column {
                                Text(text = "km/h", color = TextLightGray, fontSize = 14.sp)
                                Text(text = "Viento", color = TextLightGray, fontSize = 14.sp)
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Divider(
                            color = Color.White,
                            thickness = 2.dp,
                            modifier = Modifier.fillMaxWidth(0.6f)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        if (windGust != null) {
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(
                                    text = windGust.toInt().toString(),
                                    color = TextWhite,
                                    fontSize = 48.sp,
                                    fontWeight = FontWeight.Bold
                                ) // Dynamic wind gust
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "km/h", color = TextLightGray, fontSize = 14.sp)
                                Text(text = "Rachas", color = TextLightGray, fontSize = 14.sp)
                            }
                        }
                    }
                    // Wind Compass
                    WindCompass(degree = windDegrees) // Dynamic wind degrees
                }
            }
        }
    }
}

/**
 * Composable for the wind compass.
 *
 * @param degree The wind direction degree (0-360).
 */
@Composable
fun WindCompass(degree: Int) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(TextWhite), // A darker blue for the compass
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2, size.height / 2)
            val radius = size.minDimension / 2 - 4.dp.toPx()

            // Outer circle
            drawCircle(
                color = Color.Gray.copy(alpha = 0.1f),
                radius = radius,
                style = Stroke(width = 2.dp.toPx())
            )

            // Direction lines (N, E, S, W)
            val lineLength = radius * 0.2f
            drawLine(
                Color.Gray,
                start = Offset(center.x, center.y - radius),
                end = Offset(center.x, center.y - radius + lineLength),
                strokeWidth = 2.dp.toPx()
            ) // N
            drawLine(
                Color.Gray,
                start = Offset(center.x + radius, center.y),
                end = Offset(center.x + radius - lineLength, center.y),
                strokeWidth = 2.dp.toPx()
            ) // E
            drawLine(
                Color.Gray,
                start = Offset(center.x, center.y + radius),
                end = Offset(center.x, center.y + radius - lineLength),
                strokeWidth = 2.dp.toPx()
            ) // S
            drawLine(
                Color.Gray,
                start = Offset(center.x - radius, center.y),
                end = Offset(center.x - radius + lineLength, center.y),
                strokeWidth = 2.dp.toPx()
            ) // W
        }
        Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxHeight().padding(start = 14.dp)) {
                Text(text = "W", color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            // Degrees and direction text
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center ,modifier = Modifier.fillMaxHeight().weight(0.8f)) {
                Text(text = "N", color = Color.Red, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$degree°",
                    color = Color.Gray,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ) // Dynamic degrees
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "S",//getWindDirection(degree),
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                ) // Dynamic abbreviated direction
            }
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxHeight().padding(end = 14.dp)) {
                Text(text = "E", color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }

    }
}

/**
 * Composable for the sunrise and sunset card.
 *
 * @param sunriseTimestamp The timestamp for sunrise.
 * @param sunsetTimestamp The timestamp for sunset.
 */
@Composable
fun SunriseSunsetCard(sunriseTimestamp: Long, sunsetTimestamp: Long) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = Color.White,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = BlueAccent),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.wb_twilight), // Replace with your wind icon
                            contentDescription = "Sunset icon",
                            modifier = Modifier.size(24.dp) // Adjust size as needed
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Puesta de sol",
                            color = TextWhite,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    // Sunset
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth() ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Anochece ${formatTimestampToTime(sunsetTimestamp)}",
                                color = TextLightGray,
                                fontSize = 14.sp
                            ) // Dynamic sunset time
                            Image(
                                painter = painterResource(id = R.drawable.puesta_de_sol), // Replace with your sunset icon
                                contentDescription = "Sunset graphic",
                                modifier = Modifier.size(60.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        // Sunrise
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Amanece ${formatTimestampToTime(sunriseTimestamp)}",
                                color = TextLightGray,
                                fontSize = 14.sp
                            ) // Dynamic sunrise time
                            Image(
                                painter = painterResource(id = R.drawable.puesta_de_sol2), // Replace with your sunset icon
                                contentDescription = "Sunset graphic",
                                modifier = Modifier.size(60.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Helper function to format a Unix timestamp (seconds) to a "HH:mm" time string.
 */
fun formatTimestampToTime(timestamp: Long): String {
    val date = Date(timestamp * 1000) // Convert seconds to milliseconds
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formatter.format(date)
}

/**
 * Helper function to get cardinal wind direction from degrees.
 */
fun getWindDirection(degrees: Int): String {
    val directions = arrayOf("N", "NE", "E", "SE", "S", "SW", "W", "NW", "N")
    return directions[((degrees % 360) / 45).toInt()]
}

/**
 * Preview of the WeatherDisplayScreen Composable.
 */
@Preview(showBackground = true, widthDp = 360, heightDp = 720)
@Composable
fun PreviewWeatherDisplayScreen() {
    WeatherAppTheme {
        // Example data for the preview, matching the improved Weather model
        val sampleWeather = Weather(
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
        WeatherDisplayScreen(weather = sampleWeather, onRefresh = { /* Handle refresh action */ })
    }
}

