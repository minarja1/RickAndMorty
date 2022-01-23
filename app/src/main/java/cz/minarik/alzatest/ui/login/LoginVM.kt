//package cz.minarik.alzatest.ui.login
//
//import cz.minarik.alzatest.ui.base.data.Status
//import cz.minarik.alzatest.ui.base.ui.BaseVM
//import kotlinx.coroutines.delay
//
//class LoginVM : BaseVM<LoginState, Event, Effect>(LoginState()) {
//
//    private fun togglePasswordVisibility() = launch {
//        setState { copy(isPasswordShown = isPasswordShown.not()) }
//    }
//
//    private suspend fun login() {
//        setState { copy(status = Status.Loading) }
//        delay(1500L)
//        setState { copy(status = Status.Success(Unit)) }
//        setEffect { Effect.NavigateToDashboard }
//    }
//
//    override fun handleEvents(event: Event) = launch {
//        when (event) {
//            Event.Login -> login()
//            is Event.EmailChanged -> setState { copy(email = event.email) }
//            is Event.PasswordChanged -> setState { copy(password = event.password) }
//            Event.TogglePasswordVisibility -> togglePasswordVisibility()
//        }
//    }
//}