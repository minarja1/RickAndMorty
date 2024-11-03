package cz.minarik.rickandmorty.ui.screens.home.components

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import cz.minarik.rickandmorty.R
import cz.minarik.rickandmorty.ui.core.composable.ComponentPreview
import cz.minarik.rickandmorty.ui.core.composable.PreviewSurface
import cz.minarik.rickandmorty.ui.model.TVCharacterVo
import cz.minarik.rickandmorty.ui.screens.home.util.MockData
import cz.minarik.rickandmorty.ui.theme.SpacingSmall
import cz.minarik.rickandmorty.ui.theme.SpacingXSmall
import cz.minarik.rickandmorty.ui.theme.SpacingXXSmall

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CharacterListItem(
    modifier: Modifier = Modifier,
    character: TVCharacterVo,
    onItemClick: (TVCharacterVo) -> Unit,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
) {
    val roundedCornerShape = RoundedCornerShape(8.dp)

    val sharedTransitionModifierImage =
        if (sharedTransitionScope == null || animatedContentScope == null) {
            Modifier
        } else {
            with(sharedTransitionScope) {
                Modifier.sharedBounds(
                    rememberSharedContentState(key = "${character.imageUrl}"),
                    animatedVisibilityScope = animatedContentScope,
                    enter = fadeIn(),
                    exit = fadeOut(),
                    resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds()
                )
            }
        }

    Card(
        shape = roundedCornerShape,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onBackground
        ),
        modifier = modifier
            .clip(roundedCornerShape)
            .then(sharedTransitionModifierImage)
            .clickable { onItemClick(character) }
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
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
                val sharedTransitionModifierName =
                    if (sharedTransitionScope == null || animatedContentScope == null) {
                        Modifier
                    } else {
                        with(sharedTransitionScope) {
                            Modifier.sharedBounds(
                                rememberSharedContentState(key = "${character.id} + ${character.name}"),
                                animatedVisibilityScope = animatedContentScope,
                                enter = fadeIn(),
                                exit = fadeOut(),
                                resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds
                            )
                        }
                    }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    MaterialTheme.colorScheme.background
                                )
                            )
                        )
                        .then(sharedTransitionModifierName),
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = SpacingXSmall)
                            .padding(bottom = SpacingXXSmall)
                            .padding(top = SpacingSmall)
                            .align(Alignment.BottomCenter),
                        text = character.name,
                        style = MaterialTheme.typography.bodyLarge,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                }
            }
        }
    }
}

private val CharacterImageSize = 148.dp

@OptIn(ExperimentalSharedTransitionApi::class)
@ComponentPreview
@Composable
private fun CharacterListItemPreview() {
    PreviewSurface {
        CharacterListItem(
            character = MockData.characters.first(),
            onItemClick = {},
        )
    }
}
