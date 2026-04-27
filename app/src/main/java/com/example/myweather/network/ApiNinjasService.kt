package com.example.myweather.network

import com.example.myweather.data.CityResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiNinjasService {

    @GET("v1/city")
    suspend fun getCity(
        @Header("X-Api-Key") apiKey: String = Constants.API_NINJAS_KEY,
        @Query("name") cityName: String
    ): List<CityResponse>
}
