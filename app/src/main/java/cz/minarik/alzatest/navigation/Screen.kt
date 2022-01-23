package cz.minarik.alzatest.navigation

sealed class Screen(val route: String) {
    object HomePage : Screen("home_page")
    object ProductList : Screen("product_list")
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
