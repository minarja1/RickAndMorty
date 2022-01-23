//package cz.minarik.alzatest.ui.login
//
//import androidx.compose.runtime.Immutable
//import cz.minarik.alzatest.ui.base.data.Status
//import cz.minarik.alzatest.ui.base.ui.ViewEvent
//import cz.minarik.alzatest.ui.base.ui.ViewSideEffect
//import cz.minarik.alzatest.ui.base.ui.ViewState
//
//@Immutable
//data class LoginState(
//    val email: String = "",
//    val password: String = "",
//    val isPasswordShown: Boolean = false,
//    override val status: Status<Unit> = Status.Init
//) : ViewState.State<Unit>(status = status)
//
//sealed interface Event : ViewEvent {
//    object Login : Event
//    class EmailChanged(val email: String) : Event
//    class PasswordChanged(val password: String) : Event
//    object TogglePasswordVisibility : Event
//}
//
//sealed interface Effect : ViewSideEffect {
//    object NavigateToDashboard : Effect
//}
