package cz.minarik.rickandmorty.data.remote.paging

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import cz.minarik.rickandmorty.data.remote.response.CharactersResponse
import cz.minarik.rickandmorty.data.remote.response.InfoResponse
import cz.minarik.rickandmorty.domain.model.TVCharacter
import cz.minarik.rickandmorty.domain.repository.CharacterRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk

/**
 * Tests for [CharacterPagingSource].
 */
class CharacterPagingSourceTest : StringSpec({
    val mockCharacterRepository = mockk<CharacterRepository>()
    val pagingSource = CharacterPagingSource(mockCharacterRepository)

    "getRefreshKey should return anchorPosition" {
        val state = PagingState<Int, TVCharacter>(
            pages = emptyList(),
            anchorPosition = 5,
            config = PagingConfig(pageSize = 20),
            leadingPlaceholderCount = 0,
        )

        pagingSource.getRefreshKey(state) shouldBe 5
    }

    "load should return LoadResult.Page with correct data" {
        val nextPage = 1
        val prevPage = 0
        val characters = listOf(
            TVCharacter(
                id = "1",
                name = "Character1",
                imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            ),
            TVCharacter(
                id = "2",
                name = "Character2j",
                imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            ),
        )
        val info = InfoResponse(prev = prevPage, next = nextPage, count = 2, pages = 3)
        val charactersResponse = CharactersResponse(characters, info)

        coEvery { mockCharacterRepository.getCharacters(nextPage) } returns charactersResponse

        val loadResult = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = nextPage,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        loadResult.shouldBeInstanceOf<PagingSource.LoadResult.Page<Int, TVCharacter>>().apply {
            data.shouldContainExactly(characters)
            prevKey.shouldNotBeNull().apply { this shouldBe prevPage }
            nextKey.shouldNotBeNull().apply { this shouldBe nextPage }
        }
    }

    "load should return LoadResult.Error on exception" {
        val nextPage = 1
        val exception = Exception("Error loading characters")

        coEvery { mockCharacterRepository.getCharacters(nextPage) } throws exception

        val loadResult = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = nextPage,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        loadResult.shouldBeInstanceOf<PagingSource.LoadResult.Error<Int, TVCharacter>>().apply {
            this.throwable shouldBe exception
        }
    }
})
