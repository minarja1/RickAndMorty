package cz.minarik.alzatest.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import cz.minarik.alzatest.R
import cz.minarik.alzatest.domain.model.Character
import cz.minarik.alzatest.ui.composables.AlzaTopAppBar
import cz.minarik.alzatest.ui.composables.ErrorView
import cz.minarik.alzatest.ui.dimens.SpacingXSmall
import cz.minarik.alzatest.ui.dimens.SpacingXXSmall
import cz.minarik.alzatest.ui.screens.home.components.CharacterListItem
import cz.minarik.alzatest.ui.screens.home.util.CharacterItemUtils.getListColumnsCount
import cz.minarik.alzatest.ui.theme.AlzaTestTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(
    onDetailClicked: (Character) -> Unit,
) {
    val viewModel = getViewModel<HomeScreenViewModel>()
    AlzaTestTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                AlzaTopAppBar(text = stringResource(id = R.string.characters))
            },
        ) { padding ->
            HomeScreenContent(
                Modifier.padding(padding),
                viewModel.pagedCharacters.collectAsLazyPagingItems(),
                onDetailClicked,
            )
        }
    }
}

@Composable
private fun HomeScreenContent(
    modifier: Modifier,
    pagedCharacters: LazyPagingItems<Character>,
    onDetailClicked: (Character) -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            itemsIndexed(pagedCharacters) { index, characterInList ->
                BoxWithConstraints {
                    val screenWidth = maxWidth
                    val columns = remember(maxWidth) {
                        getListColumnsCount(screenWidth)
                    }
                    CharactersRow(pagedCharacters.itemSnapshotList.items, index, columns, onDetailClicked, characterInList)
                }
            }
            pagedCharacters.loadState.append.apply {
                when (this) {
                    is LoadState.Loading -> {
                        // next page loading
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(SpacingXSmall)
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                )
                            }
                        }
                    }
                    is LoadState.Error -> {
                        // next page error
                        item {
                            ErrorView(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(SpacingXSmall),
                                error = this@apply.error.message ?: ""
                            ) {
                                pagedCharacters.retry()
                            }
                        }
                    }
                    is LoadState.NotLoading -> Unit // handled outside of list*
                }
            }
        }

        StateScreen(pagedCharacters.loadState.refresh) {
            pagedCharacters.refresh()
        }

    }
}

@Composable
private fun BoxScope.StateScreen(
    loadState: LoadState,
    refresh: () -> Unit
) {
    loadState.apply {
        when (this) {
            is LoadState.Loading -> {
                // initial loading
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is LoadState.Error -> {
                // initial error
                ErrorView(
                    modifier = Modifier.fillMaxSize(),
                    error = this.error.message ?: "",
                    onTryAgainClicked = refresh
                )
            }
            else -> Unit /* handled in list */
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
                        CharacterListItem(
                            modifier = Modifier
                                .weight(1f)
                                .padding(SpacingXXSmall),
                            character = character,
                            onItemClick = { onDetailClicked(it) }
                        )
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
