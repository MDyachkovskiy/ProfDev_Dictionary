package gb.com.model.repository

import gb.com.model.data.wordDefinition.WordDTO
import gb.com.model.datasource.db.DataSourceFavorite

class RepositoryImplementationFavorite(
    private val favoriteDataSource: DataSourceFavorite
) : RepositoryFavorites {

    override suspend fun saveFavoriteToDB(wordDTO: WordDTO) {
        favoriteDataSource.saveFavoriteToDB(wordDTO)
    }

    override suspend fun getAllFavorites(): List<WordDTO> {
        return favoriteDataSource.getAllFavorites()
    }
}