package com.mma.formationappkmp

import com.mma.formationappkmp.api.KtorWeatherApi
import com.mma.formationappkmp.di.apiModule
import com.mma.formationappkmp.di.fakeApiModule
import com.mma.formationappkmp.di.viewModelModule
import com.mma.formationappkmp.domain.WeatherRepository
import com.mma.formationappkmp.presentation.ui.screens.MainViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.seconds

class MainViewModelTest {
    @Test
    fun testAPIWork() = runTest(timeout = 10.seconds) {

        val koin = startKoin {
            modules(apiModule, viewModelModule)
        }.koin

        val viewModel = koin.get<MainViewModel>()
        viewModel.onSearch(query = "Montpellier")

        assertFalse { viewModel.runInProgress.first { !it } }
        viewModel.loadData()

        assertTrue(viewModel.dataList.first().isNotEmpty())
    }

    @Test
    fun testFakeAPIWork() = runTest(timeout = 10.seconds) {

        val koin = startKoin {
            modules(fakeApiModule, viewModelModule)
        }.koin

        val weatherRepository = koin.get<WeatherRepository>()
        val viewModel = koin.get<MainViewModel>()
        viewModel.loadData()

        val response = weatherRepository.loadWeather(city = "Paris")
        assertTrue(response.list.isNotEmpty())
        assertTrue(viewModel.dataList.first().isNotEmpty())
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }
}