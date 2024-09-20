package cz.minarik.rickandmorty.navigation

/**
 * Sealed class for all screens of the app.
 *
 * @property route Route of the screen.
 */
sealed class Screen(val route: String) {
    /**
     * Home screen.
     */
    data object HomePage : Screen("home_page")

    /**
     * Characters screen.
     */
    data object CharacterDetail : Screen("character_detail")

    /**
     * Episodes screen.
     */
    data object EpisodeDetail : Screen("episode_detail")

    /**
     * Builds route with arguments.
     */
    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
