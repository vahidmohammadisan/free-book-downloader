package ir.vahidmohammadisan.base.domain.usecase

import ir.vahidmohammadisan.base.domain.model.Book
import ir.vahidmohammadisan.base.domain.repository.BookRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import java.io.IOException


private const val RETRY_TIME_IN_MILLIS = 15_000L

fun interface GetBooksUseCase : (String, Int) -> Flow<Result<List<Book>>>

fun getBooks(
    bookRepository: BookRepository,
    keyword: String,
    page: Int
): Flow<Result<List<Book>>> =
    bookRepository.getBooks(keyword = keyword, page = page)
        .map {
            Result.success(it)
        }
        .retryWhen { cause, _ ->
            if (cause is IOException) {
                emit(Result.failure(cause))

                delay(RETRY_TIME_IN_MILLIS)
                true
            } else {
                false
            }
        }
        .catch { // for other than IOException but it will stop collecting Flow
            emit(Result.failure(it)) // also catch does re-throw CancellationException
        }