package cz.minarik.alzatest.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo.exception.ApolloException
import cz.minarik.alzatest.domain.model.Character
import cz.minarik.alzatest.domain.repository.CharacterRepository

class CharacterPagingSource(
    private val characterRepository: CharacterRepository,
) : PagingSource<Int, Character>() {

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val nextPage = params.key ?: 1
            val charactersResponse = characterRepository.getCharacters(nextPage)
            LoadResult.Page(
                data = charactersResponse.characters,
                prevKey = charactersResponse.info?.prev,
                nextKey = charactersResponse.info?.next,
            )
        } catch (exception: ApolloException) {
            return LoadResult.Error(exception)
        }
    }
}
