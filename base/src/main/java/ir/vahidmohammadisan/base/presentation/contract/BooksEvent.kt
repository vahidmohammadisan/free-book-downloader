package ir.vahidmohammadisan.base.presentation.contract

sealed class BooksEvent {
    data class OpenWebBrowserWithDetails(val uri: String) : BooksEvent()
}
