package com.mma.formationappkmp.presentation.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mma.formationappkmp.api.KtorWeatherApi
import com.mma.formationappkmp.domain.Main
import com.mma.formationappkmp.domain.Weather
import com.mma.formationappkmp.domain.WeatherEntity
import com.mma.formationappkmp.domain.WeatherRepository
import com.mma.formationappkmp.domain.Wind
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    val dataList = MutableStateFlow(emptyList<WeatherEntity>())
    val dataListFiltered = MutableStateFlow(emptyList<WeatherEntity>())
    val runInProgress = MutableStateFlow(false)
    val errorMessage = MutableStateFlow("")

    val searchQuery = MutableStateFlow("")
    val error = MutableStateFlow(false)

    init {
        //Création d'un jeu de donnée au démarrage
        //Log.d("test", "Instanciation de MainViewModel")
        //loadData()
    }

    fun onSearch(query: String) {
        //Log.d("test", "new query : $query")
        searchQuery.update { query }
        loadData()
        /*dataListFiltered.value = dataList.value.filter {
            it.name.contains(query, ignoreCase = true)
        }*/
    }

    fun onClearSearch() {
        onSearch(query = "")
    }

    fun loadData() {
        runInProgress.value = true
        viewModelScope.launch(Dispatchers.IO) {

            /*val newData = listOf(
                KtorWeatherApi.loadWeather(city = "Le Mans").list[0],
                KtorWeatherApi.loadWeather(city = "Paris").list[0],
                KtorWeatherApi.loadWeather(city = "Toulouse").list[0],
                KtorWeatherApi.loadWeather(city = "Montpellier").list[0],
                KtorWeatherApi.loadWeather(city = "Lyon").list[0]
            )*/
            try {
                val newData = weatherRepository.loadWeather(city = searchQuery.value).list

                dataList.value = newData
                dataListFiltered.value = newData
                error.value = false
            } catch (e: Exception) {
                dataList.value = emptyList()
                dataListFiltered.value = emptyList()
                error.value = true
            }
            runInProgress.value = false

        }
    }

    //Version avancée
    fun loadFakeData(runInProgress :Boolean = false, errorMessage:String = "" ) {
        this.runInProgress.value = runInProgress
        this.errorMessage.value = errorMessage
        dataList.value = listOf(
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
        dataListFiltered.value = dataList.value
    }
}