package gb.com.model.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface WordDao {
    @Query("SELECT * FROM history_word")
    suspend fun all(): List<WordEntity>

    @Query("SELECT * FROM history_word WHERE word LIKE :word")
    suspend fun getDataByWord(word: String): List <WordEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: WordEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(entities: List<WordEntity>)

    @Update
    suspend fun update(entity: WordEntity)

    @Delete
    suspend fun delete(entity: WordEntity)
}