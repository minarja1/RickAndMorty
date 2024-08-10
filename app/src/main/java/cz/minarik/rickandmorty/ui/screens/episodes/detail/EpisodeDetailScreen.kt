package cz.minarik.rickandmorty.ui.screens.episodes.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.minarik.rickandmorty.R
import cz.minarik.rickandmorty.common.util.decodeSafely
import cz.minarik.rickandmorty.ui.components.CircleImagesRow
import cz.minarik.rickandmorty.ui.core.composable.PreviewSurface
import cz.minarik.rickandmorty.ui.core.composable.RaMTopAppBar
import cz.minarik.rickandmorty.ui.core.composable.ScreenContentWrapper
import cz.minarik.rickandmorty.ui.core.composable.ScreenPreview
import cz.minarik.rickandmorty.ui.core.model.ComposeViewModel
import cz.minarik.rickandmorty.ui.core.model.PreviewViewModel
import cz.minarik.rickandmorty.ui.core.model.UIEvent
import cz.minarik.rickandmorty.ui.core.model.UIState
import cz.minarik.rickandmorty.ui.model.CircleImageVo
import cz.minarik.rickandmorty.ui.model.EpisodeDetailVo
import cz.minarik.rickandmorty.ui.theme.SpacingMedium
import cz.minarik.rickandmorty.ui.theme.SpacingSmall
import cz.minarik.rickandmorty.ui.theme.SpacingXLarge
import cz.minarik.rickandmorty.ui.theme.grayscale

@Composable
fun EpisodeDetailScreen(
    onBackClicked: () -> Unit,
    episodeName: String?,
    viewModel: ComposeViewModel<EpisodeDetailScreenData, UIEvent>,
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    ScreenContentWrapper(state = viewState) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(
                    WindowInsets.statusBars
                ),
            topBar = {
                RaMTopAppBar(
                    onBackClicked = onBackClicked,
                    text = episodeName?.decodeSafely()
                )
            },
            content = { padding ->
                viewState.data.episode?.let {
                    EpisodeDetailView(
                        modifier = Modifier.padding(padding),
                        episode = it,
                    )
                }
            }
        )
    }
}

@Composable
private fun EpisodeDetailView(
    episode: EpisodeDetailVo,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            EpisodeHeader(episode)
        }

        if (!episode.characterImages.isNullOrEmpty()) {
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
                CircleImagesRow(
                    modifier = Modifier.padding(top = ScreenPaddingVertical),
                    images = episode.characterImages
                )
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

@Composable
private fun EpisodeHeader(
    episode: EpisodeDetailVo
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

@ScreenPreview
@Composable
private fun EpisodeDetailScreenPreview() {
    PreviewSurface {
        EpisodeDetailScreen(
            viewModel = PreviewViewModel(
                UIState(
                    data = EpisodeDetailScreenData(
                        episode = EpisodeDetailVo(
                            id = "1",
                            name = "Pilot",
                            airDate = "December 2, 2013",
                            code = "S01E01",
                            characterImages = listOf(
                                CircleImageVo(
                                    id = "1",
                                    imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
                                ),
                                CircleImageVo(
                                    id = "2",
                                    imageUrl = "https://rickandmortyapi.com/api/character/avatar/2.jpeg"
                                ),
                                CircleImageVo(
                                    id = "3",
                                    imageUrl = "https://rickandmortyapi.com/api/character/avatar/3.jpeg"
                                ),
                            )
                        )
                    )
                )
            ),
            onBackClicked = {},
            episodeName = "Pilot",
        )
    }
}
