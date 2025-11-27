package com.example.animeapp.data.local.exstra

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import com.example.animeapp.data.api.Anime
import com.example.animeapp.data.local.getAnimeIdeaDatabase   // ⬅️ viktig

object FavoritesState {

    // Entity lagring
    val favorites = mutableStateListOf<FavoriteAnimeEntity>()

    private var isLoaded = false

    // Kalles én gang når appen/skjermen starter for å hente fra Room
    suspend fun loadFromDb(context: Context) {
        if (isLoaded) return

        val dao = getAnimeIdeaDatabase(context).favoriteAnimeDao()
        val entities = dao.getAll()

        favorites.clear()
        favorites.addAll(entities)

        isLoaded = true
    }

    // Toggle favorit-status for en Anime
    suspend fun toggleFavorite(context: Context, anime: Anime) {
        val dao = getAnimeIdeaDatabase(context).favoriteAnimeDao()
        val existing = dao.getById(anime.id)

        if (existing != null) {
            // Fjern fra Room
            dao.deleteById(anime.id)
            favorites.removeAll { it.id == anime.id }
        } else {
            // Legg til i Room
            val entity = FavoriteAnimeEntity(
                id = anime.id,
                title = anime.title,
                imageUrl = anime.images.jpg.image
            )
            dao.insert(entity)
            favorites.add(entity)
        }
    }

    fun isFavorite(id: Int): Boolean {
        return favorites.any { it.id == id }
    }
}
