package gb.com.model.datasource.db

import gb.com.model.data.wordDefinition.Definition
import gb.com.model.data.wordDefinition.WordDTO
import gb.com.model.database.FavoriteDao
import gb.com.model.database.FavoriteEntity

class FavoriteDataBaseImplementation(
    private val favoriteDao: FavoriteDao
) : DataSourceFavorite {

    override suspend fun saveFavoriteToDB(word: WordDTO) {
        word.toFavoriteEntity().also {
            favoriteDao.insert(it)
        }
    }

    override suspend fun getAllFavorites(): List<WordDTO> {
        val favorites = favoriteDao.all()
        val favoritesDTO = favorites.toWordDTOList()
        return favoritesDTO
    }

    private fun WordDTO.toFavoriteEntity(): FavoriteEntity {
        return FavoriteEntity(
            word = this.word,
            phonetic = this.phonetic,
            partOfSpeech = this.partOfSpeech,
            definition = this.definition.definition
        )
    }

    private fun FavoriteEntity.toWordDTO(): WordDTO {
        return WordDTO(
            word = this.word,
            phonetic = this.phonetic ?: "",
            partOfSpeech = this.partOfSpeech,
            definition = Definition(this.definition)
        )
    }

    fun List<FavoriteEntity>.toWordDTOList(): List<WordDTO> {
        return this.map {entity ->
            entity.toWordDTO()
        }
    }
}