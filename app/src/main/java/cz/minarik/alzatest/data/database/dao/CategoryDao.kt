package cz.minarik.alzatest.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import cz.minarik.alzatest.data.database.BaseDao
import cz.minarik.alzatest.data.database.entity.CharacterEntity

@Dao
interface CategoryDao : BaseDao<CharacterEntity> {

    @Query("SELECT * FROM CharacterEntity")
    suspend fun getAll(): List<CharacterEntity>

    @Query("DELETE FROM CharacterEntity")
    suspend fun delete()
}
