package com.example.animeapp.screens.animedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.data.api.APIRepository
import com.example.animeapp.data.api.Anime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnimeDetailsViewModel : ViewModel() {

    private val _anime = MutableStateFlow<Anime?>(null)
    val anime = _anime.asStateFlow()

    var error: String? = null
        private set

    var isLoading = false
        private set


    fun load(id: Int) = viewModelScope.launch {

        try {
            isLoading = true
            error = null
            _anime.value = APIRepository.getAnimeById(id)           // Viktig. Her henter vi en anime fra APiet ut ifra ID.
            if (_anime.value == null) error = "Unfortunately, I couldn’t find any anime."
        } catch (e: Exception) {
            error = e.message
        } finally {
            isLoading = false
        }



    }
}