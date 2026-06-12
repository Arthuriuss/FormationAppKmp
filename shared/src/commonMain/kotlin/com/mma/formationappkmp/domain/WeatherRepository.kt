package com.mma.formationappkmp.domain

interface WeatherRepository {
    suspend fun loadWeather(city: String): OpenWeatherResponse
}