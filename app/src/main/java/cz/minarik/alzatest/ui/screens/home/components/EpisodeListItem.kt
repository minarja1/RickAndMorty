package cz.minarik.alzatest.ui.screens.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import cz.minarik.alzatest.R
import cz.minarik.alzatest.domain.model.Episode
import cz.minarik.alzatest.ui.dimens.SpacingXSmall
import cz.minarik.alzatest.ui.theme.CardViewOutlineColor

@Composable
fun EpisodeListItem(
    modifier: Modifier = Modifier,
    episode: Episode,
    onItemClick: (Episode) -> Unit,
) {
    val roundedCornerShape = RoundedCornerShape(8.dp)
    Card(
        shape = roundedCornerShape,
        border = BorderStroke(1.dp, CardViewOutlineColor),
        modifier = modifier
            .clip(roundedCornerShape)
            .clickable { onItemClick(episode) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            episode.name?.let { name ->
                Text(
                    modifier = Modifier.padding(SpacingXSmall),
                    text = name,
                    style = MaterialTheme.typography.h6,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            }
            episode.code?.let { code ->
                Text(
                    modifier = Modifier.padding(SpacingXSmall),
                    text = code,
                    style = MaterialTheme.typography.body2,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            }
            episode.characters?.let { characters ->
                LazyRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(
                        items = characters,
                        key = { it.id },
                    ) { character ->
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(character.imageUrl)
                                .transformations(CircleCropTransformation())
                                .crossfade(true)
                                .build(),
                            contentScale = ContentScale.Crop,
                            contentDescription = stringResource(id = R.string.character_image),
                            modifier = Modifier
                                .height(CharacterImageSize)
                                .padding(SpacingXSmall)
                        )
                    }
                }
            }
        }
    }
}

private val CharacterImageSize = 72.dp

@Preview
@Composable
private fun EpisodeListItemPreview() {
    EpisodeListItem(
        episode = Episode(
            id = "1",
            air_date = "December 2, 2013",
            name = "Pilot",
            code = "S01E01",
            characters = emptyList(),
        ),
        onItemClick = {}
    )
}
