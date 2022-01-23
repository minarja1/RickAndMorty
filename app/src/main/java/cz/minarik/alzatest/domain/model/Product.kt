package cz.minarik.alzatest.domain.model

data class Product(
    val id: Int, //123456
    val name: String? = null, // Pixel
    val imageUrl: String? = null, //https://odkaz na png nebo drawable z paměti zařízení
    val price: String? = null, // 1234 Kč
    val availability: String? = null, // Skladem > 5 ks
    val canBuy: Boolean = false, // True/false zobrazení tlačítka pro nákup
)