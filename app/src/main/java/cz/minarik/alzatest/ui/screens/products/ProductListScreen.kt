package cz.minarik.alzatest.ui.screens.products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import cz.minarik.alzatest.domain.model.Product
import cz.minarik.alzatest.navigation.Screen
import cz.minarik.alzatest.ui.composables.AlzaTopAppBar
import cz.minarik.alzatest.ui.composables.ErrorView
import cz.minarik.alzatest.ui.screens.products.components.ProductListItem
import cz.minarik.alzatest.ui.theme.AlzaTestTheme
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ProductList(navController: NavController, categoryId: String?, categoryName: String?) {
    val viewModel = getViewModel<ProductListScreenViewModel> {
        parametersOf(categoryId)
    }
    viewModel.state
    AlzaTestTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                AlzaTopAppBar(navController = navController, text = categoryName ?: "")
            },
            content = {
                HandleState(
                    viewModel.state.collectAsState(initial = ProductListScreenState()),
                    navController
                ) {
                    viewModel.getProducts()
                }
            }
        )
    }
}

@Composable
fun HandleState(state: State<ProductListScreenState>, navController: NavController, reload: () -> Unit) {
    state.value.apply {
        Box(modifier = Modifier.fillMaxSize()) {
            Products(products, isLoading, navController, reload)
            if (error.isNotBlank()) {
                ErrorView(error) {
                    reload.invoke()
                }
            }
        }
    }
}

@Composable
fun Products(categories: List<Product>, isLoading: Boolean, navController: NavController, reload: () -> Unit) {
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
                ProductListItem(category) {
                    navController.navigate(Screen.ProductList.withArgs(it.id.toString(), it.name ?: ""))
                }
            }
        }
    }

}
