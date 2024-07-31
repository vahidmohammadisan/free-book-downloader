package ir.vahidmohammadisan.base.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Book(
    val id: Int,
    val title: String,
    val md5: String,
    val cover: String,
    val page: Int,
    val date: Int,
    val language: String,
    val type: String,
    val fileSize: Int,
    val publisher: String,
) : Parcelable {

    companion object {
        fun getEmpty() = Book(
            id = 0,
            title = "",
            md5 = "",
            cover = "",
            page = 0,
            date = 0,
            language = "",
            type = "",
            fileSize = 0,
            publisher = "",
        )
    }

}