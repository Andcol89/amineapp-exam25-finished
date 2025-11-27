package com.example.animeapp.data.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIRepository {
    private val _httpClient = OkHttpClient.Builder()                // 1. Opprettelse av klient (privat)
        .addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(
                    HttpLoggingInterceptor.Level.BODY
                )
        ).build()

    private val _retrofit = Retrofit.Builder()                      // 2. Opprettelse av Retrofit-objekt (privat)
        .client(_httpClient)
        .baseUrl("https://api.jikan.moe/")
        .addConverterFactory(GsonConverterFactory.create()) // konvertering fra JSON til Kotlin-objekt og vice versa
        .build()

    private val _apiService = _retrofit.create(APIService::class.java)// 3. Initiering av apiService (privat)


    suspend fun getAllAnimes() : List<Anime> {                    // 4. Metodene for å gjøre HTTP-kall (åpent)
        //
        // Man skal ha try catch rundt kode som gjør kall til Web API eller Database
        try {
            val response=_apiService.getAllAnimes()

            if(response.isSuccessful){
                // body()? sjekker om body() som forventes skal være JSON fra Web APIet er tomt (null)
                return response.body()?.data ?: emptyList()     // ?: heter "Elvis operator" og sjekker om "data" fra API-et = null
            }else{
                return emptyList()
            }
        }
        catch (e: Exception){

            Log.d("getAllAnimes()",e.toString() )
            return emptyList()
        }
    }


    suspend fun getAnimeById(id: Int): Anime? {
        return try {
            val response = _apiService.getAnimeById(id)
            if (response.isSuccessful) {
                response.body()?.data
            } else null
        } catch (e: Exception) {
           Log.d("getAnimeById()", e.toString())
            null
        }
    }

//for å søke på navn/id
    suspend fun searchAnimeByName(name: String): Anime? {
        return try {
            val response = _apiService.searchAnime(name)

            if (response.isSuccessful) {
                response.body()?.data?.firstOrNull()
            } else {
                null
            }
        } catch (e: Exception) {
            Log.d("searchAnimeByName()", e.toString())
            null
        }
    }



}