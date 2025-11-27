package com.example.animeapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.animeapp.data.local.exstra.FavoriteAnimeDao
import com.example.animeapp.data.local.exstra.FavoriteAnimeEntity

// Holder på database-instansen
private var instance: AnimeIdeaDatabase? = null

// Gir databasen (lager den hvis den ikke finnes)
fun getAnimeIdeaDatabase(context: Context): AnimeIdeaDatabase {
    if (instance == null) {
        instance = Room.databaseBuilder(
            context.applicationContext,
            AnimeIdeaDatabase::class.java,
            "anime_ideas_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    return instance!!
}

@Database(
    entities = [
        AnimeIdeaEntity::class,
        FavoriteAnimeEntity::class,
    ],
    version = 3,
    exportSchema = false
)
abstract class AnimeIdeaDatabase : RoomDatabase() {

    // DAO for anime-ideer
    abstract fun ideaDao(): AnimeIdeaDao

    // DAO for favoritt-anime
    abstract fun favoriteAnimeDao(): FavoriteAnimeDao
}
