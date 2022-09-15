package cz.minarik.alzatest.ui.screens.home

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import cz.minarik.alzatest.common.base.BaseViewModel
import cz.minarik.alzatest.data.remote.paging.CharacterPagingSource
import cz.minarik.alzatest.domain.model.Character
import cz.minarik.alzatest.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

class HomeScreenViewModel(
    private val characterRepository: CharacterRepository,
) : BaseViewModel() {
    val pagedCharacters: Flow<PagingData<Character>> = Pager(PagingConfig(pageSize = 20)) {
        CharacterPagingSource(characterRepository)
    }.flow.cachedIn(viewModelScope)
}
