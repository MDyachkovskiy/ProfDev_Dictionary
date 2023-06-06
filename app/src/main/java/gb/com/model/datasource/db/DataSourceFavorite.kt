package gb.com.model.datasource.db

import gb.com.model.data.wordDefinition.WordDTO


interface DataSourceFavorite {
    suspend fun saveFavoriteToDB(word: WordDTO)
    suspend fun getAllFavorites(): List<WordDTO>
}