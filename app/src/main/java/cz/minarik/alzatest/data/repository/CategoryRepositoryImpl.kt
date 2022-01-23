package cz.minarik.alzatest.data.repository

import android.util.Log
import cz.minarik.alzatest.common.Constants
import cz.minarik.alzatest.common.util.DataStoreManager
import cz.minarik.alzatest.data.database.dao.CategoryDao
import cz.minarik.alzatest.data.database.entity.toCategory
import cz.minarik.alzatest.data.remote.AlzaApiService
import cz.minarik.alzatest.data.remote.ApiRequest
import cz.minarik.alzatest.data.remote.response.toCategory
import cz.minarik.alzatest.domain.model.Category
import cz.minarik.alzatest.domain.model.toEntity
import cz.minarik.alzatest.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val api: AlzaApiService,
    private val categoryDao: CategoryDao,
) : CategoryRepository {
    companion object {
        val TAG = CategoryRepositoryImpl::class.simpleName
    }

    override suspend fun getCategories(): List<Category> {
        return if (shouldFetchRemote()) {
            val fromServer = ApiRequest.getResult { api.getCategories() }?.data?.map {
                it.toCategory()
            }
            fromServer?.let {
                updateDb(it)
            }
            Log.i(TAG, "Returning data from server")
            fromServer ?: emptyList()
        } else {
            Log.i(TAG, "Returning data from DB")
            categoryDao.getAll().map {
                it.toCategory()
            }
        }
    }

    private suspend fun updateDb(categories: List<Category>) {
        Log.i(TAG, "Updating data in DB")
        categoryDao.delete()
        categoryDao.save(
            categories.map {
                it.toEntity()
            }
        )
        DataStoreManager.setCategoriesLastFetchTime(System.currentTimeMillis())
    }

    private suspend fun shouldFetchRemote(): Boolean {
        return try {
            System.currentTimeMillis() - DataStoreManager.getCategoriesLastFetchTime()
                .first() >= Constants.categoriesMinFetchGap
        } catch (e: Exception) {
            true
        }
    }
}