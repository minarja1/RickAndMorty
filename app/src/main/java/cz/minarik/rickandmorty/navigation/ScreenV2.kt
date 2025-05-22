package cz.minarik.rickandmorty.navigation

/**
 * Sealed class for all screens of the app.
 */
sealed class ScreenV2 {
    /**
     * Home screen.
     */
    data object HomePage : ScreenV2()

    /**
     * Character detail screen,..
     */
    data class CharacterDetail(
        val imageUrl: String,
        val name: String,
        val id: String,
    ) : ScreenV2()

    /**
     * Episode detail screen.
     */
    data class EpisodeDetail(
        val id: String,
    ) : ScreenV2()
}
