package cz.minarik.alzatest.data.remote.paging
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import cz.minarik.alzatest.data.remote.response.EpisodesResponse
import cz.minarik.alzatest.data.remote.response.InfoResponse
import cz.minarik.alzatest.domain.model.Episode
import cz.minarik.alzatest.domain.repository.EpisodeRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk

/**
 * Tests for [EpisodePagingSource].
 */
class EpisodePagingSourceTest : StringSpec({
    val mockEpisodeRepository = mockk<EpisodeRepository>()
    val pagingSource = EpisodePagingSource(mockEpisodeRepository)

    "getRefreshKey should return anchorPosition" {
        val state = PagingState<Int, Episode>(
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
        val episodes = listOf(
            Episode(
                id = "1",
                name = "Episode1",
            ),
            Episode(
                id = "2",
                name = "Episode2",
            ),
        )
        val info = InfoResponse(prev = prevPage, next = nextPage, count = 2, pages = 3)
        val episodesResponse = EpisodesResponse(episodes, info)

        coEvery { mockEpisodeRepository.getEpisodes(nextPage) } returns episodesResponse

        val loadResult = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = nextPage,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        loadResult.shouldBeInstanceOf<PagingSource.LoadResult.Page<Int, Episode>>().apply {
            data.shouldContainExactly(episodes)
            prevKey.shouldNotBeNull().apply { this shouldBe prevPage }
            nextKey.shouldNotBeNull().apply { this shouldBe nextPage }
        }
    }

    "load should return LoadResult.Error on exception" {
        val nextPage = 1
        val exception = Exception("Error loading episodes")

        coEvery { mockEpisodeRepository.getEpisodes(nextPage) } throws exception

        val loadResult = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = nextPage,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        loadResult.shouldBeInstanceOf<PagingSource.LoadResult.Error<Int, Episode>>().apply {
            this.throwable shouldBe exception
        }
    }
})
