package cz.minarik.alzatest.domain.repository

import cz.minarik.alzatest.domain.model.Product

interface ProductRepository {

    suspend fun getProducts(categoryId: Long): List<Product>

}