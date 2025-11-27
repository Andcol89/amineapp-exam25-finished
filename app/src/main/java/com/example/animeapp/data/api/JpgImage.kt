package com.example.animeapp.data.api

import com.google.gson.annotations.SerializedName

data class JpgImage(
    @SerializedName("image_url")
    val image: String
)