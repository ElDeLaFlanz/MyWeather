package com.example.myweather.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myweather.data.WeatherItem

@Composable
fun DayCard(date: String, items: List<WeatherItem>) {

    val min = items.minOf { it.temperature }
    val max = items.maxOf { it.temperature }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Text(date)

            Text("Мин: $min°C  Макс: $max°C")

            Spacer(modifier = Modifier.height(6.dp))

            items.take(4).forEach {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(it.time.substringAfter("T"))
                    Text(getWeatherIcon(it.weatherCode))
                    Text("${it.temperature}°C")
                }
            }
        }
    }
}

fun getWeatherIcon(code: Int): String {
    return when (code) {
        0 -> "☀"
        1, 2 -> "🌤"
        3 -> "☁"
        in 45..48 -> "🌫"
        in 51..67 -> "🌧"
        in 71..77 -> "❄"
        in 80..82 -> "🌦"
        else -> "❓"
    }
}
