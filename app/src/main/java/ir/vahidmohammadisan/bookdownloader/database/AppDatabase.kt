package ir.vahidmohammadisan.bookdownloader.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.vahidmohammadisan.base.data.local.dao.BookDao
import ir.vahidmohammadisan.base.data.local.model.BookCached


private const val DATABASE_VERSION = 1

@Database(
    entities = [BookCached::class],
    version = DATABASE_VERSION,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}
