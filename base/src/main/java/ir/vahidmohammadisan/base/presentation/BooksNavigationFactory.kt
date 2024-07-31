package ir.vahidmohammadisan.base.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ir.vahidmohammadisan.base.presentation.composable.BooksRoute
import ir.vahidmohammadisan.core.navigation.NavigationDestination
import ir.vahidmohammadisan.core.navigation.NavigationFactory
import javax.inject.Inject

class BooksNavigationFactory @Inject constructor() : NavigationFactory {

    override fun create(builder: NavGraphBuilder) {
        builder.composable(NavigationDestination.Books.route) {
            BooksRoute()
        }
    }
}
