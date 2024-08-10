package cz.minarik.rickandmorty.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cz.minarik.rickandmorty.common.Constants
import cz.minarik.rickandmorty.ui.screens.characters.detail.CharacterDetailScreen
import cz.minarik.rickandmorty.ui.screens.characters.detail.CharacterDetailScreenViewModel
import cz.minarik.rickandmorty.ui.screens.episodes.detail.EpisodeDetailScreen
import cz.minarik.rickandmorty.ui.screens.episodes.detail.EpisodeDetailScreenViewModel
import cz.minarik.rickandmorty.ui.screens.home.HomeScreen
import cz.minarik.rickandmorty.ui.screens.home.HomeScreenViewModel
import cz.minarik.rickandmorty.ui.theme.RaMTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.net.URLEncoder

@Composable
fun Navigation() {
    RaMTheme {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = Screen.HomePage.route) {
            composable(route = Screen.HomePage.route) {
                HomeScreen(
                    viewModel = koinViewModel<HomeScreenViewModel>(),
                    onCharacterDetailClicked = { id, name ->
                        navController.navigate(
                            Screen.CharacterDetail.withArgs(
                                id,
                                URLEncoder.encode(name ?: "", Constants.UTF_8)
                            )
                        )
                    },
                    onEpisodeDetailClicked = { id, name ->
                        navController.navigate(
                            Screen.EpisodeDetail.withArgs(
                                id,
                                URLEncoder.encode(name ?: "", Constants.UTF_8)
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
                    characterName = it.arguments?.getString(Constants.argCharacterName),
                    viewModel = koinViewModel<CharacterDetailScreenViewModel>(parameters = {
                        parametersOf(it.arguments?.getString(Constants.argCharacterId))
                    }),
                    onEpisodeDetailClicked = { id, name ->
                        navController.navigate(
                            Screen.EpisodeDetail.withArgs(
                                id,
                                URLEncoder.encode(name ?: "", Constants.UTF_8)
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
                    viewModel = koinViewModel<EpisodeDetailScreenViewModel>(parameters = {
                        parametersOf(it.arguments?.getString(Constants.argEpisodeId))
                    }),
                    episodeName = it.arguments?.getString(Constants.argEpisodeName),
                )
            }
        }
    }
}

