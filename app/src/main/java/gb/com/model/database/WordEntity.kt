package gb.com.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "history_word",
    indices = arrayOf(Index(value = arrayOf("definition"), unique = true))
)
data class WordEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var word: String = "",
    var phonetic: String? = "",
    var partOfSpeech: String = "",
    @ColumnInfo(name = "definition")
    var definition: String = ""
)