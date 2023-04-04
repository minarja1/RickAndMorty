package cz.minarik.alzatest.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cz.minarik.alzatest.common.Constants
import cz.minarik.alzatest.ui.screens.characters.detail.CharacterDetailScreen
import cz.minarik.alzatest.ui.screens.episodes.detail.EpisodeDetailScreen
import cz.minarik.alzatest.ui.screens.home.HomeScreen
import java.net.URLEncoder

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.HomePage.route) {
        composable(route = Screen.HomePage.route) {
            HomeScreen(
                onCharacterDetailClicked = { character ->
                    navController.navigate(
                        Screen.CharacterDetail.withArgs(
                            character.id,
                            URLEncoder.encode(character.name ?: "", Constants.UTF_8)
                        )
                    )
                },
                onEpisodeDetailClicked = { episode ->
                    navController.navigate(
                        Screen.EpisodeDetail.withArgs(
                            episode.id,
                            URLEncoder.encode(episode.name ?: "", Constants.UTF_8)
                        )
                    )
                }
            )
        }

        composable(
            route = Screen.CharacterDetail.route + "/{${Constants.argCharacterId}}/{${Constants.argCharacterName}}",
            arguments = listOf(
                navArgument(Constants.argCharacterId) {
                    type = NavType.StringType
                },
                navArgument(Constants.argCharacterName) {
                    type = NavType.StringType
                },
            )
        ) {
            CharacterDetailScreen(
                onBackClicked = navController::navigateUp,
                characterId = it.arguments?.getString(Constants.argCharacterId),
                characterName = it.arguments?.getString(Constants.argCharacterName),
                onEpisodeDetailClicked = { episode ->
                    navController.navigate(
                        Screen.EpisodeDetail.withArgs(
                            episode.id,
                            URLEncoder.encode(episode.name ?: "", Constants.UTF_8)
                        )
                    )
                }
            )
        }

        composable(
            route = Screen.EpisodeDetail.route + "/{${Constants.argEpisodeId}}/{${Constants.argEpisodeName}}",
            arguments = listOf(
                navArgument(Constants.argEpisodeId) {
                    type = NavType.StringType
                },
                navArgument(Constants.argEpisodeName) {
                    type = NavType.StringType
                },
            )
        ) {
            EpisodeDetailScreen(
                onBackClicked = navController::navigateUp,
                episodeId = it.arguments?.getString(Constants.argEpisodeId),
                episodeName = it.arguments?.getString(Constants.argEpisodeName),
            )
        }
    }
}

