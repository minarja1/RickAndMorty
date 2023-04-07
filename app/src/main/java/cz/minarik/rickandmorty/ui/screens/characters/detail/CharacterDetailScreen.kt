package cz.minarik.rickandmorty.ui.screens.characters.detail

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import cz.minarik.rickandmorty.R
import cz.minarik.rickandmorty.common.util.decodeSafely
import cz.minarik.rickandmorty.domain.model.CharacterDetail
import cz.minarik.rickandmorty.domain.model.Episode
import cz.minarik.rickandmorty.ui.composables.ErrorView
import cz.minarik.rickandmorty.ui.composables.RaMTopAppBar
import cz.minarik.rickandmorty.ui.dimens.SpacingMedium
import cz.minarik.rickandmorty.ui.dimens.SpacingSmall
import cz.minarik.rickandmorty.ui.dimens.SpacingXLarge
import cz.minarik.rickandmorty.ui.model.toCardVO
import cz.minarik.rickandmorty.ui.screens.home.components.ClickableCard
import cz.minarik.rickandmorty.ui.theme.RaMTheme
import cz.minarik.rickandmorty.ui.theme.grayscale
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf


@Composable
fun CharacterDetailScreen(
    onBackClicked: () -> Unit,
    characterId: String?,
    characterName: String?,
    onEpisodeDetailClicked: (Episode) -> Unit,
) {
    val viewModel = getViewModel<CharacterDetailScreenViewModel> {
        parametersOf(characterId)
    }
    RaMTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                RaMTopAppBar(
                    onBackClicked = onBackClicked,
                    text = characterName?.decodeSafely()
                )
            },
            content = { padding ->
                HandleState(
                    modifier = Modifier.padding(padding),
                    characterDetailScreenStateState = viewModel.state.collectAsState(initial = CharacterDetailScreenState()),
                    expanded = viewModel.episodesExpanded,
                    onExpanded = viewModel::expandedStateChanged,
                    reload = viewModel::getCharacterDetail,
                    onEpisodeDetailClicked = onEpisodeDetailClicked,
                )
            }
        )
    }
}


@Composable
fun HandleState(
    characterDetailScreenStateState: State<CharacterDetailScreenState>,
    expanded: State<Boolean>,
    reload: () -> Unit,
    onExpanded: () -> Unit,
    onEpisodeDetailClicked: (Episode) -> Unit,
    modifier: Modifier,
) {
    Crossfade(targetState = characterDetailScreenStateState.value) { state ->
        state.apply {
            Box(modifier = modifier.fillMaxSize()) {
                character?.let {
                    CharacterDetailView(
                        character = character,
                        expanded = expanded,
                        onExpanded = onExpanded,
                        onEpisodeDetailClicked = onEpisodeDetailClicked,
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
}

@Composable
fun CharacterDetailView(
    character: CharacterDetail,
    expanded: State<Boolean>,
    onExpanded: () -> Unit,
    onEpisodeDetailClicked: (Episode) -> Unit,
) {
    val rotationState by animateFloatAsState(
        targetValue = if (expanded.value) AngleArrowUp else AngleArrowDown
    )
    LazyColumn(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            CharacterHeader(character)
        }

        if (character.episodes.isNotEmpty()) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = ScreenPaddingHorizontal)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onExpanded() }
                ) {
                    Spacer(modifier = Modifier.height(ScreenPaddingVertical))
                    Row {
                        TitleText(stringResource(id = R.string.episodes))
                        Image(
                            modifier = Modifier.rotate(rotationState),
                            painter = painterResource(id = R.drawable.ic_baseline_chevron_right_24),
                            contentDescription = stringResource(id = R.string.chevron),
                            colorFilter = ColorFilter.tint(color = MaterialTheme.colors.grayscale.gray700)
                        )
                    }
                    Spacer(modifier = Modifier.height(ScreenPaddingVertical))
                }
            }

            items(
                items = character.episodes,
                key = { it.id },
            ) { episode ->
                if (expanded.value) {
                    ClickableCard(
                        modifier = Modifier
                            .padding(horizontal = ScreenPaddingHorizontal, vertical = SpacingSmall),
                        clickableCardViewObject = episode.toCardVO(),
                        onItemClick = {
                            onEpisodeDetailClicked.invoke(episode)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun CharacterHeader(character: CharacterDetail) {
    Image(
        modifier = Modifier.height(164.dp),
        painter = rememberAsyncImagePainter(character.imageUrl),
        contentDescription = stringResource(id = R.string.character_image),
        contentScale = ContentScale.FillHeight
    )
    if (character.name?.isNotBlank() == true) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = ScreenPaddingHorizontal,
                    vertical = ScreenPaddingVertical
                ),
            textAlign = TextAlign.Center,
            text = character.name,
            style = MaterialTheme.typography.h5,
        )
    }
    if (character.species?.isNotBlank() == true) {
        TextLine(
            title = stringResource(id = R.string.species),
            text = character.species,
            style = MaterialTheme.typography.body1,
        )
    }
    if (character.type?.isNotBlank() == true) {
        TextLine(
            title = stringResource(id = R.string.type),
            text = character.type,
            style = MaterialTheme.typography.body1,
        )
    }
    if (character.gender?.isNotBlank() == true) {
        TextLine(
            title = stringResource(id = R.string.gender),
            text = character.gender,
            style = MaterialTheme.typography.body1,
        )
    }
    if (character.status?.isNotBlank() == true) {
        TextLine(
            title = stringResource(id = R.string.status),
            text = character.status,
            style = MaterialTheme.typography.body1,
        )
    }
    if (character.origin?.name?.isNotBlank() == true) {
        TextLine(
            title = stringResource(id = R.string.origin),
            text = character.origin.name,
            style = MaterialTheme.typography.body1,
        )
    }
    if (character.location?.name?.isNotBlank() == true) {
        TextLine(
            title = stringResource(id = R.string.location),
            text = character.location.name,
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
private const val AngleArrowUp = 270f
private const val AngleArrowDown = 90f

@Preview
@Composable
private fun CharacterDetailPreview() {
    CharacterDetailView(
        character = CharacterDetail(
            id = "1",
            name = "Rick Sanchez",
            imageUrl = "https://static.wikia.nocookie.net/rickandmorty/images/a/a6/Rick_Sanchez.png/" +
                    "revision/latest/top-crop/width/360/height/360?cb=20160923150728",
            species = "Human",
            type = "",
            status = "Alive",
            gender = "Male",
        ),
        expanded = remember {
            mutableStateOf(true)
        },
        onExpanded = {},
        onEpisodeDetailClicked = { },
    )
}
