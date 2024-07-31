package ir.vahidmohammadisan.base.presentation

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.vahidmohammadisan.base.domain.usecase.GetBookDetailsUseCase
import ir.vahidmohammadisan.base.domain.usecase.GetBooksUseCase
import ir.vahidmohammadisan.base.presentation.contract.BooksEvent
import ir.vahidmohammadisan.base.presentation.contract.BooksIntent
import ir.vahidmohammadisan.base.presentation.contract.BooksUiState
import ir.vahidmohammadisan.core.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val getBooksUseCase: GetBooksUseCase,
    private val getBookDetailsUseCase: GetBookDetailsUseCase,
    private val context: Context,
    savedStateHandle: SavedStateHandle,
    booksInitialState: BooksUiState,
) : BaseViewModel<BooksUiState, BooksUiState.PartialState, BooksEvent, BooksIntent>(
    savedStateHandle,
    booksInitialState,
) {
    init {
        observeBooks()
    }

    override fun mapIntents(intent: BooksIntent): Flow<BooksUiState.PartialState> = when (intent) {
        is BooksIntent.BookClicked -> bookClicked(intent.uri)
    }

    override fun reduceUiState(
        previousState: BooksUiState,
        partialState: BooksUiState.PartialState,
    ): BooksUiState = when (partialState) {
        is BooksUiState.PartialState.Loading -> previousState.copy(
            isLoading = true,
            isError = false,
        )

        is BooksUiState.PartialState.Fetched -> previousState.copy(
            isLoading = false,
            books = partialState.list,
            isError = false,
        )

        is BooksUiState.PartialState.DetailsFetched -> previousState.copy(
            isLoading = false,
            details = partialState.details,
            isError = false,
        )

        is BooksUiState.PartialState.Error -> previousState.copy(
            isLoading = false,
            isError = true,
        )
    }

    private fun observeBooks() = acceptChanges(
        getBooksUseCase("android", 1)
            .map { result ->
                result.fold(
                    onSuccess = { bookList ->
                        BooksUiState.PartialState.Fetched(
                            bookList
                        )
                    },
                    onFailure = {
                        BooksUiState.PartialState.Error(
                            it
                        )
                    },
                )
            }
            .onStart {
                emit(BooksUiState.PartialState.Loading)
            },
    )

    private fun bookClicked(md5: String): Flow<BooksUiState.PartialState> = flow {
        getBookDetailsUseCase(md5).collect { result ->
            result.onSuccess { bookDetails ->
                Log.w("bbbbbbbbbb", "." + bookDetails.fileName)
                downloadFile(bookDetails.downloadLink, bookDetails.fileName)
                emit(
                    BooksUiState.PartialState.DetailsFetched(
                        bookDetails
                    )
                )
            }.onFailure { exception ->
                emit(
                    BooksUiState.PartialState.Error(
                        exception
                    )
                )
            }
        }
    }

    private fun downloadFile(url: String, fileName: String) {
        viewModelScope.launch {
            val request = DownloadManager.Request(Uri.parse(url))
                .setTitle(fileName)
                .setDescription("Downloading $fileName")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

            val downloadManager =
                context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            downloadManager.enqueue(request)
        }
    }

}