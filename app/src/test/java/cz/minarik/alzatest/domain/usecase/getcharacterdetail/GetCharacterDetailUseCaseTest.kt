package cz.minarik.alzatest.domain.usecase.getcharacterdetail

import cz.minarik.alzatest.common.base.FailedWithError
import cz.minarik.alzatest.common.base.Loading
import cz.minarik.alzatest.common.base.SuccessWithData
import cz.minarik.alzatest.data.remote.exception.GeneralApiException
import cz.minarik.alzatest.domain.model.CharacterDetail
import cz.minarik.alzatest.domain.model.Episode
import cz.minarik.alzatest.domain.model.Location
import cz.minarik.alzatest.domain.repository.CharacterRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList

/**
 * Tests for [GetCharacterDetailUseCase].
 */
class GetCharacterDetailUseCaseTest : StringSpec({

    val repository = mockk<CharacterRepository>()
    val characterId = "1"
    val useCase = GetCharacterDetailUseCase(repository)

    "should emit Loading and SuccessWithData when repository returns data" {
        val characterDetail = CharacterDetail(
            id = characterId,
            name = "John Doe",
            imageUrl = "https://example.com/image.png",
            species = "Human",
            type = "Unknown",
            status = "Alive",
            gender = "Male",
            origin = Location("Earth", "1"),
            location = Location("Mars", "2"),
            episodes = listOf(
                Episode("S01E01", "Pilot"),
                Episode("S01E02", "The One with the Sonogram at the End"),
            )
        )

        coEvery { repository.getCharacterDetail(characterId) } returns characterDetail

        val result = useCase(characterId).toList()

        result.first().shouldBeInstanceOf<Loading<*>>()
        result.last() shouldBe SuccessWithData(characterDetail)
    }

    "should emit Loading and FailedWithError when repository throws an exception" {
        coEvery { repository.getCharacterDetail(characterId) } throws GeneralApiException()

        val result = useCase(characterId).toList()

        result.first().shouldBeInstanceOf<Loading<*>>()
        result.last().shouldBeInstanceOf<FailedWithError<GeneralApiException>>()
        (result.last() as FailedWithError<*>).error shouldBe GeneralApiException.generalMessage
    }
})
