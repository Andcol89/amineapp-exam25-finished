// NavRoutes.kt
package com.example.animeapp.navigation

import kotlinx.serialization.Serializable

sealed class NavRoutes {
    @Serializable
    data object Home : NavRoutes()

    @Serializable
    data object Search : NavRoutes()

    @Serializable
    data object Create : NavRoutes()

    @Serializable
    data object Exstra : NavRoutes()

    @Serializable
    data class AnimeDetails(val id: Int) : NavRoutes()

    @Serializable
    data class CreateCharacter(
        val title: String,
        val description: String
    ) : NavRoutes()
}
