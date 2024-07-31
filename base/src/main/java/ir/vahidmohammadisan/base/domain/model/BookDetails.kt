package ir.vahidmohammadisan.base.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class BookDetails(
    val downloadLink: String,
    val fileName: String,
    val description: String,
    val author: String,
    val isbn: String
) : Parcelable {

    companion object {
        fun getEmpty() = BookDetails(
            downloadLink = "",
            fileName = "",
            description = "",
            author = "",
            isbn = "",
        )
    }

}