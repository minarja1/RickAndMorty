package cz.minarik.rickandmorty.ui.core.model

/**
 * Model for button.
 *
 * @property title title of the button
 * @property onClick action to be executed when button is clicked
 */
data class ButtonModel(
    val text: String,
    val onClick: () -> Unit,
)
