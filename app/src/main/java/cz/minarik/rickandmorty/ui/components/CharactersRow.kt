package cz.minarik.rickandmorty.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import cz.minarik.rickandmorty.R
import cz.minarik.rickandmorty.ui.dimens.SpacingXSmall

@Composable
fun CharactersRow(
    characters: List<cz.minarik.rickandmorty.domain.model.TVCharacter>,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier.fillMaxWidth()
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

private val CharacterImageSize = 72.dp
