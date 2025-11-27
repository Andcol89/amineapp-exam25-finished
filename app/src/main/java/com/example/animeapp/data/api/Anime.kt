package com.example.animeapp.data.api

import com.example.animeapp.data.api.AnimeImages
import com.google.gson.annotations.SerializedName

data class Anime(
    @SerializedName("mal_id")
    val id: Int,                // id
    val title: String,          // f.eks. "Cowboy Bebop"
    val images: AnimeImages,     //-> går til  (jpg:JpgImage) -> går til (image_url: String)

    // Ekstra info -------------------------
    // APIet garanterer ikke at alle feltene finner for hver anime.
    // Dermed må vi ha med ? for informasjonen kan være "null".

    val year: Int?,         //   år
    val status: String?,    //   Om serien er ferdig elle rikke.
    val score: Float?,      //   Hvor høy score den har fått.
    val rank: Int?,         //   Hvor på rankinglisten den ligger.

    val episodes: Int?,      //  Hvor mange episoder.
    val duration: String?,   //  F.eks "21 min per ep"
    val ageRating: String?,     //  Aldergrense

    val synopsis: String?,   // En beskrivelse av serien. Ofte veldig lang.

)