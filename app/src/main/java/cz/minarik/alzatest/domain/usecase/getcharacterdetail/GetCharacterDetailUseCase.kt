package cz.minarik.alzatest.domain.usecase.getcharacterdetail

import cz.minarik.alzatest.common.base.FailedWithError
import cz.minarik.alzatest.common.base.FetchState
import cz.minarik.alzatest.common.base.Loading
import cz.minarik.alzatest.common.base.SuccessWithData
import cz.minarik.alzatest.data.remote.exception.GeneralApiException
import cz.minarik.alzatest.domain.model.CharacterDetail
import cz.minarik.alzatest.domain.repository.CharacterRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Use case for getting character detail.
 *
 * @property repository character repository
 */
class GetCharacterDetailUseCase @Inject constructor(
    private val repository: CharacterRepository,
) {

    /**
     * Get character detail.
     *
     * @param characterId character id
     * @return character detail
     */
    operator fun invoke(characterId: String): Flow<FetchState<CharacterDetail?>> = flow {
        emit(Loading())
        val characterDetail = repository.getCharacterDetail(characterId)
        emit(SuccessWithData(characterDetail))
    }.catch { e ->
        emit(FailedWithError(e.message ?: GeneralApiException.generalMessage))
    }
}
