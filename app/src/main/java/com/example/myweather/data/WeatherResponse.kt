package com.example.myweather.data

data class WeatherResponse(
    val hourly: HourlyWeather
)

data class HourlyWeather(
    val time: List<String>,
    val temperature_2m: List<Double>,
    val weathercode: List<Int>
)
