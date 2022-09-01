package cz.minarik.alzatest.data.remote.response

import cz.minarik.alzatest.domain.model.Character
import java.io.Serializable

@kotlinx.serialization.Serializable
class CategoriesResponse(
    val data: List<CategoryResponse> = emptyList()
)

@kotlinx.serialization.Serializable
data class CategoryResponse(
    val id: Long? = null,
    val name: String? = null,
    val img: String? = null,
) : Serializable
//
//fun CategoryResponse.toCategory(): Character {
//    return Character(id, name, img)
//}