package cz.minarik.alzatest.data.remote.response

data class InfoResponse(
    /**
     * The amount of pages.
     */

    val pages: Int?,
    /**
     * The length of the response.
     */

    val count: Int?,
    /**
     * Number of the next page (if it exists)
     */
    val next: Int?,

    /**
     * Number of the previous page (if it exists)
     */
    val prev: Int?,
)
