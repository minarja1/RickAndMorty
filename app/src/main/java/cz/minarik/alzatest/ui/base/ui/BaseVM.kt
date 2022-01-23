//package cz.minarik.alzatest.ui.base.ui
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import cz.minarik.alzatest.ui.base.data.Status
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.channels.Channel
//import kotlinx.coroutines.flow.*
//import kotlinx.coroutines.launch
//
//const val LaunchListenForEffects = "launch-listen-to-effects"
//
//sealed interface ViewState {
//    open class State<T>(open val status: Status<T>) : ViewState
//}
//interface ViewEvent
//interface ViewSideEffect
//
//abstract class BaseVM<UiState : ViewState, Event : ViewEvent, Effect : ViewSideEffect>(initialState: UiState) : ViewModel() {
//
//    private val _viewState = MutableStateFlow(initialState)
//    val viewState: StateFlow<UiState> = _viewState
//
//    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
//
//    private val _effect: Channel<Effect> = Channel()
//    val effect: Flow<Effect> = _effect.receiveAsFlow()
//
//    init {
//        subscribeToEvents()
//    }
//
//    fun setEvent(event: Event) {
//        viewModelScope.launch { _event.emit(event) }
//    }
//
//    protected suspend fun setState(reducer: UiState.() -> UiState) {
//        val newState = viewState.value.reducer()
//        onStateChange(oldState = viewState.value, newState = newState)
//        _viewState.emit(newState)
//    }
//
//    fun launch(block: suspend CoroutineScope.() -> Unit) {
//        viewModelScope.launch {
//            try {
//                block.invoke(this)
//            } catch (e: Throwable) {
//                handleFailure(e)
//                e.printStackTrace()
//            }
//        }
//    }
//
//    open fun dismissErrorDialog() {}
//
//    protected open fun handleFailure(e: Throwable) = launch {
//        // emit(_state.value.copy(Status.Failure(e)))
//    }
//
//    open fun onStateChange(oldState: UiState, newState: UiState) {}
//
//    private fun subscribeToEvents() {
//        launch {
//            _event.collect {
//                handleEvents(it)
//            }
//        }
//    }
//
//    abstract fun handleEvents(event: Event)
//
//    protected fun setEffect(builder: () -> Effect) {
//        val effectValue = builder()
//        launch { _effect.send(effectValue) }
//    }
//
//}
//
//
