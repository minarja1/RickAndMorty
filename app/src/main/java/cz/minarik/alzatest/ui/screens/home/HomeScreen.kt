package cz.minarik.alzatest.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import cz.minarik.alzatest.R
import cz.minarik.alzatest.common.Constants.UTF_8
import cz.minarik.alzatest.domain.model.Character
import cz.minarik.alzatest.navigation.Screen
import cz.minarik.alzatest.ui.composables.AlzaTopAppBar
import cz.minarik.alzatest.ui.composables.ErrorView
import cz.minarik.alzatest.ui.dimens.SpacingXXSmall
import cz.minarik.alzatest.ui.screens.home.components.CharacterListItem
import cz.minarik.alzatest.ui.screens.home.util.CharacterItemUtils.getListColumnsCount
import cz.minarik.alzatest.ui.theme.AlzaTestTheme
import org.koin.androidx.compose.getViewModel
import java.net.URLEncoder


@Composable
fun HomeScreen(navController: NavController) {
    val viewModel = getViewModel<HomeScreenViewModel>()
    AlzaTestTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                AlzaTopAppBar(navController = navController, text = stringResource(id = R.string.characters))
            },
            content = {
                HandleState(viewModel.state.collectAsState(initial = HomeScreenState()), navController) {
                    viewModel.getCategories()
                }
            }
        )
    }
}

@Composable
fun HandleState(state: State<HomeScreenState>, navController: NavController, reload: () -> Unit) {
    state.value.apply {
        Box(modifier = Modifier.fillMaxSize()) {
            Characters(characters, isLoading, navController, reload)
            if (error.isNotBlank()) {
                ErrorView(error, characters.isEmpty()) {
                    reload.invoke()
                }
            }
        }
    }
}

@Composable
fun Characters(characters: List<Character>, isLoading: Boolean, navController: NavController, reload: () -> Unit) {
    BoxWithConstraints {
        val screenWidth = maxWidth
        val columns = remember(maxWidth) {
            getListColumnsCount(screenWidth)
        }

        SwipeRefresh(
            state = rememberSwipeRefreshState(isLoading),
            onRefresh = { reload.invoke() },
        ) {
            LazyColumn(
                Modifier
                    .background(MaterialTheme.colors.background)
                    .fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                itemsIndexed(characters) { index, characterInList ->
                    if (index % columns == 0) {
                        key(characterInList.id) {
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
                                            character
                                        ) {
                                            navController.navigate(
                                                Screen.ProductList.withArgs(
                                                    it.id,
                                                    URLEncoder.encode(it.name ?: "", UTF_8)
                                                )
                                            )
                                        }
                                    } else {
                                        // todo placeholder
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CharactersPreview() {
    Characters(
        listOf(
            Character("1", "Computers and Laptops", "https://cdn.britannica.com/77/170477-050-1C747EE3/Laptop-computer.jpg"),
            Character("1", "Network Components", "https://cdn.britannica.com/77/170477-050-1C747EE3/Laptop-computer.jpg"),
            Character("1", "Software", "https://cdn.britannica.com/77/170477-050-1C747EE3/Laptop-computer.jpg"),
        ), false, NavController(context = LocalContext.current)
    ) {}
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ErrorViewPreview() {
    ErrorView(error = "Preview error", true) {}
}