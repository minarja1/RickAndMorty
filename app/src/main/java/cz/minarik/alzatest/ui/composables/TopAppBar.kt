package cz.minarik.alzatest.ui.composables

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import cz.minarik.alzatest.R

@Composable
fun AlzaTopAppBar(navController: NavController, text: String? = null) {
    TopAppBar(
        title = {
            Text(
                text = text ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        navigationIcon = if (navController.previousBackStackEntry != null) {
            {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            }
        } else {
            null
        }
    )
}

@Preview
@Composable
fun AlzaTopAppBarPreview() {
    AlzaTopAppBar(NavController(context = LocalContext.current), "Top App Bar")
}