package com.example.myweather.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweather.data.WeatherItem
import com.example.myweather.network.RetrofitClient
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class WeatherViewModel : ViewModel() {

    var cityInput by mutableStateOf("")
    var selectedCity by mutableStateOf("")
    var weatherList by mutableStateOf<List<WeatherItem>>(emptyList())
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    //
    var cities by mutableStateOf(
        listOf("Helsinki", "Moscow", "Berlin", "Izhevsk", "Glazov")
    )

    //СЛОВАРЬ переводов
    private val cityMap = mapOf(
        "москва" to "Moscow",
        "хельсинки" to "Helsinki",
        "берлин" to "Berlin",
        "ижевск" to "Izhevsk",
        "глазов" to "Glazov"
    )

    //Функция перевода
    private fun translateCity(name: String): String {
        val lower = name.lowercase()
        return cityMap[lower] ?: name
    }

    fun addCity() {
        val city = cityInput.trim()

        if (city.isEmpty()) {
            errorMessage = "Введите название города"
            return
        }

        if (!cities.contains(city)) {
            cities = cities + city
        }

        cityInput = ""
    }

    fun removeCity(city: String) {
        cities = cities - city
    }

    fun loadWeatherForCity(city: String) {
        cityInput = city
        loadWeather()
    }

    fun loadWeather() {
        val originalName = cityInput.trim()

        if (originalName.isEmpty()) {
            errorMessage = "Введите город"
            return
        }

        //ПЕРЕВОД
        val cityName = translateCity(originalName)

        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = ""
                weatherList = emptyList()

                val cityResponse =
                    RetrofitClient.apiNinjasService.getCity(cityName = cityName)

                if (cityResponse.isEmpty()) {
                    errorMessage = "Город не найден"
                    return@launch
                }

                val city = cityResponse.first()

                //Показываем РУССКОЕ имя
                selectedCity = "$originalName (${city.country})"

                val weather =
                    RetrofitClient.openMeteoService.getWeather(
                        latitude = city.latitude,
                        longitude = city.longitude
                    )

                weatherList = weather.hourly.time.indices.map { i ->
                    WeatherItem(
                        time = weather.hourly.time[i],
                        temperature = weather.hourly.temperature_2m[i],
                        weatherCode = weather.hourly.weathercode[i]
                    )
                }

            } catch (e: UnknownHostException) {
                errorMessage = "Нет интернета"
            } catch (e: Exception) {
                errorMessage = "Ошибка: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
}

