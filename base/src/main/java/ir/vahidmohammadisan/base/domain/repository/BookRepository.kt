package ir.vahidmohammadisan.base.domain.repository

import ir.vahidmohammadisan.base.domain.model.Book
import ir.vahidmohammadisan.base.domain.model.BookDetails
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    fun getBooks(keyword: String, page: Int): Flow<List<Book>>
    fun getBookDetails(md5: String): Flow<BookDetails>
}