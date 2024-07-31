package ir.vahidmohammadisan.core.navigation

sealed class NavigationDestination(
    val route: String,
) {
    data object Books : NavigationDestination("booksDestination")
    data object Back : NavigationDestination("navigationBack")
}
