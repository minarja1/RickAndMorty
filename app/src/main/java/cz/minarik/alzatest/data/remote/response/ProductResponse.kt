package cz.minarik.alzatest.data.remote.response

import cz.minarik.alzatest.domain.model.Product
import java.io.Serializable

@kotlinx.serialization.Serializable
data class ProductsResponse(
    val data: List<ProductResponse> = emptyList()
)

@kotlinx.serialization.Serializable
data class ProductResponse(
    val id: Int, //123456
    val name: String? = null, // Pixel
    val img: String? = null, //https://odkaz na png nebo drawable z paměti zařízení
    val price: String? = null, // 1234 Kč
    val availability: String? = null, // Skladem > 5 ks
    val canBuy: Boolean = false, // True/false zobrazení tlačítka pro nákup
) : Serializable

fun ProductResponse.toProduct(): Product {
    return Product(
        id,
        name,
        img,
        price,
        availability,
        canBuy,
    )
}