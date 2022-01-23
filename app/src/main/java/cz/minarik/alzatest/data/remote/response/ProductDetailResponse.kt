package cz.minarik.alzatest.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class ProductDetailResponse(
    val data: ProductResponse? = null
)
