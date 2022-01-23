package cz.minarik.alzatest.ui.screens.products.list

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import cz.minarik.alzatest.common.Constants.UTF_8
import cz.minarik.alzatest.domain.model.Product
import cz.minarik.alzatest.navigation.Screen
import cz.minarik.alzatest.ui.composables.AlzaTopAppBar
import cz.minarik.alzatest.ui.composables.ErrorView
import cz.minarik.alzatest.ui.screens.products.list.components.ProductListItem
import cz.minarik.alzatest.ui.theme.AlzaTestTheme
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import java.net.URLDecoder
import java.net.URLEncoder

@Composable
fun ProductListScreen(navController: NavController, categoryId: String?, categoryName: String?) {
    val viewModel = getViewModel<ProductListScreenViewModel> {
        parametersOf(categoryId)
    }
    AlzaTestTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                AlzaTopAppBar(navController = navController, text = URLDecoder.decode(categoryName ?: "", UTF_8))
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
                ErrorView(error, products.isEmpty()) {
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
                    navController.navigate(
                        Screen.ProductDetail.withArgs(
                            it.id.toString(),
                            URLEncoder.encode(it.name ?: "", UTF_8)
                        )
                    )
                }
            }
        }
    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProductsPreview() {
    Products(fakeProducts, false, NavController(context = LocalContext.current)) {}
}

val fakeProducts = listOf(
    Product(
        id = 1,
        name = "iPhone 13 128GB růžová",
        price = "128 Kc",
        imageUrl = "https://cdn.britannica.com/77/170477-050-1C747EE3/Laptop-computer.jpg",
        specification = "Automatický kávovar tlak 15 bar,  cappuccino a latte, mlýnek na kávu, nastavení množství kávy, nastavení množství vody, parní tryska a příprava dvou šálků najednou,  objem nádržky na vodu 1,8 l,  velikost zásobníku mlýnku 250 g,  stupně hrubosti mletí 13,  příkon 1450 W,  šířka 23,8 cm,  výška 35,1 cm,  hloubka 43 cm,  hmotnost 9 kg",
    ),
    Product(
        id = 1,
        name = "iPhone 13 128GB růžová",
        price = "128 Kc",
        imageUrl = "https://cdn.britannica.com/77/170477-050-1C747EE3/Laptop-computer.jpg",
        specification = "Automatický kávovar tlak 15 bar,  cappuccino a latte, mlýnek na kávu, nastavení množství kávy, nastavení množství vody, parní tryska a příprava dvou šálků najednou,  objem nádržky na vodu 1,8 l,  velikost zásobníku mlýnku 250 g,  stupně hrubosti mletí 13,  příkon 1450 W,  šířka 23,8 cm,  výška 35,1 cm,  hloubka 43 cm,  hmotnost 9 kg",
    ),
    Product(
        id = 1,
        name = "iPhone 13 128GB růžová",
        price = "128 Kc",
        imageUrl = "https://cdn.britannica.com/77/170477-050-1C747EE3/Laptop-computer.jpg"
    ),
    Product(
        id = 1,
        name = "iPhone 13 128GB růžová",
        price = "128 Kc",
        imageUrl = "https://cdn.britannica.com/77/170477-050-1C747EE3/Laptop-computer.jpg"
    ),
)