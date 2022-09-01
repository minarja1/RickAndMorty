package cz.minarik.alzatest.data.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import cz.minarik.alzatest.GetCharactersQuery
import cz.minarik.alzatest.data.database.dao.CategoryDao
import cz.minarik.alzatest.domain.model.Character
import cz.minarik.alzatest.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val apolloClient: ApolloClient,
) : CharacterRepository {
    companion object {
        val TAG = CharacterRepositoryImpl::class.simpleName
    }

    override suspend fun getCharacters(): List<Character> {
        val page = 1
        val result = apolloClient.query(GetCharactersQuery(Optional.presentIfNotNull(page))).execute()
        return result.data?.characters?.results?.mapNotNull { character ->
            character?.id?.let {
                Character(
                    id = it,
                    name = character.name,
                    imageUrl = character.image
                )
            }
        }.orEmpty()
    }

//    private suspend fun updateDb(categories: List<Character>) {
//        Log.i(TAG, "Updating data in DB")
//        categoryDao.delete()
//        categoryDao.save(
//            categories.map {
//                it.toEntity()
//            }
//        )
//        DataStoreManager.setCategoriesLastFetchTime(System.currentTimeMillis())
//    }
//
//    private suspend fun shouldFetchRemote(): Boolean {
//        return try {
//            System.currentTimeMillis() - DataStoreManager.getCategoriesLastFetchTime()
//                .first() >= Constants.categoriesMinFetchGap
//        } catch (e: Exception) {
//            true
//        }
//    }
}