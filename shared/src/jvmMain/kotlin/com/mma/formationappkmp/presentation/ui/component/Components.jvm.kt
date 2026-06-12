package com.mma.formationappkmp.presentation.ui.component

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mma.formationappkmp.domain.WeatherEntity
import com.mma.formationappkmp.presentation.ui.screens.PictureRowItem
import kotlinx.coroutines.launch

@Composable
actual fun WeatherGallery(
    modifier: Modifier,
    list: List<WeatherEntity>,
    onOpenWeather: (Int) -> Unit
) {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyRow(
        modifier = modifier
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    coroutineScope.launch {
                        scrollState.scrollBy(-delta)
                    }
                }
            ),
        state = scrollState,
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
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