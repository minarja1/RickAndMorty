package cz.minarik.rickandmorty.ui.screens.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cz.minarik.rickandmorty.ui.components.CharactersRow
import cz.minarik.rickandmorty.ui.core.composable.ComponentPreview
import cz.minarik.rickandmorty.ui.core.composable.PreviewSurface
import cz.minarik.rickandmorty.ui.dimens.SpacingXSmall
import cz.minarik.rickandmorty.ui.model.ClickableCardViewObject

@Composable
fun ClickableCard(
    modifier: Modifier = Modifier,
    clickableCardViewObject: ClickableCardViewObject,
    onItemClick: (String) -> Unit,
) {
    val roundedCornerShape = RoundedCornerShape(8.dp)
    Card(
        shape = roundedCornerShape,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colors.onBackground
        ),
        modifier = modifier
            .clip(roundedCornerShape)
            .clickable { onItemClick(clickableCardViewObject.id) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            clickableCardViewObject.title?.let { name ->
                Text(
                    modifier = Modifier.padding(SpacingXSmall),
                    text = name,
                    style = MaterialTheme.typography.h6,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            }
            clickableCardViewObject.subtitle?.let { code ->
                Text(
                    modifier = Modifier.padding(SpacingXSmall),
                    text = code,
                    style = MaterialTheme.typography.body2,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            }
            CharactersRow(
                characters = clickableCardViewObject.characters,
            )
        }
    }
}

@ComponentPreview
@Composable
private fun EpisodeListItemPreview() {
    PreviewSurface {
        ClickableCard(
            clickableCardViewObject = ClickableCardViewObject(
                id = "1",
                title = "December 2, 2013",
                subtitle = "S01E01",
                characters = emptyList(),
            ),
            onItemClick = {}
        )
    }
}
