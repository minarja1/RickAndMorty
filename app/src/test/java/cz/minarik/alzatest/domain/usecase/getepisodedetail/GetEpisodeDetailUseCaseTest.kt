package cz.minarik.alzatest.domain.usecase.getepisodedetail

import cz.minarik.alzatest.common.base.FailedWithError
import cz.minarik.alzatest.common.base.Loading
import cz.minarik.alzatest.common.base.SuccessWithData
import cz.minarik.alzatest.data.remote.exception.GeneralApiException
import cz.minarik.alzatest.domain.model.EpisodeDetail
import cz.minarik.alzatest.domain.repository.EpisodeRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList

/**
 * Tests for [GetEpisodeDetailUseCase].
 */
class GetEpisodeDetailUseCaseTest : StringSpec({

    val repository = mockk<EpisodeRepository>()
    val useCase = GetEpisodeDetailUseCase(repository)
    val episodeId = "1"

    "GetEpisodeDetailUseCase should return SuccessWithData when episode detail is retrieved successfully" {
        val expectedEpisodeDetail = EpisodeDetail(
            id = episodeId,
            name = "Pilot",
            airDate = "2013-12-02",
        )

        coEvery { repository.getEpisodeDetail(episodeId) } returns expectedEpisodeDetail

        val result = useCase(episodeId).toList()

        result.first().shouldBeInstanceOf<Loading<*>>()
        result.last() shouldBe SuccessWithData(expectedEpisodeDetail)
    }

    "should emit Loading and FailedWithError when repository throws an exception" {
        val expectedErrorMessage = "Error message"
        coEvery { repository.getEpisodeDetail(episodeId) } throws RuntimeException(
            expectedErrorMessage
        )

        val result = useCase(episodeId).toList()

        result.first().shouldBeInstanceOf<Loading<*>>()
        result.last().shouldBeInstanceOf<FailedWithError<GeneralApiException>>()
        (result.last() as FailedWithError<*>).error shouldBe expectedErrorMessage
    }
})

