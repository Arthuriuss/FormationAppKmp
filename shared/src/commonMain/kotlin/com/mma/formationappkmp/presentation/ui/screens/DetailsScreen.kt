package com.mma.formationappkmp.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_TYPE_NORMAL
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.mma.formationappkmp.domain.Main
import com.mma.formationappkmp.domain.Weather
import com.mma.formationappkmp.domain.WeatherEntity
import com.mma.formationappkmp.domain.Wind
import com.mma.formationappkmp.presentation.ui.theme.FormationKmpTheme
import formationappkmp.shared.generated.resources.Res
import formationappkmp.shared.generated.resources.back
import formationappkmp.shared.generated.resources.error
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable //id du WeatherEntity à afficher
fun DetailScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    data: WeatherEntity,
) {

    Column (
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainerHighest),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(space = 16.dp)
        ) {
            Text(
                text = data.name,
                color = MaterialTheme.colorScheme.primary
            )

            AsyncImage(
                model = data.weather[0].icon,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(48.dp),
                error = painterResource(Res.drawable.error),
                onError = {
                    //Log.d("test", "error (${data.weather[0].icon}) : ${it.result.throwable.message}")
                }
            )

            Text(
                text = data.getResume(),
            )
        }

        Button(
            onClick = onBack
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
                Text(text = stringResource(Res.string.back))
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL)
@Composable
fun DetailScreenPreview() {
    FormationKmpTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            DetailScreen(
                onBack = {},
                modifier = Modifier.padding(innerPadding),
                //jeu de donnée pour la Preview
                data = WeatherEntity(
                    id = 2,
                    name = "Toulouse",
                    main = Main(temp = 22.3),
                    weather = listOf(
                        Weather(description = "partiellement nuageux", icon = "https://picsum.photos/201")
                    ),
                    wind = Wind(speed = 3.2)
                )
            )
        }
    }
}