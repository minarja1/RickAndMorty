package cz.minarik.rickandmorty.domain.usecase.getepisodedetail

import cz.minarik.rickandmorty.common.base.FailedWithError
import cz.minarik.rickandmorty.common.base.FetchState
import cz.minarik.rickandmorty.common.base.Loading
import cz.minarik.rickandmorty.common.base.SuccessWithData
import cz.minarik.rickandmorty.data.remote.exception.GeneralApiException
import cz.minarik.rickandmorty.domain.model.EpisodeDetail
import cz.minarik.rickandmorty.domain.repository.EpisodeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Use case for getting episode detail.
 *
 * @property repository episode repository
 */
class GetEpisodeDetailUseCase @Inject constructor(
    private val repository: EpisodeRepository,
) {

    /**
     * Get episode detail.
     *
     * @param episodeId episode id
     * @return episode detail
     */
    operator fun invoke(episodeId: String): Flow<FetchState<EpisodeDetail?>> = flow {
        emit(Loading())
        val episodeDetail = repository.getEpisodeDetail(episodeId)
        emit(SuccessWithData(episodeDetail))
    }.catch { e ->
        emit(FailedWithError(e.message ?: GeneralApiException.generalMessage))
    }
}
