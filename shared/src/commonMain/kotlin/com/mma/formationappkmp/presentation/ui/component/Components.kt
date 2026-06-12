package com.mma.formationappkmp.presentation.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mma.formationappkmp.domain.WeatherEntity

@Composable
expect fun WeatherGallery(modifier:Modifier = Modifier, list: List<WeatherEntity>, onOpenWeather: (Int)->Unit)