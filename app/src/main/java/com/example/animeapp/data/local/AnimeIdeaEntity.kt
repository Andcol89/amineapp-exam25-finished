package com.example.animeapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime_ideas")
data class AnimeIdeaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String
)
