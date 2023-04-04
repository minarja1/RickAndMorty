package cz.minarik.alzatest.ui.screens.episodes.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import cz.minarik.alzatest.R
import cz.minarik.alzatest.common.util.decodeSafely
import cz.minarik.alzatest.domain.model.EpisodeDetail
import cz.minarik.alzatest.ui.components.CharactersRow
import cz.minarik.alzatest.ui.composables.ErrorView
import cz.minarik.alzatest.ui.composables.RaMTopAppBar
import cz.minarik.alzatest.ui.dimens.SpacingMedium
import cz.minarik.alzatest.ui.dimens.SpacingSmall
import cz.minarik.alzatest.ui.dimens.SpacingXLarge
import cz.minarik.alzatest.ui.theme.RaMTheme
import cz.minarik.alzatest.ui.theme.grayscale
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf


@Composable
fun EpisodeDetailScreen(
    onBackClicked: () -> Unit,
    episodeId: String?,
    episodeName: String?
) {
    val viewModel = getViewModel<EpisodeDetailScreenViewModel> {
        parametersOf(episodeId)
    }
    RaMTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                RaMTopAppBar(
                    onBackClicked = onBackClicked,
                    text = episodeName?.decodeSafely() ?: ""
                )
            },
            content = { padding ->
                HandleState(
                    modifier = Modifier.padding(padding),
                    state = viewModel.state.collectAsState(initial = EpisodeDetailScreenState()),
                    reload = viewModel::getEpisodeDetail
                )
            }
        )
    }
}


@Composable
fun HandleState(
    state: State<EpisodeDetailScreenState>,
    reload: () -> Unit,
    modifier: Modifier,
) {
    state.value.apply {
        Box(modifier = modifier.fillMaxSize()) {
            episode?.let {
                EpisodeDetailView(
                    episode = episode,
                )
            }
            if (error.isNotBlank()) {
                ErrorView(modifier = Modifier.fillMaxSize(), error = error) {
                    reload.invoke()
                }
            }
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun EpisodeDetailView(
    episode: EpisodeDetail,
) {
    LazyColumn(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            EpisodeHeader(episode)
        }

        if (!episode.characters.isNullOrEmpty()) {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = ScreenPaddingHorizontal,
                            vertical = ScreenPaddingVertical
                        ),
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.characters),
                    style = MaterialTheme.typography.h6,
                )
                CharactersRow(
                    modifier = Modifier.padding(top = ScreenPaddingVertical),
                    characters = episode.characters
                )
            }
        }
    }
}

@Composable
private fun EpisodeHeader(
    episode: EpisodeDetail
) {
    if (episode.name?.isNotBlank() == true) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = ScreenPaddingHorizontal,
                    vertical = ScreenPaddingVertical
                ),
            textAlign = TextAlign.Center,
            text = episode.name,
            style = MaterialTheme.typography.h5,
        )
    }
    if (episode.airDate?.isNotBlank() == true) {
        TextLine(
            title = stringResource(id = R.string.aired),
            text = episode.airDate,
            style = MaterialTheme.typography.body1,
        )
    }
    if (episode.code?.isNotBlank() == true) {
        TextLine(
            title = stringResource(id = R.string.code),
            text = episode.code,
            style = MaterialTheme.typography.body1,
        )
    }
}

@Composable
private fun TextLine(
    title: String,
    text: String,
    style: TextStyle,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = ScreenPaddingHorizontal,
                vertical = ScreenPaddingVertical
            ),
        horizontalArrangement = Arrangement.spacedBy(SpacingSmall)
    ) {
        TitleText(text = title)
        Text(
            text = text,
            style = style,
        )
    }
}

@Composable
private fun RowScope.TitleText(text: String) {
    Text(
        modifier = Modifier.weight(1f),
        text = text,
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.grayscale.gray700
    )
}

private val ScreenPaddingHorizontal = SpacingXLarge
private val ScreenPaddingVertical = SpacingMedium

@Preview
@Composable
private fun CharacterDetailPreview() {
    EpisodeDetailView(
        episode = EpisodeDetail(
            id = "1",
            name = "Rick Sanchez",
            airDate = "December 2, 2013",
            code = "S01E01",
            characters = listOf(
                cz.minarik.alzatest.domain.model.Character(
                    id = "1",
                    name = "Rick Sanchez",
                    imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                )
            )
        ),
    )
}
