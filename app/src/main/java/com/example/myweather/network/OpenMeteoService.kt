package com.example.myweather.network

import com.example.myweather.data.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoService {

    @GET("v1/forecast")
    suspend fun getWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("hourly") hourly: String = "temperature_2m,weathercode",
        @Query("forecast_days") forecastDays: Int = 3
    ): WeatherResponse
}
