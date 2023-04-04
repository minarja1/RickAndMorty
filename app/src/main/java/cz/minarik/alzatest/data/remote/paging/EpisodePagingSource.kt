package cz.minarik.alzatest.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import cz.minarik.alzatest.domain.model.Episode
import cz.minarik.alzatest.domain.repository.EpisodeRepository

/**
 * Paging source for episodes.
 *
 * @param episodeRepository episode repository.
 */
@Suppress("TooGenericExceptionCaught")
class EpisodePagingSource(
    private val episodeRepository: EpisodeRepository,
) : PagingSource<Int, Episode>() {

    override fun getRefreshKey(state: PagingState<Int, Episode>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Episode> {
        return try {
            val nextPage = params.key ?: 1
            val charactersResponse = episodeRepository.getEpisodes(nextPage)
            LoadResult.Page(
                data = charactersResponse.episodes,
                prevKey = charactersResponse.info?.prev,
                nextKey = charactersResponse.info?.next,
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}
