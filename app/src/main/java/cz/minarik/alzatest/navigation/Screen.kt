package cz.minarik.alzatest.navigation

sealed class Screen(val route: String) {
    object HomePage : Screen("home_page")
    object ProductDetail : Screen("product_detail")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
