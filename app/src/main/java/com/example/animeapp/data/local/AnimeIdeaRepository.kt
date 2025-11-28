package com.example.animeapp.data.local

class AnimeIdeaRepository(private val dao: AnimeIdeaDao) {

    // Henter alle ideer
    val ideas = dao.getAllIdeas()

    // Legger til en ny idé
    suspend fun addIdea(title: String, description: String) {
        dao.insert(AnimeIdeaEntity(title = title, description = description))
    }

    // Oppdaterer en idé
    suspend fun updateIdea(id: Int, title: String, desc: String) {
        dao.update(
            AnimeIdeaEntity(
                id = id,
                title = title,
                description = desc
            )
        )
    }

    // Sletter en idé
    suspend fun deleteIdea(idea: AnimeIdeaEntity) {
        dao.delete(idea)
    }

    // Kode som vi ikke bruker nå
    /* Henter én idé etter id
   // suspend fun getIdea(id: Int): AnimeIdeaEntity? = dao.getById(id)
*/
}
