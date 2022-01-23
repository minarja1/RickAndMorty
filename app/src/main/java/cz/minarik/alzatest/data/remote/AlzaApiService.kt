package cz.minarik.alzatest.data.remote

import cz.minarik.alzatest.data.remote.request.ProductsRequest
import cz.minarik.alzatest.data.remote.response.CategoriesResponse
import cz.minarik.alzatest.data.remote.response.ProductsResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AlzaApiService {
    companion object {
        operator fun invoke(retrofit: Retrofit): AlzaApiService {
            return retrofit
                .create(AlzaApiService::class.java)
        }

    }

    @GET("v1/floors")
    suspend fun getCategories(): Response<CategoriesResponse>

    @POST("v2/products")
    suspend fun postProducts(
        @Body productsRequest: ProductsRequest
    ): Response<ProductsResponse>
}