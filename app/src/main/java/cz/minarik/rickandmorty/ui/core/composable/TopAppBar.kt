package cz.minarik.rickandmorty.ui.core.composable

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import cz.minarik.rickandmorty.R

/**
 * Rick and Morty Top App Bar.
 *
 * @param onBackClicked Callback for back button click.
 * @param text Title text.
 */
@Composable
fun RaMTopAppBar(
    onBackClicked: (() -> Unit)? = null,
    text: String? = null
) {
    TopAppBar(
        title = {
            Text(
                text = text ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.onBackground,
            )
        },
        navigationIcon = if (onBackClicked != null) {
            {
                IconButton(onClick = onBackClicked) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back),
                        tint = MaterialTheme.colors.onBackground
                    )
                }
            }
        } else {
            null
        }
    )
}

@ComponentPreview
@Composable
fun RaMTopAppBarPreview() {
    PreviewSurface {
        RaMTopAppBar({}, "Top App Bar")
    }
}
