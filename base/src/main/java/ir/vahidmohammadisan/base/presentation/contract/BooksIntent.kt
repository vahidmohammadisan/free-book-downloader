package ir.vahidmohammadisan.base.presentation.contract

sealed class BooksIntent {
    data class BookClicked(val uri: String) : BooksIntent()
}
