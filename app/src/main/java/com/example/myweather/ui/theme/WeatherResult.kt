package com.example.myweather.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.example.myweather.data.WeatherItem

@Composable
fun WeatherResult(city: String, list: List<WeatherItem>) {

    val current = list.first()
    val grouped = list.groupBy { it.time.substring(0, 10) }

    Spacer(modifier = Modifier.height(8.dp))

    Text(city, style = MaterialTheme.typography.titleLarge)

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(getWeatherIcon(current.weatherCode))
            Text("${current.temperature}°C")
        }
    }

    Spacer(modifier = Modifier.height(12.dp))

    grouped.entries.take(3).forEach {
        DayCard(it.key, it.value)
    }
}
