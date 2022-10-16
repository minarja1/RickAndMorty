package cz.minarik.alzatest.domain.usecase.getcharacterdetail

import cz.minarik.alzatest.common.base.FailedWithError
import cz.minarik.alzatest.common.base.FetchState
import cz.minarik.alzatest.common.base.Loading
import cz.minarik.alzatest.common.base.SuccessWithData
import cz.minarik.alzatest.data.remote.exception.GeneralApiException
import cz.minarik.alzatest.domain.model.CharacterDetail
import cz.minarik.alzatest.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCharacterDetailUseCase @Inject constructor(
    private val repository: CharacterRepository,
) {
    operator fun invoke(productId: String): Flow<FetchState<CharacterDetail?>> = flow {
        emit(Loading())
        val categories = repository.getCharacterDetail(productId)
        emit(SuccessWithData(categories))
    }.catch { e ->
        emit(FailedWithError(e.message ?: GeneralApiException.generalMessage))
    }
}
