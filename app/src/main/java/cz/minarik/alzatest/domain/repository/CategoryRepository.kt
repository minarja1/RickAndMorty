package cz.minarik.alzatest.domain.repository

import cz.minarik.alzatest.domain.model.Category

interface CategoryRepository {

    suspend fun getCategories(): List<Category>

}