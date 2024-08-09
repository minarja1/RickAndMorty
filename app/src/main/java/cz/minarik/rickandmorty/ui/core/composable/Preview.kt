package cz.minarik.rickandmorty.ui.core.composable

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

/**
 * Preview annotation definitions.
 *
 * @author eMan a.s.
 */
@Preview(name = "Light preview", showBackground = true)
@Preview(name = "Dark preview", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(
    name = "Small screen large font light preview",
    device = Devices.NEXUS_5,
    fontScale = 1.5f,
    showBackground = true
)
@Preview(
    name = "Small screen large font dark preview",
    device = Devices.NEXUS_5,
    fontScale = 1.5f,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
annotation class ScreenPreview

@Preview(name = "Light preview", showBackground = true)
@Preview(name = "Dark preview", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
annotation class ComponentPreview
