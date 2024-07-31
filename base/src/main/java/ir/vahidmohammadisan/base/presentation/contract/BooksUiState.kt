package ir.vahidmohammadisan.base.presentation.contract

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import ir.vahidmohammadisan.base.domain.model.Book
import ir.vahidmohammadisan.base.domain.model.BookDetails
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class BooksUiState(
    val isLoading: Boolean = false,
    val books: List<Book> = emptyList(),
    val details: BookDetails = BookDetails.getEmpty(),
    val isError: Boolean = false,
) : Parcelable {

    sealed class PartialState {
        data object Loading : PartialState()

        data class Fetched(val list: List<Book>) : PartialState()

        data class DetailsFetched(val details: BookDetails) : PartialState()

        data class Error(val throwable: Throwable) : PartialState()
    }
}
