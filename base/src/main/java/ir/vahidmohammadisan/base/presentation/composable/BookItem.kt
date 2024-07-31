package ir.vahidmohammadisan.base.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import ir.vahidmohammadisan.base.domain.model.Book

@Composable
fun BookItem(
    book: Book,
    modifier: Modifier = Modifier,
    onBookClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(
                //  vertical = dimensionResource(id = R.dimen.dimen_medium),
            )
            .clickable { onBookClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            // verticalArrangement = Arrangement.spacedBy(
            //   dimensionResource(id = R.dimen.dimen_small),
            //  ),
        ) {
            Text(
                text = book.title,
                //style = Typography.titleMedium,
            )

        }

        AsyncImage(
            model = book.cover,
            contentDescription = "",
            modifier = Modifier
                .weight(1f),
        )
    }
}
