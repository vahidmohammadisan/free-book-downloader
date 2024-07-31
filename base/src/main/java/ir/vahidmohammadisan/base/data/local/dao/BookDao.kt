package ir.vahidmohammadisan.base.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ir.vahidmohammadisan.base.data.local.model.BookCached
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Query("SELECT * FROM BookCached")
    fun getBooks(): Flow<List<BookCached>>

    @Upsert
    suspend fun saveBooks(books: List<BookCached>)
}
