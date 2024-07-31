package ir.vahidmohammadisan.base.presentation.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import ir.vahidmohammadisan.base.domain.model.Book
import ir.vahidmohammadisan.base.domain.model.BookDetails

const val BOOK_DIVIDER_TEST_TAG = "bookDividerTestTag"

@Composable
fun BooksListContent(
    bookList: List<Book>,
    details: BookDetails,
    modifier: Modifier = Modifier,
    onBookClick: (String) -> Unit,
) {

    LazyColumn(
        modifier = modifier
            .padding(
                //    horizontal = dimensionResource(id = R.dimen.dimen_medium),
            ),
    ) {
        itemsIndexed(
            items = bookList,
            key = { _, book -> book.id },
        ) { index, item ->
            BookItem(
                book = item,
                onBookClick = { onBookClick(item.md5) },
            )

            if (index < bookList.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier.testTag(BOOK_DIVIDER_TEST_TAG),
                )
            }
        }
    }
}
