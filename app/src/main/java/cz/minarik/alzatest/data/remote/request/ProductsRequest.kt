package cz.minarik.alzatest.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class ProductsRequest(
    val filterParameters: FilterParameters,
)

@Serializable
data class FilterParameters(
    val id: Long,
    val params: List<FilterParameters> = emptyList(),
)
