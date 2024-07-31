package ir.vahidmohammadisan.base.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookCached(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

)
