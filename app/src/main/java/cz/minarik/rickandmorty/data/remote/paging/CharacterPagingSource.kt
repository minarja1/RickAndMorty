package cz.minarik.rickandmorty.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import cz.minarik.rickandmorty.domain.model.TVCharacter
import cz.minarik.rickandmorty.domain.repository.CharacterRepository

/**
 * Paging source for characters.
 *
 * @param characterRepository character repository.
 */
@Suppress("TooGenericExceptionCaught")
class CharacterPagingSource(
    private val characterRepository: CharacterRepository,
) : PagingSource<Int, TVCharacter>() {

    override fun getRefreshKey(state: PagingState<Int, TVCharacter>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TVCharacter> {
        return try {
            val nextPage = params.key ?: 1
            val charactersResponse = characterRepository.getCharacters(nextPage)
            LoadResult.Page(
                data = charactersResponse.characters,
                prevKey = charactersResponse.info?.prev,
                nextKey = charactersResponse.info?.next,
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}
