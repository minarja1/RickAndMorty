package cz.minarik.alzatest.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import androidx.paging.compose.itemsIndexed
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import cz.minarik.alzatest.domain.model.Character
import cz.minarik.alzatest.domain.model.Episode
import cz.minarik.alzatest.ui.composables.ErrorView
import cz.minarik.alzatest.ui.dimens.SpacingXXSmall
import cz.minarik.alzatest.ui.model.toCardVO
import cz.minarik.alzatest.ui.screens.home.components.CharacterListItem
import cz.minarik.alzatest.ui.screens.home.components.ClickableCard
import cz.minarik.alzatest.ui.screens.home.components.LoadStateFooter
import cz.minarik.alzatest.ui.screens.home.components.LoadStateScreen
import cz.minarik.alzatest.ui.screens.home.util.CharacterItemUtils.getListColumnsCount
import cz.minarik.alzatest.ui.theme.RaMTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

/**
 * Home screen with two tabs - Characters and Episodes
 *
 * @param onCharacterDetailClicked Callback for character detail click.
 * @param onEpisodeDetailClicked Callback for episode detail click.
 */
@Composable
fun HomeScreen(
    onCharacterDetailClicked: (Character) -> Unit,
    onEpisodeDetailClicked: (Episode) -> Unit,
) {
    val viewModel = getViewModel<HomeScreenViewModel>()
    RaMTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) { padding ->
            HomeScreenContent(
                modifier = Modifier.padding(padding),
                pagedCharacters = viewModel.pagedCharacters.collectAsLazyPagingItems(),
                pagedEpisodes = viewModel.pagedEpisodes.collectAsLazyPagingItems(),
                onCharacterDetailClicked = onCharacterDetailClicked,
                onEpisodeDetailClicked = onEpisodeDetailClicked,
            )
        }
    }
}

@Composable
private fun HomeScreenContent(
    pagedCharacters: LazyPagingItems<Character>,
    pagedEpisodes: LazyPagingItems<Episode>,
    onCharacterDetailClicked: (Character) -> Unit,
    onEpisodeDetailClicked: (Episode) -> Unit,
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
    pagedEpisodes: LazyPagingItems<Episode>,
    onEpisodeDetailClicked: (Episode) -> Unit,
    modifier: Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize(), contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(
                items = pagedEpisodes,
                key = { it.id },
            ) { episode ->
                episode?.let {
                    ClickableCard(
                        modifier = Modifier.padding(SpacingXXSmall),
                        clickableCardViewObject = episode.toCardVO(),
                        onItemClick = {
                            onEpisodeDetailClicked(episode)
                        }
                    )
                }
            }
            if (pagedEpisodes.loadState.append !is LoadState.NotLoading) {
                item {
                    LoadStateFooter(
                        pagedEpisodes.loadState.append
                    ) {
                        pagedEpisodes.retry()
                    }
                }
            }
        }

        LoadStateScreen(pagedEpisodes.loadState.refresh) {
            pagedEpisodes.refresh()
        }

    }
}

@Composable
private fun CharactersContent(
    pagedCharacters: LazyPagingItems<Character>,
    onCharacterDetailClicked: (Character) -> Unit,
    modifier: Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize(), contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            itemsIndexed(pagedCharacters) { index, characterInList ->
                BoxWithConstraints {
                    val screenWidth = maxWidth
                    val columns = remember(maxWidth) {
                        getListColumnsCount(screenWidth)
                    }
                    CharactersRow(
                        pagedCharacters.itemSnapshotList.items,
                        index,
                        columns,
                        onCharacterDetailClicked,
                        characterInList
                    )
                }
            }
            if (pagedCharacters.loadState.append !is LoadState.NotLoading) {
                item {
                    LoadStateFooter(
                        pagedCharacters.loadState.append
                    ) {
                        pagedCharacters.retry()
                    }
                }
            }
        }

        LoadStateScreen(pagedCharacters.loadState.refresh) {
            pagedCharacters.refresh()
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
            )
        }) {
            HomeScreenTabs.values().forEachIndexed { index, tabData ->
                val title = stringResource(id = tabData.tabTitleStringRes)
                Tab(selected = tabIndex == index, onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }, text = {
                    Text(title)
                })
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
            count = HomeScreenTabs.values().size,
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
    characters: List<Character>,
    index: Int,
    columns: Int,
    onDetailClicked: (Character) -> Unit,
    startingCharacter: Character?
) {
    if (index % columns == 0) {
        key(startingCharacter?.id) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                for (i in 0 until columns) {
                    val character = characters.getOrNull(index + i)
                    if (character != null) {
                        CharacterListItem(modifier = Modifier
                            .weight(1f)
                            .padding(SpacingXXSmall),
                            character = character,
                            onItemClick = { onDetailClicked(it) })
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


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ErrorViewPreview() {
    ErrorView(error = "Preview error") {}
}

