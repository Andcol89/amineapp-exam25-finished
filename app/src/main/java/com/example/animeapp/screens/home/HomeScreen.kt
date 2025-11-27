package com.example.animeapp.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.animeapp.components.header.StyleLayoutDark
import com.example.animeapp.components.header.StyleLayoutLight



@Composable
fun HomeScreen(
    onAnimeClick: (Int) -> Unit,
    isDarkBackground: Boolean,
    animeVm: HomeScreenViewModel = viewModel()
) {
    val animes = animeVm.animes
    val isLoading = animeVm.isLoadingAnime
    val error = animeVm.error

    val title = "Anime series"

    //hvis mørk bakgrunn
    if (isDarkBackground) {
        StyleLayoutDark(
            title = title,
            modifier = Modifier.padding(16.dp)
        ){
            HomeContent(
                isLoading = isLoading,
                error = error,
                animes = animes,
                onAnimeClick = onAnimeClick
            )
        }
    } else { //hvis lys bakgrunn
        StyleLayoutLight(
            title = title,
            modifier = Modifier.padding(16.dp)) {
            HomeContent(
                isLoading = isLoading,
                error = error,
                animes = animes,
                onAnimeClick = onAnimeClick
            )
        }
    }
}

