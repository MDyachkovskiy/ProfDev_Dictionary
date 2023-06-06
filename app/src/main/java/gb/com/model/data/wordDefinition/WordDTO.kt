package gb.com.model.data.wordDefinition

data class WordDTO (
    val word: String,
    val phonetic: String,
    val partOfSpeech: String,
    val definition: Definition
    )