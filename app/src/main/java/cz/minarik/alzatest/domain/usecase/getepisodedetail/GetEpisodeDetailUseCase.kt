package cz.minarik.alzatest.domain.usecase.getepisodedetail

import cz.minarik.alzatest.common.base.FailedWithError
import cz.minarik.alzatest.common.base.FetchState
import cz.minarik.alzatest.common.base.Loading
import cz.minarik.alzatest.common.base.SuccessWithData
import cz.minarik.alzatest.data.remote.exception.GeneralApiException
import cz.minarik.alzatest.domain.model.EpisodeDetail
import cz.minarik.alzatest.domain.repository.EpisodeRepository
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
