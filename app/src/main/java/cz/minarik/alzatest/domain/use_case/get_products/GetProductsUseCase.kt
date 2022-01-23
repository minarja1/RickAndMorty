package cz.minarik.alzatest.domain.use_case.get_products

import cz.minarik.alzatest.data.remote.FailedWithError
import cz.minarik.alzatest.data.remote.FetchState
import cz.minarik.alzatest.data.remote.Loading
import cz.minarik.alzatest.data.remote.SuccessWithData
import cz.minarik.alzatest.data.remote.exception.GeneralApiException
import cz.minarik.alzatest.domain.model.Product
import cz.minarik.alzatest.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository,
) {
    operator fun invoke(categoryId: Long): Flow<FetchState<List<Product>>> = flow {
        emit(Loading())
        val categories = repository.getProducts(categoryId)
        emit(SuccessWithData(categories))
    }.catch { e ->
        emit(FailedWithError(e.message ?: GeneralApiException.generalMessage))
    }
}