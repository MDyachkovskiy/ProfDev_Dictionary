package gb.com.model.repository

import gb.com.model.data.wordDefinition.WordDTO

interface RepositoryFavorites {
    suspend fun saveFavoriteToDB(wordDTO: WordDTO)
    suspend fun getAllFavorites(): List<WordDTO>
}