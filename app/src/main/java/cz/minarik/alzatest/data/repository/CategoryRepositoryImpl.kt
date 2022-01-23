package cz.minarik.alzatest.data.repository

import cz.minarik.alzatest.data.remote.AlzaApiService
import cz.minarik.alzatest.data.remote.ApiRequest
import cz.minarik.alzatest.data.remote.response.toCategory
import cz.minarik.alzatest.domain.model.Category
import cz.minarik.alzatest.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val api: AlzaApiService
) : CategoryRepository {

    override suspend fun getCategories(): List<Category> {
        return ApiRequest.getResult { api.getCategories() }?.data?.map {
            it.toCategory()
        } ?: emptyList()
    }

}