package com.example.animeapp.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
@Dao
interface AnimeIdeaDao {

    // Henter alle ideer fra databasen (live oppdatering).
    @Query("SELECT * FROM anime_ideas ORDER BY id DESC")
    fun getAllIdeas(): Flow<List<AnimeIdeaEntity>>

    // Lagrer en idé i databasen.
    // Hvis den finnes fra før, blir den byttet ut.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(idea: AnimeIdeaEntity)

    // Endrer en idé som allerede ligger i databasen.
    @Update
    suspend fun update(idea: AnimeIdeaEntity)

    // Sletter en idé.
    @Delete
    suspend fun delete(idea: AnimeIdeaEntity)

    // Henter én idé ut fra databasen ved å bruke id.
    // Returnerer null hvis den ikke finnes.
    @Query("SELECT * FROM anime_ideas WHERE id = :id")
    suspend fun getById(id: Int): AnimeIdeaEntity?
}