package cz.minarik.alzatest.data.database

import androidx.room.*

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(entity: T): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(entity: List<T>): List<Long>

    @Update
    suspend fun update(entity: T)

    @Delete
    suspend fun delete(entity: T)

    @Delete
    suspend fun delete(entity: List<T>)

}