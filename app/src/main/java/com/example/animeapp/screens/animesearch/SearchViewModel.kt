package com.example.animeapp.screens.animesearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.data.api.APIRepository
import com.example.animeapp.data.api.Anime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _nyVerdi = MutableStateFlow<Anime?>(value = null)
    val offentligVerdi = _nyVerdi.asStateFlow()

    // søk ID eller navn
    fun search(query: String) {
        viewModelScope.launch {
            val id = query.toIntOrNull()

            //bruker søker på id
            _nyVerdi.value = if (id != null) {
                //hent id
                APIRepository.getAnimeById(id)
            } else {
                // hvis ikke - søk på navn
                APIRepository.searchAnimeByName(query)
            }
        }
    }

    /*
    fun setAnimeById(id: Int) {
        viewModelScope.launch {
            _nyVerdi.value = APIRepository.getAnimeById(id)
        }
    }*/
}