package cz.minarik.rickandmorty.ui.screens.episodes.detail

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.minarik.rickandmorty.R
import cz.minarik.rickandmorty.navigation.CharacterId
import cz.minarik.rickandmorty.navigation.CharacterImageUrl
import cz.minarik.rickandmorty.navigation.CharacterName
import cz.minarik.rickandmorty.ui.core.composable.CharactersRow
import cz.minarik.rickandmorty.ui.core.composable.PreviewSurface
import cz.minarik.rickandmorty.ui.core.composable.ScreenContentWrapper
import cz.minarik.rickandmorty.ui.core.composable.ScreenPreview
import cz.minarik.rickandmorty.ui.core.model.PreviewViewModel
import cz.minarik.rickandmorty.ui.core.model.UIEvent
import cz.minarik.rickandmorty.ui.core.model.UIState
import cz.minarik.rickandmorty.ui.core.model.UIViewModel
import cz.minarik.rickandmorty.ui.model.EpisodeDetailVo
import cz.minarik.rickandmorty.ui.screens.home.util.CharacterItemUtils.getListColumnsCount
import cz.minarik.rickandmorty.ui.screens.home.util.MockData
import cz.minarik.rickandmorty.ui.theme.SpacingMedium
import cz.minarik.rickandmorty.ui.theme.SpacingSmall
import cz.minarik.rickandmorty.ui.theme.SpacingXLarge

@OptIn(ExperimentalSharedTransitionApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EpisodeDetailScreen(
    viewModel: UIViewModel<EpisodeDetailScreenData, UIEvent>,
    onCharacterDetailClicked: (CharacterId, CharacterImageUrl?, CharacterName?) -> Unit,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    ScreenContentWrapper(state = viewState) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            content = { _ ->
                viewState.data.episode?.let {
                    EpisodeDetailView(
                        episode = it,
                        onCharacterDetailClicked = onCharacterDetailClicked,
                        modifier = Modifier
                            .fillMaxSize(),
                        sharedTransitionScope = sharedTransitionScope,
                        animatedContentScope = animatedContentScope,
                    )
                }
            }
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun EpisodeDetailView(
    episode: EpisodeDetailVo,
    modifier: Modifier = Modifier,
    onCharacterDetailClicked: (CharacterId, CharacterImageUrl?, CharacterName?) -> Unit,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(
            horizontal = ScreenPaddingHorizontal,
        )
    ) {

        item {
            Spacer(
                Modifier.windowInsetsTopHeight(
                    WindowInsets.statusBars
                )
            )
        }

        item {
            EpisodeHeader(episode)
        }

        if (!episode.characters.isNullOrEmpty()) {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = ScreenPaddingVertical),
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.characters),
                    style = MaterialTheme.typography.headlineMedium,
                )
            }

            items(
                count = episode.characters.size,
            ) { index ->
                BoxWithConstraints {
                    val screenWidth = maxWidth
                    val columns = remember(maxWidth) {
                        getListColumnsCount(screenWidth)
                    }
                    CharactersRow(
                        characters = episode.characters,
                        index = index,
                        columns = columns,
                        onDetailClicked = onCharacterDetailClicked,
                        startingCharacter = episode.characters[index],
                        sharedTransitionScope = sharedTransitionScope,
                        animatedContentScope = animatedContentScope,
                    )
                }
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
                .padding(vertical = ScreenPaddingVertical),
            textAlign = TextAlign.Center,
            text = episode.name,
            style = MaterialTheme.typography.headlineMedium,
        )
    }
    if (episode.airDate?.isNotBlank() == true) {
        TextLine(
            title = stringResource(id = R.string.aired),
            text = episode.airDate,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
    if (episode.code?.isNotBlank() == true) {
        TextLine(
            title = stringResource(id = R.string.code),
            text = episode.code,
            style = MaterialTheme.typography.bodyLarge,
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
            .padding(vertical = ScreenPaddingVertical),
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
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onBackground
    )
}

private val ScreenPaddingHorizontal = SpacingXLarge
private val ScreenPaddingVertical = SpacingMedium

@OptIn(ExperimentalSharedTransitionApi::class)
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
                            characters = MockData.characters
                        )
                    )
                )
            ),
            onCharacterDetailClicked = { _, _, _ -> }
        )
    }
}
