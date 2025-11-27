package com.example.animeapp.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.animeapp.components.animes.AnimeList
import com.example.animeapp.data.api.Anime

//innhold
@Composable
 fun HomeContent(
    isLoading: Boolean,                //henter fra viewModel - om vi laster data nå
    error: String?,                   //henter fra viewModel - feilmelding hvis noe gikk galt
    animes: List<Anime>,             //henter fra viewModel - lista du skal vise
    onAnimeClick: (Int) -> Unit     //for animedetalj funksjon
) {
    Column(
        modifier = Modifier
            .padding(all = 10.dp)
    ) {

        if (isLoading) {
            Text(text = "Loading ..")
        } else if (error != null) {
            Text(text = "error: $error")
        } else {
            AnimeList(
                animes = animes,
                onAnimeClick = onAnimeClick
            )
        }
    }
}