package cz.minarik.alzatest.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import cz.minarik.alzatest.R
import cz.minarik.alzatest.domain.model.Category
import cz.minarik.alzatest.navigation.Screen
import cz.minarik.alzatest.ui.composables.AlzaTopAppBar
import cz.minarik.alzatest.ui.composables.ErrorView
import cz.minarik.alzatest.ui.screens.home.components.CategoryListItem
import cz.minarik.alzatest.ui.theme.AlzaTestTheme
import org.koin.androidx.compose.getViewModel


@Composable
fun HomeScreen(navController: NavController) {
    val viewModel = getViewModel<HomeScreenViewModel>()
    AlzaTestTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                AlzaTopAppBar(navController = navController, text = stringResource(id = R.string.categories))
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
            Categories(categories, isLoading, navController, reload)
            if (error.isNotBlank()) {
                ErrorView(error) {
                    reload.invoke()
                }
            }
        }
    }
}

@Composable
fun Categories(categories: List<Category>, isLoading: Boolean, navController: NavController, reload: () -> Unit) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(isLoading),
        onRefresh = { reload.invoke() },
    ) {
        LazyColumn(
            Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
        ) {
            items(categories) { category ->
                CategoryListItem(category) {
                    navController.navigate(Screen.ProductList.withArgs(it.id.toString(), it.name ?: ""))
                }
            }
        }
    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CategoriesPreview() {
    Categories(
        listOf(
            Category(1, "Computers and Laptops", "https://cdn.britannica.com/77/170477-050-1C747EE3/Laptop-computer.jpg"),
            Category(1, "Network Components", "https://cdn.britannica.com/77/170477-050-1C747EE3/Laptop-computer.jpg"),
            Category(1, "Software", "https://cdn.britannica.com/77/170477-050-1C747EE3/Laptop-computer.jpg"),
        ), false, NavController(context = LocalContext.current)
    ) {}
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ErrorViewPreview() {
    ErrorView(error = "Preview error") {}
}