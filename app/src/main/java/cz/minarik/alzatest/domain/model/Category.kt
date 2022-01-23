package cz.minarik.alzatest.domain.model

import cz.minarik.alzatest.data.database.entity.CategoryEntity

data class Category(
    val id: Long?,
    val name: String?,
    val imageUrl: String?,
)

fun Category.toEntity(): CategoryEntity {
    return CategoryEntity(id, name, imageUrl)
}