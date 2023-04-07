package cz.minarik.rickandmorty.common

import java.nio.charset.StandardCharsets

/**
 * Constants used in the app.
 *
 * todo move to appropriate modules, should not be app-wide
 */
@Suppress("UndocumentedPublicProperty")
object Constants {
    const val argCharacterId = "argCharacterId"
    const val argCharacterName = "argCharacterName"

    const val argEpisodeName = "argEpisodeName"
    const val argEpisodeId = "argEpisodeId"

    val UTF_8 = StandardCharsets.UTF_8.toString()
}
