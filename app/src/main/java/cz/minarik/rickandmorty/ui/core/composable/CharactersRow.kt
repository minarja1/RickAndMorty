package cz.minarik.rickandmorty.ui.core.composable

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import cz.minarik.rickandmorty.navigation.CharacterId
import cz.minarik.rickandmorty.navigation.CharacterImageUrl
import cz.minarik.rickandmorty.navigation.CharacterName
import cz.minarik.rickandmorty.ui.model.TVCharacterVo
import cz.minarik.rickandmorty.ui.screens.home.components.CharacterListItem
import cz.minarik.rickandmorty.ui.theme.SpacingXXSmall

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CharactersRow(
    characters: List<TVCharacterVo>,
    index: Int,
    columns: Int,
    onDetailClicked: (CharacterId, CharacterImageUrl?, CharacterName?) -> Unit,
    startingCharacter: TVCharacterVo?,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
) {
    if (index % columns == 0) {
        key(startingCharacter?.id) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                for (i in 0 until columns) {
                    val character = characters.getOrNull(index + i)
                    if (character != null) {
                        CharacterListItem(
                            modifier = Modifier
                                .weight(1f)
                                .padding(SpacingXXSmall),
                            character = character,
                            onItemClick = { onDetailClicked(it.id, it.imageUrl, it.name) },
                            sharedTransitionScope = sharedTransitionScope,
                            animatedContentScope = animatedContentScope,
                        )
                    } else {
                        // invisible placeholders to fill empty space until end of row
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .alpha(0f)
                                .padding(SpacingXXSmall),
                        )
                    }
                }
            }
        }
    }
}
