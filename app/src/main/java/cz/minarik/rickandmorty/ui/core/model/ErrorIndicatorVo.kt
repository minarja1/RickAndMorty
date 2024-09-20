package cz.minarik.rickandmorty.ui.core.model

/**
 * Model for error view.
 *
 * @property text error message
 * @property buttonVo button model for error view
 * @property showOverlay show overlay or not
 */
data class ErrorIndicatorVo(
    val text: StringModel,
    val buttonVo: ButtonVo? = null,
    val showOverlay: Boolean = true,
)
