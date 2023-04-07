package cz.minarik.alzatest.ui.screens.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import cz.minarik.alzatest.R
import cz.minarik.alzatest.domain.model.TVCharacter
import cz.minarik.alzatest.ui.dimens.SpacingXSmall
import cz.minarik.alzatest.ui.theme.CardViewOutlineColor

@Composable
fun CharacterListItem(
    modifier: Modifier = Modifier,
    character: TVCharacter,
    onItemClick: (TVCharacter) -> Unit,
) {
    val roundedCornerShape = RoundedCornerShape(8.dp)
    Card(
        shape = roundedCornerShape,
        border = BorderStroke(1.dp, CardViewOutlineColor),
        modifier = modifier
            .clip(roundedCornerShape)
            .clickable { onItemClick(character) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(character.imageUrl)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(id = R.string.character_image),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(CharacterImageSize)
            )
            character.name?.let {
                Text(
                    modifier = Modifier.padding(SpacingXSmall),
                    text = character.name,
                    style = MaterialTheme.typography.body1,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            }
        }
    }
}

private val CharacterImageSize = 148.dp

@Preview
@Composable
private fun CharacterListItemPreview() {
    CharacterListItem(
        character = TVCharacter(
            "1",
            "Test",
            "https://cdn.britannica.com/77/170477-050-1C747EE3/Laptop-computer.jpg"
        ),
        onItemClick = {})
}
