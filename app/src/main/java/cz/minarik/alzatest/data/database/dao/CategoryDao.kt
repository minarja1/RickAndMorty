package cz.minarik.alzatest.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import cz.minarik.alzatest.data.database.BaseDao
import cz.minarik.alzatest.data.database.entity.CategoryEntity

@Dao
interface CategoryDao : BaseDao<CategoryEntity> {

    @Query("SELECT * FROM CategoryEntity")
    suspend fun getAll(): List<CategoryEntity>

    @Query("DELETE FROM CategoryEntity")
    suspend fun delete()
}
