package com.anthonydevelopedthis.ad340.api

import com.squareup.moshi.Json

data class Forecast(val temp: Float)
data class Coordinates(val lat: Float, val lon: Float)
data class Icon(val icon: String)

data class CurrentWeather(
    val name: String,
    val coord: Coordinates,
    val icon: Icon,
    @field:Json(name = "main") val forecast: Forecast
)


/*
import com.squareup.moshi.Json

data class WeatherDescription(val main: String, val description: String, val icon: String)

data class Temp(val min: Float, val max: Float)

data class DailyForecast(
    @field:Json(name = "dt") val date: Long,
    val temp: Temp,
    val weather: List<WeatherDescription>
)

data class WeeklyForecast(val daily: List<DailyForecast>)

 */