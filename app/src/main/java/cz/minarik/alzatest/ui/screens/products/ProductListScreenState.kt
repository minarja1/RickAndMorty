package cz.minarik.alzatest.ui.screens.products

import cz.minarik.alzatest.domain.model.Product

data class ProductListScreenState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val error: String = "",
)
