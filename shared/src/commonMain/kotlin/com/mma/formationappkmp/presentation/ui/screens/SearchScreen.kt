package com.mma.formationappkmp.presentation.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.mma.formationappkmp.di.apiModule
import com.mma.formationappkmp.di.viewModelModule
import com.mma.formationappkmp.presentation.ui.component.ErrorMessage
import com.mma.formationappkmp.presentation.ui.component.SearchBar
import com.mma.formationappkmp.presentation.ui.component.WeatherGallery
import com.mma.formationappkmp.presentation.ui.theme.FormationKmpTheme
import formationappkmp.shared.generated.resources.Res
import formationappkmp.shared.generated.resources.clear_filter
import formationappkmp.shared.generated.resources.error
import formationappkmp.shared.generated.resources.load_data
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.KoinApplicationPreview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    onOpenWeather: (Int) -> Unit = {}
) {
    val dataList by mainViewModel.dataList.collectAsStateWithLifecycle()
    val searchQuery by mainViewModel.searchQuery.collectAsStateWithLifecycle()
    val runInProgress by mainViewModel.runInProgress.collectAsStateWithLifecycle()
    val error by mainViewModel.error.collectAsStateWithLifecycle()

    Box(
        modifier = modifier.fillMaxSize().imePadding(),
    ) {
        if (runInProgress) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        AnimatedVisibility(
            visible = error,
            modifier = Modifier.align(Alignment.Center)
        ) {
            ErrorMessage(
                text = "Erreur de chargement des données",
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(space = 8.dp)
        ) {
            SearchBar(
                value = searchQuery,
                onValueChange = {
                    mainViewModel.onSearch(query = it)
                },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )

            WeatherGallery(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                list = dataList,
                onOpenWeather = onOpenWeather
            )

            /*LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(space = 8.dp)
            ) {
                items(items = dataList) { data ->
                    PictureRowItem(
                        id = data.id,
                        text = data.name,
                        color = Color.Blue,
                        description = data.getResume(),
                        icon = data.weather.firstOrNull()?.icon,
                        onClick = {
                            it?.let { openWeatherId -> onOpenWeather(openWeatherId) }
                        }
                    )
                }
            }*/

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
                ) {
                    Button(
                        onClick = {
                            mainViewModel.onClearSearch()
                        }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = null
                            )
                            Text(text = stringResource(Res.string.clear_filter))
                        }
                    }
                    Button(
                        onClick = {
                            mainViewModel.loadData()
                        }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Upload,
                                contentDescription = null
                            )
                            Text(text = stringResource(Res.string.load_data))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PictureRowItem(
    id: Int?,
    text: String,
    modifier: Modifier = Modifier,
    description: String? = null,
    icon: String? = null,
    color: Color = Color.Black,
    onClick: (Int?) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainerHighest)
            .clickable { onClick(id) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {
        AsyncImage(
            model = icon,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(48.dp),
            error = painterResource(Res.drawable.error),
            onError = {
                //Log.d("test", "error ($icon) : ${it.result.throwable.message}")
            }
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = text,
                modifier = Modifier.fillMaxWidth(),
                color = color
            )
            description?.let {
                Text(
                    text = if(expanded) it else "${it.take(38)}...",
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}


@Preview
@Composable
fun SearchScreenDataPreview() {
    //Si besoin du contexte
    KoinApplicationPreview(application = {
        //androidContext(context) uniquement si coté Android avec Context
        modules(viewModelModule, apiModule)
    }) {

        //Il faut remplacer NomVotreAppliTheme par le thème de votre application
        //Utilisé par exemple dans MainActivity.kt sous setContent {...}
        FormationKmpTheme {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                val mainViewModel: MainViewModel = koinViewModel<MainViewModel>()
                mainViewModel.loadFakeData()
                SearchScreen(
                    modifier = Modifier.padding(innerPadding),
                    mainViewModel = mainViewModel
                )
            }
        }
    }
}