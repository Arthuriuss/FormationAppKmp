package com.mma.formationappkmp.di

import com.mma.formationappkmp.api.FakeWeatherApi
import com.mma.formationappkmp.api.KtorWeatherApi
import com.mma.formationappkmp.api.configurePlatformSsl
import com.mma.formationappkmp.domain.WeatherRepository
import com.mma.formationappkmp.presentation.ui.screens.MainViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.ContentType.Application.Json
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

//Si besoin du contexte, pour le passer en paramètre au lancement de Koin
fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(apiModule, viewModelModule)
    }
}

// Version pour iOS et Desktop
fun initKoin() = initKoin {}

//------------------------
//DECLARATION DES MODULES
//------------------------
val apiModule = module {
    //Création d'un singleton pour le client HTTP
    single {
        HttpClient {
            configurePlatformSsl()
            install(Logging) {
                //(import io.ktor.client.plugins.logging.Logger)
                logger = object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
                level = LogLevel.INFO  // TRACE, HEADERS, BODY, etc.
            }
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 5000
            }
            //engine { proxy = ProxyBuilder.http("monproxy:1234") }
        }
    }

    //Création d'un singleton pour les repository.
    //Get() injectera les objets déjà connus par koin, ici le HttpClient
    //single { PhotographerAPI(get()) }

    //Version avec injection automatique des objets connues
    single<WeatherRepository> { KtorWeatherApi(get()) }
}

val fakeApiModule = module {
    single<WeatherRepository> { FakeWeatherApi() }
}

//Version spécifique au ViewModel
val viewModelModule = module {

    //V1 : Si on veut ajouter manuellement certain paramètre
    //viewModel { MainViewModel(get(), Dispatchers.IO) }

    //V2 On déclare un Dispatchers à koin
    viewModelOf(::MainViewModel)
}