//
//import androidx.compose.runtime.Immutable
//import cz.minarik.alzatest.ui.base.data.Status
//import cz.minarik.alzatest.ui.base.ui.ViewEvent
//import cz.minarik.alzatest.ui.base.ui.ViewState
//
//@Immutable
//data class DashboardState(
//    val list: List<RocketLaunch> = emptyList(),
//    override val status: Status = Status.Loading,
//) : ViewState.State<Unit>(status = status) {
//    companion object
//}
//
//sealed interface Event : ViewEvent {
//    object FetchItems : Event
//}