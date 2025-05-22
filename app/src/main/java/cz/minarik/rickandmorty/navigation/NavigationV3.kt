package cz.minarik.rickandmorty.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
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

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
// todo use this when stable
fun NavigationV3() {
    RaMTheme {
        val backStack = remember { mutableStateListOf<ScreenV2>(ScreenV2.HomePage) }

        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = { key ->
                when (key) {
                    is ScreenV2.HomePage -> NavEntry(key) {
                        HomeScreen(
                            viewModel = koinViewModel<HomeScreenViewModel>(),
                            onCharacterDetailClicked = { id, imageUrl, name ->
                                backStack.add(
                                    ScreenV2.CharacterDetail(
                                        id = id,
                                        imageUrl = URLEncoder.encode(
                                            imageUrl.orEmpty(), Constants.UTF_8
                                        ),
                                        name = name.orEmpty(),
                                    )
                                )
                            },
                            onEpisodeDetailClicked = { id ->
                                backStack.add(ScreenV2.EpisodeDetail(id = id))
                            },
                            // todo add shared elements when supported
                            sharedTransitionScope = null,
                            animatedContentScope = null,
                        )
                    }

                    is ScreenV2.CharacterDetail -> NavEntry(key) {
                        CharacterDetailScreen(
                            viewModel = koinViewModel<CharacterDetailScreenViewModel>(parameters = {
                                parametersOf(key.id)
                            }),
                            onEpisodeDetailClicked = { id ->
                                backStack.add(ScreenV2.EpisodeDetail(id = id))
                            },
                            imageUrl = key.imageUrl,
                            characterName = key.name,
                            characterId = key.id,
                            // todo add shared elements when supported
                            sharedTransitionScope = null,
                            animatedContentScope = null,
                        )
                    }

                    is ScreenV2.EpisodeDetail -> NavEntry(key) {
                        EpisodeDetailScreen(
                            viewModel = koinViewModel<EpisodeDetailScreenViewModel>(parameters = {
                                parametersOf(key.id)
                            }),
                            onCharacterDetailClicked = { id, imageUrl, name ->
                                backStack.add(
                                    ScreenV2.CharacterDetail(
                                        id = id,
                                        imageUrl = URLEncoder.encode(
                                            imageUrl.orEmpty(), Constants.UTF_8
                                        ),
                                        name = name.orEmpty(),
                                    )
                                )
                            },
                            // todo add shared elements when supported
                            sharedTransitionScope = null,
                            animatedContentScope = null,
                        )
                    }
                }
            })
    }
}
