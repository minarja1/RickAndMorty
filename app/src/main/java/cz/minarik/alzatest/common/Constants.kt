package cz.minarik.alzatest.common

import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

object Constants {
    const val argCategoryId = "argCategoryId"
    const val argCategoryName = "argCategoryName"
    const val argProductId = "argProductId"
    const val argProductName = "argProductName"

    const val dataStoreName = "settings"

    val categoriesMinFetchGap = TimeUnit.MINUTES.toMillis(1)

    val UTF_8 = StandardCharsets.UTF_8.toString()
}