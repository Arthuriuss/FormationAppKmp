package com.mma.formationappkmp.domain

import kotlinx.serialization.Serializable

@Serializable
data class WeatherEntity(
    val coord: Coord? = null,
    val dt: Int? = null,
    val id: Int? = null,
    val main: Main,
    val name: String,
    val weather: List<Weather>,
    val wind: Wind
) {
    fun getResume(): String {
        return "Il fait ${main.temp}° avec un vent de ${wind.speed} m/s\n" +
                "-Description : ${weather[0].description}\n" +
                "-Icône : ${weather[0].icon}"
    }
}