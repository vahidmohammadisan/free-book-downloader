package ir.vahidmohammadisan.base.presentation.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ir.vahidmohammadisan.base.presentation.BooksViewModel
import ir.vahidmohammadisan.base.presentation.contract.BooksEvent
import ir.vahidmohammadisan.base.presentation.contract.BooksIntent
import ir.vahidmohammadisan.base.presentation.contract.BooksUiState
import ir.vahidmohammadisan.core.utils.collectWithLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun BooksRoute(
    viewModel: BooksViewModel = hiltViewModel(),
) {
    HandleEvents(viewModel.event)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BooksScreen(
        uiState = uiState,
        onIntent = viewModel::acceptIntent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BooksScreen(
    uiState: BooksUiState,
    onIntent: (BooksIntent) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val pullToRefreshState = rememberPullToRefreshState()

    HandlePullToRefresh(
        pullState = pullToRefreshState,
        uiState = uiState,
        onIntent = onIntent,
    )

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .nestedScroll(pullToRefreshState.nestedScrollConnection),
        ) {
            if (uiState.books.isNotEmpty()) {
                BooksAvailableContent(
                    snackbarHostState = snackbarHostState,
                    uiState = uiState,
                    onBookClick = { onIntent(BooksIntent.BookClicked(it)) },
                )
            } else {
                BooksNotAvailableContent(
                    uiState = uiState,
                )
            }

            PullToRefreshContainer(
                state = pullToRefreshState,
                modifier = Modifier
                    .align(Alignment.TopCenter),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HandlePullToRefresh(
    pullState: PullToRefreshState,
    uiState: BooksUiState,
    onIntent: (BooksIntent) -> Unit,
) {
    if (pullState.isRefreshing) {
        LaunchedEffect(true) {
            /*  onIntent(RefreshBooks)*/
        }
    }

    if (uiState.isLoading.not()) {
        LaunchedEffect(true) {
            pullState.endRefresh()
        }
    }
}

@Composable
private fun HandleEvents(events: Flow<BooksEvent>) {
    val uriHandler = LocalUriHandler.current

    events.collectWithLifecycle {
        when (it) {
            is BooksEvent.OpenWebBrowserWithDetails -> {
                uriHandler.openUri(it.uri)
            }
        }
    }
}

@Composable
private fun BooksAvailableContent(
    snackbarHostState: SnackbarHostState,
    uiState: BooksUiState,
    onBookClick: (String) -> Unit,
) {
    if (uiState.isError) {
        val errorMessage =
            stringResource(ir.vahidmohammadisan.bookdownloader.core.R.string.app_name)

        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar(
                message = errorMessage,
            )
        }
    }

    BooksListContent(
        bookList = uiState.books,
        details = uiState.details,
        onBookClick = onBookClick,
    )
}

@Composable
private fun BooksNotAvailableContent(uiState: BooksUiState) {
    when {
        //  uiState.isLoading -> BooksLoadingPlaceholder()
        // uiState.isError -> BooksErrorContent()
    }
}
