package cz.minarik.rickandmorty.ui.core.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Interface that needs to be implemented in each of ViewModel that is used together
 * with Jetpack Compose. It ensures correct usage of State/Event architecture.
 *
 * @param T type of data that are shared with UI in [UIState]
 * @param E type of event sealed class that implements [UIEvent]
 */
interface ComposeViewModel<T, E : UIEvent> {

    /**
     * State of View that is observed in compose screens. It uses [StateFlow] to limit accessibility
     * of modifying it from outside of the ViewModel.
     */
    val viewState: StateFlow<UIState<T>>

    /**
     * To handle user actions from UI in the ViewModel. Each action has to be defined in
     * sealed class that implements [UIEvent].
     *
     * @param event specific event that is part [E] class.
     */
    fun onEvent(event: E)
}

/**
 * CLass which simplifies usage of compose previews that are connected to [ComposeViewModel].
 *
 * @param T type of data that are shared with UI in UIState
 * @param E type of event sealed class that implements UIEvent
 * @property state UI state to be set in ViewModel.
 */
open class PreviewViewModel<T, E : UIEvent>(val state: UIState<T>) : ComposeViewModel<T, E> {

    override val viewState: StateFlow<UIState<T>> = MutableStateFlow(state)

    override fun onEvent(event: E) {}
}
