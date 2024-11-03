package cz.minarik.rickandmorty.ui.core.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import cz.minarik.rickandmorty.R

/**
 * Rick and Morty Top App Bar.
 *
 * @param onBackClicked Callback for back button click.
 * @param text Title text.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RaMTopAppBar(
    modifier: Modifier = Modifier,
    onBackClicked: (() -> Unit)? = null,
    text: String? = null,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = text ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onBackground,
            )
        },
        navigationIcon =
        {
            if (onBackClicked != null) {
                IconButton(onClick = onBackClicked) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    )
}

@ComponentPreview
@Composable
fun RaMTopAppBarPreview() {
    PreviewSurface {
        RaMTopAppBar(text = "Top App Bar")
    }
}
