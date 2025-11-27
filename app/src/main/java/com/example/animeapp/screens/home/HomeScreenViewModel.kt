package com.example.animeapp.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.data.api.APIRepository
import com.example.animeapp.data.api.Anime
import kotlinx.coroutines.launch

class HomeScreenViewModel : ViewModel() {

    //private val repo = APIRepository

    var animes by mutableStateOf<List<Anime>>(emptyList())
    private set

    var isLoadingAnime by mutableStateOf(false)
    private set

    var error by mutableStateOf<String?>(null)

    init {loadAll()}


fun loadAll() = viewModelScope.launch {

    try{
        isLoadingAnime = true
        error = null
        animes = APIRepository.getAllAnimes() //viktig del. Her henter vi listen
    } catch (e: Exception) {
        error = e.message
    } finally { isLoadingAnime = false }

    }
}