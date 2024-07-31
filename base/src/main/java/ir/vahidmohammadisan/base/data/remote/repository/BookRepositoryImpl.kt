package ir.vahidmohammadisan.base.data.remote.repository

import ir.vahidmohammadisan.base.data.mapper.toBookDetails
import ir.vahidmohammadisan.base.data.mapper.toBooks
import ir.vahidmohammadisan.base.data.remote.api.BookApi
import ir.vahidmohammadisan.base.data.remote.api.BookDetailsApi
import ir.vahidmohammadisan.base.domain.model.Book
import ir.vahidmohammadisan.base.domain.model.BookDetails
import ir.vahidmohammadisan.base.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookApi: BookApi,
    private val bookDetailsApi: BookDetailsApi,
) : BookRepository {

    override fun getBooks(keyword: String, page: Int): Flow<List<Book>> = flow {
        val response = bookApi.getBooks(keyword, page)
        if (response.isSuccessful) {
            val htmlBody = response.body()?.string()
            if (!htmlBody.isNullOrEmpty()) {
                val books = htmlBody.toBooks()
                emit(books)
            } else {
                emit(emptyList())
            }
        } else {
            emit(emptyList())
        }
    }

    override fun getBookDetails(md5: String): Flow<BookDetails> = flow {
        val response = bookDetailsApi.getBookDetails(md5)
        if (response.isSuccessful) {
            val htmlBody = response.body()?.string()
            if (!htmlBody.isNullOrEmpty()) {
                val books = htmlBody.toBookDetails()
                emit(books)
            } else {
                emit(BookDetails.getEmpty())
            }
        } else {
            emit(BookDetails.getEmpty())
        }
    }

}
