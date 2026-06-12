package com.mma.formationappkmp.presentation.ui.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mma.formationappkmp.domain.WeatherEntity
import com.mma.formationappkmp.presentation.ui.screens.PictureRowItem

@Composable
actual fun WeatherGallery(
    modifier: Modifier,
    list: List<WeatherEntity>,
    onOpenWeather: (Int) -> Unit
) {
    LazyRow(
        modifier = modifier
    ) {
        items(list) { data ->
            PictureRowItem(
                id = data.id,
                text = data.name,
                color = Color.Blue,
                description = data.getResume(),
                icon = data.weather.firstOrNull()?.icon,
                onClick = {
                    it?.let { onOpenWeather(it) }
                }
            )
        }
    }
}