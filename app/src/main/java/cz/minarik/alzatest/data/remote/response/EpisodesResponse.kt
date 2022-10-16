package cz.minarik.alzatest.data.remote.response

import cz.minarik.alzatest.domain.model.Episode

data class EpisodesResponse(
    val episodes: List<Episode>,
    val info: InfoResponse?,
)
