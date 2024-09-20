package cz.minarik.rickandmorty.ui.screens.characters.detail

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import cz.minarik.rickandmorty.R
import cz.minarik.rickandmorty.common.util.decodeSafely
import cz.minarik.rickandmorty.ui.core.composable.PreviewSurface
import cz.minarik.rickandmorty.ui.core.composable.RaMTopAppBar
import cz.minarik.rickandmorty.ui.core.composable.ScreenContentWrapper
import cz.minarik.rickandmorty.ui.core.composable.ScreenPreview
import cz.minarik.rickandmorty.ui.core.model.ComposeViewModel
import cz.minarik.rickandmorty.ui.core.model.PreviewViewModel
import cz.minarik.rickandmorty.ui.core.model.UIState
import cz.minarik.rickandmorty.ui.model.CharacterDetailVo
import cz.minarik.rickandmorty.ui.screens.home.components.ClickableCard
import cz.minarik.rickandmorty.ui.screens.home.util.MockData
import cz.minarik.rickandmorty.ui.theme.SpacingMedium
import cz.minarik.rickandmorty.ui.theme.SpacingSmall
import cz.minarik.rickandmorty.ui.theme.SpacingXLarge
import cz.minarik.rickandmorty.ui.theme.grayscale

@Composable
fun CharacterDetailScreen(
    onBackClicked: () -> Unit,
    characterName: String?,
    onEpisodeDetailClicked: (String, String?) -> Unit,
    viewModel: ComposeViewModel<CharacterDetailScreenData, CharacterDetailScreenEvent>,
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
                    text = characterName?.decodeSafely()
                )
            },
            content = { padding ->
                viewState.data.character?.let {
                    CharacterDetailContent(
                        modifier = Modifier.padding(padding),
                        character = it,
                        expanded = viewState.data.episodesExpanded,
                        onExpanded = { viewModel.onEvent(CharacterDetailScreenEvent.ExpandEpisodesClicked) },
                        onEpisodeDetailClicked = onEpisodeDetailClicked,
                    )
                }
            }
        )
    }
}

@Composable
private fun CharacterDetailContent(
    character: CharacterDetailVo,
    expanded: Boolean,
    onExpanded: () -> Unit,
    onEpisodeDetailClicked: (String, String?) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            CharacterHeader(character)
        }

        if (character.episodes.isNotEmpty()) {
            item {
                EpisodesExpandButton(
                    expanded = expanded,
                    onExpanded = onExpanded
                )
            }

            if (expanded) {
                items(
                    items = character.episodes,
                    key = { it.id },
                ) { episode ->
                    ClickableCard(
                        modifier = Modifier
                            .padding(
                                horizontal = ScreenPaddingHorizontal,
                                vertical = SpacingSmall
                            ),
                        clickableCardVo = episode,
                        onItemClick = {
                            onEpisodeDetailClicked.invoke(episode.id, episode.title)
                        }
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
}

@Composable
private fun EpisodesExpandButton(
    expanded: Boolean = false,
    onExpanded: () -> Unit,
) {

    val rotationState by animateFloatAsState(
        targetValue = if (expanded) AngleArrowUp else AngleArrowDown,
        label = "rotation"
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = ScreenPaddingHorizontal,
                vertical = ItemPaddingVertical
            )
            .clip(RoundedCornerShape(8.dp))
            .clickable { onExpanded() }
    ) {
        Row {
            Text(
                text = stringResource(id = R.string.episodes),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.weight(0.1f))
            Image(
                modifier = Modifier.rotate(rotationState),
                painter = painterResource(id = R.drawable.ic_baseline_chevron_right_24),
                contentDescription = stringResource(id = R.string.chevron),
                colorFilter = ColorFilter.tint(color = MaterialTheme.colors.grayscale.gray700)
            )
        }
    }
}

@Composable
private fun CharacterHeader(character: CharacterDetailVo) {
    Image(
        modifier = Modifier
            .aspectRatio(1f)
            .fillMaxWidth(),
        painter = rememberAsyncImagePainter(character.imageUrl),
        contentDescription = stringResource(id = R.string.character_image),
        contentScale = ContentScale.Crop
    )
    Column(
        modifier = Modifier.padding(
            horizontal = ScreenPaddingHorizontal,
        )
    ) {
        if (character.name?.isNotBlank() == true) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = ItemPaddingVertical),
                textAlign = TextAlign.Center,
                text = character.name,
                style = MaterialTheme.typography.h5,
            )
        }
        if (character.species?.isNotBlank() == true) {
            TextLine(
                title = stringResource(id = R.string.species),
                text = character.species,
            )
        }
        if (character.type?.isNotBlank() == true) {
            TextLine(
                title = stringResource(id = R.string.type),
                text = character.type,
            )
        }
        if (character.gender?.isNotBlank() == true) {
            TextLine(
                title = stringResource(id = R.string.gender),
                text = character.gender,
            )
        }
        if (character.status?.isNotBlank() == true) {
            TextLine(
                title = stringResource(id = R.string.status),
                text = character.status,
            )
        }
        if (character.origin?.isNotBlank() == true) {
            TextLine(
                title = stringResource(id = R.string.origin),
                text = character.origin,
            )
        }
        if (character.location?.isNotBlank() == true) {
            TextLine(
                title = stringResource(id = R.string.location),
                text = character.location,
            )
        }
    }
}

@Composable
private fun TextLine(
    title: String,
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = ItemPaddingVertical),
        horizontalArrangement = Arrangement.spacedBy(SpacingSmall)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground
        )
        Spacer(modifier = Modifier.weight(0.1f))
        Text(
            text = text,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground,
        )
    }
}

private val ScreenPaddingHorizontal = SpacingXLarge
private val ItemPaddingVertical = SpacingMedium
private const val AngleArrowUp = 270f
private const val AngleArrowDown = 90f

@ScreenPreview
@Composable
private fun CharacterDetailScreenPreview() {
    PreviewSurface {
        CharacterDetailScreen(
            viewModel = PreviewViewModel(
                UIState(
                    data = CharacterDetailScreenData(
                        character = CharacterDetailVo(
                            id = "1",
                            name = "Rick Sanchez",
                            imageUrl = "https://static.wikia.nocookie.net/rickandmorty/images/a/a6/Rick_Sanchez.png/" +
                                    "revision/latest/top-crop/width/360/height/360?cb=20160923150728",
                            species = "Human a;sldfjk;alsdkj f;asl kdfalksjd f;lasjkd f;al",
                            type = "",
                            status = "Alive",
                            gender = "Male",
                            episodes = MockData.clickableCards
                        ),
                        episodesExpanded = true,
                    )
                )
            ),
            onBackClicked = {},
            characterName = "Rick Sanchez",
            onEpisodeDetailClicked = { _, _ ->

            }
        )
    }
}

