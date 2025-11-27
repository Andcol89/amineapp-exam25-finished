package com.example.animeapp.data.api

import com.example.animeapp.data.Info.APIAnimeById
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    //hente alle animer fra respone
    @GET("v4/anime")
    suspend fun getAllAnimes(): Response<APIAnimeList>

    // En Anime -> Id
    @GET("v4/anime/{id}")
    suspend fun getAnimeById(
        @Path("id") id: Int
    ): Response<APIAnimeById>



    //En Anime -> Navn
    //q er query-parameteren for (navn/tekst) - OBS må være q!
    @GET("v4/anime")
    suspend fun searchAnime(
        @Query("q") query: String
    ): Response<APIAnimeList>
}