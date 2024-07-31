package ir.vahidmohammadisan.base.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.vahidmohammadisan.base.data.remote.api.BookApi
import ir.vahidmohammadisan.base.data.remote.api.BookDetailsApi
import ir.vahidmohammadisan.base.data.remote.repository.BookRepositoryImpl
import ir.vahidmohammadisan.base.domain.repository.BookRepository
import ir.vahidmohammadisan.base.domain.usecase.GetBookDetailsUseCase
import ir.vahidmohammadisan.base.domain.usecase.GetBooksUseCase
import ir.vahidmohammadisan.base.domain.usecase.getBookDetails
import ir.vahidmohammadisan.base.domain.usecase.getBooks
import ir.vahidmohammadisan.core.network.BooksMainApi
import ir.vahidmohammadisan.core.network.BooksSecondApi
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object BookModule {

    @Provides
    @Singleton
    fun provideBookApi(
        @BooksMainApi retrofit: Retrofit,
    ): BookApi {
        return retrofit.create(BookApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBookSecondApi(
        @BooksSecondApi retrofit: Retrofit,
    ): BookDetailsApi {
        return retrofit.create(BookDetailsApi::class.java)
    }

    @Provides
    fun provideGetBooksUseCase(
        bookRepository: BookRepository,
    ): GetBooksUseCase {
        return GetBooksUseCase { keyword, page ->
            getBooks(bookRepository, keyword, page)
        }
    }

    @Provides
    fun provideGetBookDetailsUseCase(
        bookRepository: BookRepository,
    ): GetBookDetailsUseCase {
        return GetBookDetailsUseCase { md5 ->
            getBookDetails(bookRepository, md5)
        }
    }


    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {

        @Binds
        @Singleton
        fun bindBookRepository(impl: BookRepositoryImpl): BookRepository
    }
}
