package cz.minarik.alzatest.ui.screens.episodes.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import cz.minarik.alzatest.common.util.decodeSafely
import cz.minarik.alzatest.ui.composables.RaMTopAppBar
import cz.minarik.alzatest.ui.theme.RaMTheme
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
                    Modifier.padding(padding),
                    viewModel.state.collectAsState(initial = EpisodeDetailScreenState()),
                    expanded = viewModel.expanded,
                    onExpanded = viewModel::expandedStateChanged,
                    reload = viewModel::getEpisodeDetail
                )
            }
        )
    }
}


//@Composable
//fun HandleState(
//    modifier: Modifier,
//    state: State<EpisodeDetailScreenState>,
//    expanded: MutableState<Boolean>,
//    reload: () -> Unit,
//    onExpanded: () -> Unit,
//) {
//    state.value.apply {
//        Box(modifier = modifier.fillMaxSize()) {
//            character?.let {
//                CharacterDetailView(
//                    character = character,
//                    expanded = expanded,
//                    onExpanded = onExpanded,
//                )
//            }
//            if (error.isNotBlank()) {
//                ErrorView(modifier = Modifier.fillMaxSize(), error = error) {
//                    reload.invoke()
//                }
//            }
//            if (isLoading) {
//                CircularProgressIndicator(
//                    modifier = Modifier.align(Alignment.Center)
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun CharacterDetailView(
//    character: CharacterDetail,
//    expanded: MutableState<Boolean>,
//    onExpanded: () -> Unit,
//) {
//    val rotationState by animateFloatAsState(
//        targetValue = if (expanded.value) AngleArrowUp else AngleArrowDown
//    )
//    LazyColumn(
//        Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//    ) {
//        item {
//            CharacterHeader(character)
//        }
//
//        if (character.episodes.isNotEmpty()) {
//            item {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = ScreenPaddingHorizontal)
//                        .clip(RoundedCornerShape(8.dp))
//                        .clickable { onExpanded() }
//                ) {
//                    Spacer(modifier = Modifier.height(ScreenPaddingVertical))
//                    Row {
//                        TitleText(stringResource(id = R.string.episodes))
//                        Image(
//                            modifier = Modifier.rotate(rotationState),
//                            painter = painterResource(id = R.drawable.ic_baseline_chevron_right_24),
//                            contentDescription = stringResource(id = R.string.chevron),
//                            colorFilter = ColorFilter.tint(color = MaterialTheme.colors.grayscale.gray700)
//                        )
//                    }
//                    Spacer(modifier = Modifier.height(ScreenPaddingVertical))
//                }
//            }
//
//            items(
//                items = character.episodes,
//                key = { it.id },
//            ) { episode ->
//                if (expanded.value) {
//                    ClickableCard(
//                        modifier = Modifier
//                            .padding(horizontal = ScreenPaddingHorizontal, vertical = SpacingSmall),
//                        clickableCardViewObject = episode.toCardVO(),
//                        onItemClick = {
//                            // todo
//                        }
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//private fun CharacterHeader(character: CharacterDetail) {
//    Image(
//        modifier = Modifier.height(164.dp),
//        painter = rememberAsyncImagePainter(character.imageUrl),
//        contentDescription = stringResource(id = R.string.character_image),
//        contentScale = ContentScale.FillHeight
//    )
//    if (character.name?.isNotBlank() == true) {
//        Text(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(ScreenPaddingHorizontal, vertical = ScreenPaddingVertical),
//            textAlign = TextAlign.Center,
//            text = character.name,
//            style = MaterialTheme.typography.h5,
//        )
//    }
//    if (character.species?.isNotBlank() == true) {
//        TextLine(
//            title = stringResource(id = R.string.species),
//            text = character.species,
//            style = MaterialTheme.typography.body1,
//        )
//    }
//    if (character.type?.isNotBlank() == true) {
//        TextLine(
//            title = stringResource(id = R.string.type),
//            text = character.type,
//            style = MaterialTheme.typography.body1,
//        )
//    }
//    if (character.gender?.isNotBlank() == true) {
//        TextLine(
//            title = stringResource(id = R.string.gender),
//            text = character.gender,
//            style = MaterialTheme.typography.body1,
//        )
//    }
//    if (character.status?.isNotBlank() == true) {
//        TextLine(
//            title = stringResource(id = R.string.status),
//            text = character.status,
//            style = MaterialTheme.typography.body1,
//        )
//    }
//    if (character.origin?.name?.isNotBlank() == true) {
//        TextLine(
//            title = stringResource(id = R.string.origin),
//            text = character.origin.name,
//            style = MaterialTheme.typography.body1,
//        )
//    }
//    if (character.location?.name?.isNotBlank() == true) {
//        TextLine(
//            title = stringResource(id = R.string.location),
//            text = character.location.name,
//            style = MaterialTheme.typography.body1,
//        )
//    }
//}
//
//@Composable
//private fun TextLine(
//    title: String,
//    text: String,
//    style: TextStyle,
//) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(ScreenPaddingHorizontal, vertical = ScreenPaddingVertical),
//        horizontalArrangement = Arrangement.spacedBy(SpacingSmall)
//    ) {
//        TitleText(text = title)
//        Text(
//            text = text,
//            style = style,
//        )
//    }
//}
//
//@Composable
//private fun RowScope.TitleText(text: String) {
//    Text(
//        modifier = Modifier.weight(1f),
//        text = text,
//        style = MaterialTheme.typography.body1,
//        color = MaterialTheme.colors.grayscale.gray700
//    )
//}
//
//private val ScreenPaddingHorizontal = SpacingXLarge
//private val ScreenPaddingVertical = SpacingMedium
//private const val AngleArrowUp = 270f
//private const val AngleArrowDown = 90f
//
//@Preview
//@Composable
//private fun CharacterDetailPreview() {
//    CharacterDetailView(
//        character = CharacterDetail(
//            id = "1",
//            name = "Rick Sanchez",
//            imageUrl = "https://static.wikia.nocookie.net/rickandmorty/images/a/a6/Rick_Sanchez.png/revision/latest/top-crop/width/360/height/360?cb=20160923150728",
//            species = "Human",
//            type = "",
//            status = "Alive",
//            gender = "Male",
//        ),
//        expanded = remember {
//            mutableStateOf(true)
//        },
//        onExpanded = {},
//    )
//}
