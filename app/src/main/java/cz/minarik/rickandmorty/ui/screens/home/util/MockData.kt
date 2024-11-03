package cz.minarik.rickandmorty.ui.screens.home.util

import cz.minarik.rickandmorty.ui.model.CircleImageVo
import cz.minarik.rickandmorty.ui.model.ClickableCardVo
import cz.minarik.rickandmorty.ui.model.TVCharacterVo

/**
 * Mock data for testing/preview purposes.
 */
object MockData {
    val characters = listOf(
        TVCharacterVo(
            "1",
            "Rick Sanchez",
            "https://cdn.britannica.com/77/170477-050-1C747EE3/Laptop-computer.jpg"
        ),
        TVCharacterVo(
            "2",
            "Morty Smith",
            "https://cdn.britannica.com/77/170477-050-1C747EE3/Laptop-computer.jpg"
        ),
        TVCharacterVo(
            "3",
            "Summer Smith",
            "https://cdn.britannica.com/77/170477-050-1C747EE3/Laptop-computer.jpg"
        ),
        TVCharacterVo(
            "4",
            "Beth Smith",
            "https://cdn.britannica.com/77/170477-050-1C747EE3/Laptop-computer.jpg"
        ),
        TVCharacterVo(
            "5",
            "Jerry Smith",
            "https://cdn.britannica.com/77/170477-050-1C747EE3/Laptop-computer.jpg"
        ),
    )

    val clickableCards = listOf(
        ClickableCardVo(
            id = "1",
            title = "Pilot",
            subtitle = "S01E01",
            images = listOf(
                CircleImageVo(
                    id = "1",
                    imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
                ),
                CircleImageVo(
                    id = "2",
                    imageUrl = "https://rickandmortyapi.com/api/character/avatar/2.jpeg"
                ),
            )
        ),
        ClickableCardVo(
            id = "2",
            title = "Lawnmower Dog",
            subtitle = "S01E02",
            images = listOf(
                CircleImageVo(
                    id = "3",
                    imageUrl = "https://rickandmortyapi.com/api/character/avatar/3.jpeg"
                ),
                CircleImageVo(
                    id = "4",
                    imageUrl = "https://rickandmortyapi.com/api/character/avatar/4.jpeg"
                ),
            )
        ),
        ClickableCardVo(
            id = "3",
            title = "Anatomy Park",
            subtitle = "S01E03",
            images = listOf(
                CircleImageVo(
                    id = "5",
                    imageUrl = "https://rickandmortyapi.com/api/character/avatar/5.jpeg"
                ),
                CircleImageVo(
                    id = "6",
                    imageUrl = "https://rickandmortyapi.com/api/character/avatar/6.jpeg"
                ),
            )
        ),
        ClickableCardVo(
            id = "4",
            title = "M. Night Shaym-Aliens!",
            subtitle = "S01E04",
            images = listOf(
                CircleImageVo(
                    id = "7",
                    imageUrl = "https://rickandmortyapi.com/api/character/avatar/7.jpeg"
                ),
                CircleImageVo(
                    id = "8",
                    imageUrl = "https://rickandmortyapi.com/api/character/avatar/8.jpeg"
                ),
            )
        ),
        ClickableCardVo(
            id = "5",
            title = "Meeseeks and Destroy",
            subtitle = "S01E05",
            images = listOf(
                CircleImageVo(
                    id = "9",
                    imageUrl = "https://rickandmortyapi.com/api/character/avatar/9.jpeg"
                ),
                CircleImageVo(
                    id = "10",
                    imageUrl = "https://rickandmortyapi.com/api/character/avatar/10.jpeg"
                ),
            )
        ),
    )
}
