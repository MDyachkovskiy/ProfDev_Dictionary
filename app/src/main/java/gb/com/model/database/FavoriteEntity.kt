package gb.com.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorites",
    indices = arrayOf(Index(value = arrayOf("definition"), unique = true))
)
data class FavoriteEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val word: String = "",
    val phonetic: String? = "",
    val partOfSpeech: String = "",
    @ColumnInfo(name = "definition")
    val definition: String = ""
)