package com.example.animeapp.data.local.exstra

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteAnimeDao {

    @Query("SELECT * FROM favorite_anime")
    suspend fun getAll(): List<FavoriteAnimeEntity>

    @Query("SELECT * FROM favorite_anime WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): FavoriteAnimeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: FavoriteAnimeEntity)

    @Query("DELETE FROM favorite_anime WHERE id = :id")
    suspend fun deleteById(id: Int)
}