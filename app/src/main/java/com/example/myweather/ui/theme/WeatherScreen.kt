package com.example.myweather.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myweather.data.WeatherItem
import com.example.myweather.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(vm: WeatherViewModel = viewModel()) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("MyWeather", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = vm.cityInput,
            onValueChange = { vm.cityInput = it },
            label = { Text("Введите город") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Button(
                onClick = { vm.loadWeather() },
                modifier = Modifier.weight(1f)
            ) {
                Text("Погода")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { vm.addCity() },
                modifier = Modifier.weight(1f)
            ) {
                Text("Добавить")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Города:", style = MaterialTheme.typography.titleMedium)

        LazyColumn {

            items(vm.cities) { city ->
                CityItem(
                    city = city,
                    onShow = { vm.loadWeatherForCity(city) },
                    onDelete = { vm.removeCity(city) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))

                if (vm.isLoading) {
                    CircularProgressIndicator()
                }

                if (vm.errorMessage.isNotEmpty()) {
                    Text(vm.errorMessage, color = MaterialTheme.colorScheme.error)
                }

                if (vm.weatherList.isNotEmpty()) {
                    WeatherResult(vm.selectedCity, vm.weatherList)
                }
            }
        }
    }
}
