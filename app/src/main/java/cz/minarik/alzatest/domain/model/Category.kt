package cz.minarik.alzatest.domain.model

import cz.minarik.alzatest.data.remote.response.CategoryResponse

data class Category(
    val id: Long?,
    val name: String?,
    val imageUrl: String?,
)