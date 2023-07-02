package gb.com.model.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(WordEntity::class, FavoriteEntity::class),
    version = 1, exportSchema = false)
abstract class HistoryDataBase : RoomDatabase() {
    abstract fun historyDao(): WordDao
    abstract fun favoriteDao(): FavoriteDao
}