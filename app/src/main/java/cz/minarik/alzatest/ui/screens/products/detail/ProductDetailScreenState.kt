package cz.minarik.alzatest.ui.screens.products.detail

import cz.minarik.alzatest.domain.model.Product

data class ProductDetailScreenState(
    val isLoading: Boolean = false,
    val product: Product? = null,
    val error: String = "",
)