package cz.minarik.alzatest.data.repository

import cz.minarik.alzatest.data.remote.AlzaApiService
import cz.minarik.alzatest.data.remote.ApiRequest
import cz.minarik.alzatest.data.remote.request.FilterParameters
import cz.minarik.alzatest.data.remote.request.ProductsRequest
import cz.minarik.alzatest.data.remote.response.toProduct
import cz.minarik.alzatest.domain.model.Product
import cz.minarik.alzatest.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: AlzaApiService
) : ProductRepository {


    override suspend fun getProducts(categoryId: Long): List<Product> {
        return ApiRequest.getResult {
            api.postProducts(
                ProductsRequest(
                    filterParameters = FilterParameters(categoryId)
                )
            )
        }?.data?.map {
            it.toProduct()
        } ?: emptyList()
    }

    override suspend fun getProductDetail(productId: Long): Product? {
        return ApiRequest.getResult {
            api.getProductDetail(
                productId
            )
        }?.data?.toProduct()
    }

}