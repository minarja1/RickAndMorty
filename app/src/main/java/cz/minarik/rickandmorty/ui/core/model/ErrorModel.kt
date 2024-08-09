package cz.minarik.rickandmorty.ui.core.model

/**
 * Model for error view.
 *
 * @property error error message
 * @property buttonModel button model for error view
 */
data class ErrorModel(
    val error: String,
    val buttonModel: ButtonModel? = null,
)
