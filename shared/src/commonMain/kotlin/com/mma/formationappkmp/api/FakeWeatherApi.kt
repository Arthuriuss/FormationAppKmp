package com.mma.formationappkmp.api

import com.mma.formationappkmp.BuildConfig
import com.mma.formationappkmp.di.initKoin
import com.mma.formationappkmp.domain.Main
import com.mma.formationappkmp.domain.OpenWeatherResponse
import com.mma.formationappkmp.domain.Weather
import com.mma.formationappkmp.domain.WeatherEntity
import com.mma.formationappkmp.domain.WeatherRepository
import com.mma.formationappkmp.domain.Wind
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

class FakeWeatherApi: WeatherRepository {

    override suspend fun loadWeather(city: String): OpenWeatherResponse {
        return OpenWeatherResponse(
            cod = "",
            count = 0,
            message = "",
            list = listOf(
                WeatherEntity(
                    id = 1,
                    name = "Paris",
                    main = Main(temp = 18.5),
                    weather = listOf(
                        Weather(description = "ciel dégagé", icon = "https://picsum.photos/200")
                    ),
                    wind = Wind(speed = 5.0)
                ),
                WeatherEntity(
                    id = 2,
                    name = "Toulouse",
                    main = Main(temp = 22.3),
                    weather = listOf(
                        Weather(description = "partiellement nuageux", icon = "https://picsum.photos/201")
                    ),
                    wind = Wind(speed = 3.2)
                ),
                WeatherEntity(
                    id = 3,
                    name = "Toulon",
                    main = Main(temp = 25.1),
                    weather = listOf(
                        Weather(description = "ensoleillé", icon = "https://picsum.photos/202")
                    ),
                    wind = Wind(speed = 6.7)
                ),
                WeatherEntity(
                    id = 4,
                    name = "Lyon",
                    main = Main(temp = 19.8),
                    weather = listOf(
                        Weather(description = "pluie légère", icon = "https://picsum.photos/203")
                    ),
                    wind = Wind(speed = 4.5)
                )
            ).shuffled() //shuffled() pour avoir un ordre différent à chaque appel
        )
    }

}