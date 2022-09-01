package cz.minarik.alzatest.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.minarik.alzatest.domain.model.Character

@Entity
data class CharacterEntity(
    @PrimaryKey
    val id: Long?,

    val name: String?,
    val imageUrl: String?,
)
//
//fun CharacterEntity.toCategory(): Character {
//    return Character(id, name, imageUrl)
//}