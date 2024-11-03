package cz.minarik.rickandmorty.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import cz.minarik.rickandmorty.navigation.CharacterId
import cz.minarik.rickandmorty.navigation.CharacterImageUrl
import cz.minarik.rickandmorty.navigation.CharacterName
import cz.minarik.rickandmorty.ui.core.composable.CharactersRow
import cz.minarik.rickandmorty.ui.core.composable.PagedScreenContentWrapper
import cz.minarik.rickandmorty.ui.core.composable.PreviewSurface
import cz.minarik.rickandmorty.ui.core.composable.ScreenPreview
import cz.minarik.rickandmorty.ui.core.model.PreviewViewModel
import cz.minarik.rickandmorty.ui.core.model.UIEvent
import cz.minarik.rickandmorty.ui.core.model.UIState
import cz.minarik.rickandmorty.ui.core.model.UIViewModel
import cz.minarik.rickandmorty.ui.model.ClickableCardVo
import cz.minarik.rickandmorty.ui.model.TVCharacterVo
import cz.minarik.rickandmorty.ui.screens.home.components.ClickableCard
import cz.minarik.rickandmorty.ui.screens.home.components.LoadStateFooter
import cz.minarik.rickandmorty.ui.screens.home.util.CharacterItemUtils.getListColumnsCount
import cz.minarik.rickandmorty.ui.screens.home.util.MockData
import cz.minarik.rickandmorty.ui.theme.SpacingXXSmall
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

/**
 * Home screen with two tabs - Characters and Episodes
 *
 * @param onCharacterDetailClicked Callback for character detail click.
 * @param onEpisodeDetailClicked Callback for episode detail click.
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    onCharacterDetailClicked: (CharacterId, CharacterImageUrl?, CharacterName?) -> Unit,
    onEpisodeDetailClicked: (String) -> Unit,
    viewModel: UIViewModel<HomeScreenData, UIEvent>,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { _ ->
        Column {
            Spacer(
                Modifier.windowInsetsPadding(
                    WindowInsets.statusBars
                ),
            )
            TabsContent(
                pagedCharacters = viewState.data.pagedCharacters.collectAsLazyPagingItems(),
                pagedEpisodes = viewState.data.pagedEpisodes.collectAsLazyPagingItems(),
                onCharacterDetailClicked = onCharacterDetailClicked,
                onEpisodeDetailClicked = onEpisodeDetailClicked,
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope,
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun TabsContent(
    pagedCharacters: LazyPagingItems<TVCharacterVo>,
    pagedEpisodes: LazyPagingItems<ClickableCardVo>,
    onCharacterDetailClicked: (CharacterId, CharacterImageUrl?, CharacterName?) -> Unit,
    onEpisodeDetailClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
) {
    HomeScreenTabLayout(
        charactersContent = {
            CharactersContent(
                modifier = modifier,
                pagedCharacters = pagedCharacters,
                onCharacterDetailClicked = onCharacterDetailClicked,
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope,
            )
        },
        episodesContent = {
            EpisodesContent(
                modifier = modifier,
                pagedEpisodes = pagedEpisodes,
                onEpisodeDetailClicked = onEpisodeDetailClicked,
            )
        },
    )
}

@Composable
fun EpisodesContent(
    pagedEpisodes: LazyPagingItems<ClickableCardVo>,
    onEpisodeDetailClicked: (String) -> Unit,
    modifier: Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        PagedScreenContentWrapper(
            loadState = pagedEpisodes.loadState.refresh,
            onTryAgain = { pagedEpisodes.refresh() }
        ) {
            LazyColumn(
                Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize(), contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(
                    count = pagedEpisodes.itemCount,
                    key = pagedEpisodes.itemKey { it.id },
                    contentType = pagedEpisodes.itemContentType { "contentType" }
                ) { index ->
                    pagedEpisodes[index]?.let { episode ->
                        ClickableCard(
                            modifier = Modifier.padding(SpacingXXSmall),
                            clickableCardVo = episode,
                            onItemClick = {
                                onEpisodeDetailClicked(episode.id)
                            }
                        )
                    }
                }
                item {
                    LoadStateFooter(
                        loadState = pagedEpisodes.loadState.append
                    ) {
                        pagedEpisodes.retry()
                    }
                }
                item {
                    Spacer(
                        Modifier.windowInsetsBottomHeight(
                            WindowInsets.systemBars
                        )
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun CharactersContent(
    pagedCharacters: LazyPagingItems<TVCharacterVo>,
    onCharacterDetailClicked: (CharacterId, CharacterImageUrl?, CharacterName?) -> Unit,
    modifier: Modifier,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
) {
    Box(modifier = modifier.fillMaxSize()) {
        PagedScreenContentWrapper(
            loadState = pagedCharacters.loadState.refresh,
            onTryAgain = { pagedCharacters.refresh() }
        ) {
            LazyColumn(
                Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize(), contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(
                    count = pagedCharacters.itemCount,
                    key = pagedCharacters.itemKey { it.id },
                    contentType = pagedCharacters.itemContentType { "contentType" }
                ) { index ->
                    BoxWithConstraints {
                        val screenWidth = maxWidth
                        val columns = remember(maxWidth) {
                            getListColumnsCount(screenWidth)
                        }
                        CharactersRow(
                            characters = pagedCharacters.itemSnapshotList.items,
                            index = index,
                            columns = columns,
                            onDetailClicked = onCharacterDetailClicked,
                            startingCharacter = pagedCharacters[index],
                            sharedTransitionScope = sharedTransitionScope,
                            animatedContentScope = animatedContentScope,
                        )
                    }
                }
                item {
                    LoadStateFooter(
                        loadState = pagedCharacters.loadState.append
                    ) {
                        pagedCharacters.retry()
                    }
                }
                item {
                    Spacer(
                        Modifier.windowInsetsBottomHeight(
                            WindowInsets.systemBars
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeScreenTabLayout(
    charactersContent: @Composable () -> Unit,
    episodesContent: @Composable () -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { HomeScreenTabs.entries.size })
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    Column {
        TabRow(selectedTabIndex = tabIndex) {
            HomeScreenTabs.entries.forEachIndexed { index, tabData ->
                Tab(selected = tabIndex == index, onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }, text = {
                    Text(
                        text = stringResource(id = tabData.tabTitleStringRes),
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                })
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
        ) { index ->
            when (index) {
                HomeScreenTabs.Characters.ordinal -> charactersContent()
                HomeScreenTabs.Episodes.ordinal -> episodesContent()
            }
        }
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@ScreenPreview
@Composable
private fun HomeScreenPreview() {
    PreviewSurface {
        HomeScreen(
            viewModel = PreviewViewModel(
                UIState(
                    data = HomeScreenData(
                        pagedCharacters = flowOf(
                            PagingData.from(
                                MockData.characters
                            )
                        ),
                        pagedEpisodes = flowOf(
                            PagingData.from(
                                MockData.clickableCards
                            )
                        )
                    )
                ),
            ),
            onCharacterDetailClicked = { _, _, _ -> },
            onEpisodeDetailClicked = { _ -> },
        )
    }
}

