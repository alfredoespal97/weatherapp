package com.example.weaterapp.data.mapper


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


// app/data/mapper/WeatherMapper.kt
fun WeatherResponse.toDomain(): Weather {
    return Weather(
        coord = coord.toDomain(),
        weather = this.weather.map {
            it.toDomain()
        },
        base = this.base,
        main = main.toDomain(),
        visibility = this.visibility,
        wind = wind.toDomain(),
        clouds = clouds.toDomain(),
        dt = this.dt,
        sys = sys.toDomain(),
        timezone = this.timezone,
        id = this.id,
        name = this.name, // Nombre de la ciudad
        cod = this.cod
    )
}

fun CoordResponse.toDomain(): Coord {
    return Coord(
        lon = this.lon,
        lat = this.lat
    )
}

fun WeatherDescriptionResponse.toDomain(): WeatherDescription {
    return WeatherDescription(
        id = this.id,
        main = this.main,
        description = this.description,
        icon = this.icon
    )
}

fun MainResponse.toDomain(): Main {
    return Main(
        temp = this.temp,
        feels_like = this.feels_like,
        temp_min = this.temp_min,
        temp_max = this.temp_max,
        pressure = this.pressure,
        humidity = this.humidity,
        sea_level = this.sea_level,
        grnd_level = this.grnd_level
    )
}

fun WindResponse.toDomain(): Wind {
    return Wind(
        speed = this.speed,
        deg = this.deg,
        gust = this.gust
    )
}

fun CloudsResponse.toDomain(): Clouds {
    return Clouds(
        all = this.all
    )
}

fun SysResponse.toDomain(): Sys {
    return Sys(
        country = this.country,
        sunrise = this.sunrise,
        sunset = this.sunset
    )
}