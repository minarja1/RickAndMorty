package cz.minarik.alzatest.navigation

sealed class Screen(val route: String) {
    object HomePage : Screen("home_page")
    object CharacterDetail : Screen("character_detail")
    object EpisodeDetail : Screen("episode_detail")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
