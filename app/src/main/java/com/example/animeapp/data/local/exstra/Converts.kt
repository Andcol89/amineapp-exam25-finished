package com.example.animeapp.data.local.exstra

import androidx.room.TypeConverter
import com.example.animeapp.data.api.Anime
import com.google.gson.Gson

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun animeToJson(value: Anime): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonToAnime(value: String): Anime {
        return gson.fromJson(value, Anime::class.java)
    }
}