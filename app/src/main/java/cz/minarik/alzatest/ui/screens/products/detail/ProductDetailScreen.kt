package cz.minarik.alzatest.ui.screens.products.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import cz.minarik.alzatest.R
import cz.minarik.alzatest.common.util.decodeSafely
import cz.minarik.alzatest.domain.model.Product
import cz.minarik.alzatest.ui.composables.AlzaTopAppBar
import cz.minarik.alzatest.ui.composables.ErrorView
import cz.minarik.alzatest.ui.screens.products.list.fakeProducts
import cz.minarik.alzatest.ui.theme.AlzaTestTheme
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf


@Composable
fun ProductDetailScreen(navController: NavController, productId: String?, productName: String?) {
    val viewModel = getViewModel<ProductDetailScreenViewModel> {
        parametersOf(productId)
    }
    AlzaTestTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                AlzaTopAppBar(navController = navController, text = productName?.decodeSafely() ?: "")
            },
            content = {
                HandleState(
                    viewModel.state.collectAsState(initial = ProductDetailScreenState()),
                ) {
                    viewModel.getProductDetail()
                }
            }
        )
    }
}


@Composable
fun HandleState(state: State<ProductDetailScreenState>, reload: () -> Unit) {
    state.value.apply {
        Box(modifier = Modifier.fillMaxSize()) {
            product?.let { ProductDetail(product) }
            if (error.isNotBlank()) {
                ErrorView(error, true) {
                    reload.invoke()
                }
            }
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun ProductDetail(product: Product) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.height(164.dp),
            painter = rememberImagePainter(
                data = product.bigImageUrl ?: product.imageUrl,
            ),
            contentDescription = stringResource(id = R.string.product_image),
            contentScale = ContentScale.FillHeight
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            text = product.name ?: "",
            style = MaterialTheme.typography.h5,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            text = product.specification ?: "",
            style = MaterialTheme.typography.body1,
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ProductDetailPreview() {
    ProductDetail(product = fakeProducts.first())
}
