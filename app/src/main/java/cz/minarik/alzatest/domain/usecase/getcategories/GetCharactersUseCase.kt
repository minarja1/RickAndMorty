package cz.minarik.alzatest.domain.usecase.getcategories

import cz.minarik.alzatest.common.base.FetchState
import cz.minarik.alzatest.common.base.Loading
import cz.minarik.alzatest.domain.model.Character
import cz.minarik.alzatest.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val repository: CharacterRepository,
) {

    operator fun invoke(): Flow<FetchState<List<Character>>> = flow {
        emit(Loading())
//        val categories = repository.getCharacters(nextPage)
//        emit(SuccessWithData(categories))
//    }.catch { e ->
//        emit(FailedWithError(e.message ?: GeneralApiException.generalMessage))
    }

}
