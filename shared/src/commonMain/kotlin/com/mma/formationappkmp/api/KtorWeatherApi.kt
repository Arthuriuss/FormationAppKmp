package com.mma.formationappkmp.api

import com.mma.formationappkmp.BuildConfig
import com.mma.formationappkmp.di.initKoin
import com.mma.formationappkmp.domain.OpenWeatherResponse
import com.mma.formationappkmp.domain.WeatherRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.koin.mp.KoinPlatform

suspend fun main() {
    initKoin()
    val ktorWeatherApi = KoinPlatform.getKoin().get<KtorWeatherApi>()

    val city = "Le Mans"
    val weather : OpenWeatherResponse = ktorWeatherApi.loadWeather(city = city)
    println(weather.getResume(city = city))
    println("Liste météo : ${weather.list.map { it.name }}")
    ktorWeatherApi.client.close()
}

class KtorWeatherApi(val client: HttpClient): WeatherRepository {
    private val API_URL = "https://api.openweathermap.org/data/2.5/find?q=Toulouse&appid=&units=metric&lang=fr"
    private val BASE_URL = "https://api.openweathermap.org/data/2.5/find"
    private val API_KEY = BuildConfig.WEATHER_API_KEY

    override suspend fun loadWeather(city: String): OpenWeatherResponse {
        val response = client.get(BASE_URL) {
            // On ajoute les paramètres dynamiquement
            parameter("q", city)
            parameter("appid", API_KEY)
            parameter("units", "metric")
            parameter("lang", "fr")
        }

        if (!response.status.isSuccess()) {
            throw Exception("Erreur API: ${response.status} - ${response.bodyAsText()}")
        }

        return response.body<OpenWeatherResponse>()
    }

}