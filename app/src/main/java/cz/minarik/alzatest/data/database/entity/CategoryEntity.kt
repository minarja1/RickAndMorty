package cz.minarik.alzatest.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.minarik.alzatest.domain.model.Category

@Entity
data class CategoryEntity(
    @PrimaryKey
    val id: Long?,

    val name: String?,
    val imageUrl: String?,
)

fun CategoryEntity.toCategory(): Category {
    return Category(id, name, imageUrl)
}