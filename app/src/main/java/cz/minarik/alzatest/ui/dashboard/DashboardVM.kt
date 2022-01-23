//package cz.minarik.alzatest.ui.dashboard
//
//import DashboardState
//import Event
//import cz.minarik.alzatest.ui.base.data.Status
//import cz.minarik.alzatest.ui.base.ui.BaseVM
//import cz.minarik.alzatest.ui.base.ui.ViewSideEffect
//
//class DashboardVM(
//    private val repo: SpaceXRepo
//) : BaseVM<DashboardState, Event, ViewSideEffect>(DashboardState()) {
//
//    init {
//        setEvent(Event.FetchItems)
//    }
//
//    private suspend fun fetchItems() {
//        setState { copy(status = Status.Loading) }
//        val list = repo.fetchLaunches()
//        setState { copy(status = Status.Failure(Throwable()), list = list) }
//    }
//
//    override fun dismissErrorDialog() = launch {
//        //TODO:
//        setState { copy(status = Status.Success(Unit)) }
//    }
//
//    override fun handleEvents(event: Event) = launch {
//        when (event) {
//            is Event.FetchItems -> fetchItems()
//        }
//    }
//}