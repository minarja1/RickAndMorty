package cz.minarik.rickandmorty.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
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

typealias CharacterId = String
typealias CharacterImageUrl = String
typealias CharacterName = String

// TODO refactor to typed navigation
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Navigation() {
    RaMTheme {
        SharedTransitionLayout {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = Screen.HomePage.route) {
                composable(route = Screen.HomePage.route) {
                    HomeScreen(
                        viewModel = koinViewModel<HomeScreenViewModel>(),
                        onCharacterDetailClicked = { id, imageUrl, name ->
                            navController.navigate(
                                Screen.CharacterDetail.withArgs(
                                    id,
                                    URLEncoder.encode(imageUrl ?: "", Constants.UTF_8),
                                    name ?: ""
                                )
                            )
                        },
                        onEpisodeDetailClicked = { id ->
                            navController.navigate(
                                Screen.EpisodeDetail.withArgs(id)
                            )
                        },
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedContentScope = this@composable
                    )
                }

                composable(
                    route = Screen.CharacterDetail.route + "/{${NavigationConstants.argCharacterId}}/{${NavigationConstants.argCharacterImageUrl}}/{${NavigationConstants.argCharacterName}}",
                    arguments = listOf(
                        navArgument(NavigationConstants.argCharacterId) {
                            type = NavType.StringType
                        },
                        navArgument(NavigationConstants.argCharacterImageUrl) {
                            type = NavType.StringType
                        },
                        navArgument(NavigationConstants.argCharacterName) {
                            type = NavType.StringType
                        },
                    )
                ) {
                    CharacterDetailScreen(
                        viewModel = koinViewModel<CharacterDetailScreenViewModel>(parameters = {
                            parametersOf(it.arguments?.getString(NavigationConstants.argCharacterId))
                        }),
                        onEpisodeDetailClicked = { id ->
                            navController.navigate(
                                Screen.EpisodeDetail.withArgs(id)
                            )
                        },
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedContentScope = this@composable,
                        imageUrl = it.arguments?.getString(NavigationConstants.argCharacterImageUrl),
                        characterName = it.arguments?.getString(NavigationConstants.argCharacterName),
                        characterId = it.arguments?.getString(NavigationConstants.argCharacterId)
                            ?: ""
                    )
                }

                composable(
                    route = Screen.EpisodeDetail.route + "/{${NavigationConstants.argEpisodeId}}",
                    arguments = listOf(
                        navArgument(NavigationConstants.argEpisodeId) {
                            type = NavType.StringType
                        },
                    )
                ) {
                    EpisodeDetailScreen(
                        viewModel = koinViewModel<EpisodeDetailScreenViewModel>(parameters = {
                            parametersOf(it.arguments?.getString(NavigationConstants.argEpisodeId))
                        }),
                        onCharacterDetailClicked = { id, imageUrl, name ->
                            navController.navigate(
                                Screen.CharacterDetail.withArgs(
                                    id,
                                    URLEncoder.encode(imageUrl ?: "", Constants.UTF_8),
                                    name ?: "",
                                )
                            )
                        },
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedContentScope = this@composable
                    )
                }
            }
        }
    }
}

