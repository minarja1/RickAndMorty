package cz.minarik.alzatest.domain.use_case.get_categories

import cz.minarik.alzatest.common.base.FailedWithError
import cz.minarik.alzatest.common.base.FetchState
import cz.minarik.alzatest.common.base.Loading
import cz.minarik.alzatest.common.base.SuccessWithData
import cz.minarik.alzatest.data.remote.exception.GeneralApiException
import cz.minarik.alzatest.domain.model.Character
import cz.minarik.alzatest.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CharacterRepository,
) {

    operator fun invoke(): Flow<FetchState<List<Character>>> = flow {
        emit(Loading())
        val categories = repository.getCharacters()
        emit(SuccessWithData(categories))
    }.catch { e ->
        emit(FailedWithError(e.message ?: GeneralApiException.generalMessage))
    }

}