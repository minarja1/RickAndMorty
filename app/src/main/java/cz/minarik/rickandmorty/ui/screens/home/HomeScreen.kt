package cz.minarik.rickandmorty.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import cz.minarik.rickandmorty.ui.core.composable.PagedScreenContentWrapper
import cz.minarik.rickandmorty.ui.core.composable.PreviewSurface
import cz.minarik.rickandmorty.ui.core.composable.ScreenPreview
import cz.minarik.rickandmorty.ui.core.model.PreviewViewModel
import cz.minarik.rickandmorty.ui.core.model.UIEvent
import cz.minarik.rickandmorty.ui.core.model.UIState
import cz.minarik.rickandmorty.ui.core.model.UIViewModel
import cz.minarik.rickandmorty.ui.model.ClickableCardVo
import cz.minarik.rickandmorty.ui.model.TVCharacterVo
import cz.minarik.rickandmorty.ui.screens.home.components.CharacterListItem
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
@Composable
fun HomeScreen(
    onCharacterDetailClicked: (String, String?) -> Unit,
    onEpisodeDetailClicked: (String, String?) -> Unit,
    viewModel: UIViewModel<HomeScreenData, UIEvent>,
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(
                WindowInsets.statusBars
            ),
        backgroundColor = MaterialTheme.colors.background,
    ) { padding ->
        TabsContent(
            modifier = Modifier.padding(padding),
            pagedCharacters = viewState.data.pagedCharacters.collectAsLazyPagingItems(),
            pagedEpisodes = viewState.data.pagedEpisodes.collectAsLazyPagingItems(),
            onCharacterDetailClicked = onCharacterDetailClicked,
            onEpisodeDetailClicked = onEpisodeDetailClicked,
        )
    }
}

@Composable
private fun TabsContent(
    pagedCharacters: LazyPagingItems<TVCharacterVo>,
    pagedEpisodes: LazyPagingItems<ClickableCardVo>,
    onCharacterDetailClicked: (String, String?) -> Unit,
    onEpisodeDetailClicked: (String, String?) -> Unit,
    modifier: Modifier,
) {
    HomeScreenTabLayout(
        charactersContent = {
            CharactersContent(
                modifier = modifier,
                pagedCharacters = pagedCharacters,
                onCharacterDetailClicked = onCharacterDetailClicked
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
    onEpisodeDetailClicked: (String, String?) -> Unit,
    modifier: Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        PagedScreenContentWrapper(
            loadState = pagedEpisodes.loadState.refresh,
            onTryAgain = { pagedEpisodes.refresh() }
        ) {
            LazyColumn(
                Modifier
                    .background(MaterialTheme.colors.background)
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
                                onEpisodeDetailClicked(episode.id, episode.title)
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

@Composable
private fun CharactersContent(
    pagedCharacters: LazyPagingItems<TVCharacterVo>,
    onCharacterDetailClicked: (String, String?) -> Unit,
    modifier: Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        PagedScreenContentWrapper(
            loadState = pagedCharacters.loadState.refresh,
            onTryAgain = { pagedCharacters.refresh() }
        ) {
            LazyColumn(
                Modifier
                    .background(MaterialTheme.colors.background)
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
                            startingCharacter = pagedCharacters[index]
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

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun HomeScreenTabLayout(
    charactersContent: @Composable () -> Unit,
    episodesContent: @Composable () -> Unit,
) {
    val pagerState = rememberPagerState()
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    Column {
        TabRow(selectedTabIndex = tabIndex, indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                color = MaterialTheme.colors.onBackground,
            )
        }) {
            HomeScreenTabs.entries.forEachIndexed { index, tabData ->
                Tab(selected = tabIndex == index, onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }, text = {
                    Text(
                        text = stringResource(id = tabData.tabTitleStringRes),
                        color = MaterialTheme.colors.onBackground,
                    )
                })
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
            count = HomeScreenTabs.entries.size,
        ) { index ->
            when (index) {
                HomeScreenTabs.Characters.ordinal -> charactersContent()
                HomeScreenTabs.Episodes.ordinal -> episodesContent()
            }
        }
    }
}

@Composable
private fun CharactersRow(
    characters: List<TVCharacterVo>,
    index: Int,
    columns: Int,
    onDetailClicked: (String, String?) -> Unit,
    startingCharacter: TVCharacterVo?
) {
    if (index % columns == 0) {
        key(startingCharacter?.id) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                for (i in 0 until columns) {
                    val character = characters.getOrNull(index + i)
                    if (character != null) {
                        CharacterListItem(
                            modifier = Modifier
                                .weight(1f)
                                .padding(SpacingXXSmall),
                            character = character,
                            onItemClick = { onDetailClicked(it.id, it.name) })
                    } else {
                        // invisible placeholders to fill empty space until end of row
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .alpha(0f)
                                .padding(SpacingXXSmall),
                        )
                    }
                }
            }
        }
    }
}


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
            onCharacterDetailClicked = { _, _ -> },
            onEpisodeDetailClicked = { _, _ -> },
        )
    }
}

