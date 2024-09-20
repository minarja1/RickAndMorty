package cz.minarik.rickandmorty.ui.core.model

/**
 * Model for button.
 *
 * @property text title of the button
 * @property onClick action to be executed when button is clicked
 */
data class ButtonVo(
    val text: StringModel,
    val onClick: () -> Unit,
)
