package gb.com.model.datasource.db

import gb.com.model.data.AppState
import gb.com.model.data.Definition
import gb.com.model.data.Meaning
import gb.com.model.data.WordDefinition
import gb.com.model.database.WordDao
import gb.com.model.database.WordEntity
import gb.com.model.datasource.DataSourceLocal

class RoomDataBaseImplementation(
    private val wordDao: WordDao
) : DataSourceLocal<List<WordDefinition>> {
    override suspend fun saveToDB(appState: AppState) {
        val listOfEntities = convertWordDefinitionSuccessToEntity(appState)
        wordDao.insertAll(listOfEntities)
    }

    override suspend fun getData(word: String): List<WordDefinition> {
        return mapHistoryEntityToSearchResult(word)
    }

    private suspend fun mapHistoryEntityToSearchResult(word: String): List<WordDefinition> {
        val wordEntities = if (word.isEmpty()){
            wordDao.all()
        } else {
            wordDao.getDataByWord(word)
        }

        val groupedByWordAndPhonetic = wordEntities.groupBy {
            Pair(it.word, it.phonetic)
        }

        return groupedByWordAndPhonetic.map { (wordPhoneticPair, entities) ->
            val meanings = entities.groupBy { it.partOfSpeech }
                .map { (partOfSpeech, entities) ->
                    Meaning(
                        partOfSpeech = partOfSpeech,
                        definitions = entities.map { Definition(it.definition) }
                    )
                }
            WordDefinition(
                word = wordPhoneticPair.first,
                phonetic = wordPhoneticPair.second,
                meanings = meanings
            )
        }
    }

    private fun convertWordDefinitionSuccessToEntity(
        appState: AppState
    ): List<WordEntity> {
        return when (appState) {
            is AppState.Success -> {
                val searchResult = appState.data as List<WordDefinition>
                searchResult.flatMap { wordDefinition ->
                    wordDefinition.meanings?.flatMap { meaning ->
                        meaning.definitions.map { definition ->
                            WordEntity(
                                word = wordDefinition.word,
                                phonetic = wordDefinition.phonetic,
                                partOfSpeech = meaning.partOfSpeech,
                                definition = definition.definition
                            )
                        }
                    } ?: emptyList()
                }
            }
            else -> emptyList()
        }
    }
}