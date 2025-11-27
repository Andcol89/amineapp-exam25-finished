package com.example.animeapp.screens.createanime

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.data.local.AnimeIdeaEntity
import com.example.animeapp.data.local.AnimeIdeaRepository
import com.example.animeapp.data.local.getAnimeIdeaDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CreateViewModel(application: Application) : AndroidViewModel(application) {

    // Repository for database-håndtering
    private val repo: AnimeIdeaRepository

    // Liste av ideer (fra Room)
    private val _ideas = MutableStateFlow<List<AnimeIdeaEntity>>(emptyList())
    val ideas: StateFlow<List<AnimeIdeaEntity>> = _ideas

    // Siste lagrede karakter (brukt av CreateScreen)
    private val _lastCharacter = MutableStateFlow<AnimeCharacter?>(null)
    val lastCharacter: StateFlow<AnimeCharacter?> = _lastCharacter

    // Form-state for CreateScreen (overlever navigasjon)
    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description

    // Om vi skal vise lagrede animes / karakter
    private val _showSaved = MutableStateFlow(false)
    val showSaved: StateFlow<Boolean> = _showSaved

    // NYTT: hvilken idé som er valgt for redigering
    private val _selectedIdea = MutableStateFlow<AnimeIdeaEntity?>(null)
    val selectedIdea: StateFlow<AnimeIdeaEntity?> = _selectedIdea

    init {
        // Bruker top-level funksjonen, ikke companion object
        val db = getAnimeIdeaDatabase(application)

        // Oppretter repository
        repo = AnimeIdeaRepository(db.ideaDao())

        // Lytter på ideer og oppdaterer UI
        viewModelScope.launch {
            repo.ideas.collect { list ->
                _ideas.value = list
            }
        }
    }

    // Oppdater tekstfeltene
    fun setTitle(newTitle: String) {
        _title.value = newTitle
    }

    fun setDescription(newDescription: String) {
        _description.value = newDescription
    }

    fun clearCurrentIdeaInputs() {
        _title.value = ""
        _description.value = ""
    }

    // Styrer om vi viser lagrede animes
    fun setShowSaved(show: Boolean) {
        _showSaved.value = show
    }

    // Velger en idé for redigering
    fun selectIdea(idea: AnimeIdeaEntity) {
        _selectedIdea.value = idea
    }

    // Nullstiller valgt idé
    fun clearSelectedIdea() {
        _selectedIdea.value = null
    }

    // Kalles fra CreateCharacterScreen når du lagrer en karakter
    fun saveCharacter(character: AnimeCharacter) {
        _lastCharacter.value = character
    }

    // Legg til idé
    fun addIdea(title: String, description: String) = viewModelScope.launch {
        repo.addIdea(title, description)
    }

    // Oppdater idé
    fun updateIdea(id: Int, title: String, description: String) = viewModelScope.launch {
        repo.updateIdea(id, title, description)
    }

    // Slett idé
    fun deleteIdea(idea: AnimeIdeaEntity) = viewModelScope.launch {
        repo.deleteIdea(idea)
    }
}
