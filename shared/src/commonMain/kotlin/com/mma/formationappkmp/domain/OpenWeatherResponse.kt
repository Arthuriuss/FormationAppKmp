package com.mma.formationappkmp.domain

import kotlinx.serialization.Serializable

@Serializable
data class OpenWeatherResponse(
    val cod: String,
    val count: Int,
    val list: List<WeatherEntity>,
    val message: String
) {
    fun getResume(city: String): String {
        return "Il fait ${list[0].main.temp}° à $city (id=$) avec un vent de ${list[0].wind.speed} m/s\n" +
                "-Description : ${list[0].weather[0].description}\n" +
                "-Icône : ${list[0].weather[0].icon}"
    }
}

