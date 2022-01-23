//package cz.minarik.alzatest.ui.dashboard
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.layoutId
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.dp
//import cz.minarik.alzatest.ui.base.BaseScreen
//import org.koin.androidx.compose.get
//
//@Composable
//fun DashboardScreen(
//    viewModel: DashboardVM = get(),
//) {
//    val viewState = viewModel.viewState.collectAsState()
//
//    BaseScreen(status = viewState.value.status, vm = viewModel) {
//        Scaffold(
//            content = {
//                ListContainer(
//                    status = viewState.value.status,
//                    list = viewState.value.list,
//                    emptyMessage = { EmptyPlaceholder(stringResource(R.string.empty_list)) }
//                ) {
//                    LaunchesList(
//                        status = viewState.value.status,
//                        launches = viewState.value.list,
//                        onClick = { },
//                        onRefresh = { viewModel.setEvent(Event.FetchItems) }
//                    )
//                }
//            },
//            topBar = {
//                TopAppBar(
//                    title = { Text(stringResource(R.string.app_name)) },
//                )
//            },
//        )
//    }
//}
//
//@Composable
//fun <T> ListContainer(
//    status: Status<Unit>,
//    list: List<T>,
//    emptyMessage: @Composable () -> Unit = { EmptyPlaceholder("List is empty") },
//    content: @Composable () -> Unit,
//) {
//    when {
//        status is Status.Success && list.isEmpty() -> emptyMessage()
//        else -> content()
//    }
//}
//
//@Composable
//fun EmptyPlaceholder(
//    message: String = "List is empty",
//) {
//    Surface(
//        modifier = Modifier
//            .fillMaxWidth()
//            .wrapContentHeight()
//            .padding(8.dp),
//        shape = Shapes.large,
//        contentColor = Gray300,
//    ) {
//        Text(
//            text = message,
//            textAlign = TextAlign.Center,
//        )
//    }
//}
//
//@Composable
//fun LaunchesList(
//    status: Status<Unit>,
//    launches: List<RocketLaunch>,
//    onClick: (RocketLaunch) -> Unit,
//    onRefresh: () -> Unit,
//) {
//    val swipeRefreshState =
//        rememberSwipeRefreshState(status is Status.Loading || status is Status.Init)
//
//    SwipeRefresh(
//        modifier = Modifier.fillMaxWidth(),
//        state = swipeRefreshState,
//        onRefresh = onRefresh
//    ) {
//        LazyColumn(
//            contentPadding = PaddingValues(bottom = 56.dp, top = 8.dp, start = 8.dp, end = 8.dp),
//            verticalArrangement = Arrangement.spacedBy(8.dp),
//        ) {
//            items(items = launches, key = { it.hashCode() }) {
//                LaunchItem(
//                    launch = it,
//                    onItemClick = onClick
//                )
//            }
//        }
//    }
//}
//
//private const val TitleId = "TitleId"
//private const val RocketNameId = "RocketNameId"
//
//@OptIn(ExperimentalMaterialApi::class)
//@Composable
//fun LaunchItem(
//    launch: RocketLaunch,
//    onItemClick: (RocketLaunch) -> Unit,
//    modifier: Modifier = Modifier,
//    elevation: Dp = 1.dp,
//    bgColor: Color = Color.White,
//) {
//    Card(
//        elevation = elevation,
//        onClick = { onItemClick(launch) },
//        modifier = modifier
//            .wrapContentHeight()
//            .fillMaxWidth(),
//        backgroundColor = bgColor,
//    ) {
//        Column {
//            Text(
//                text = launch.missionName,
//                modifier = Modifier.layoutId(TitleId),
//                style = Typography.h5,
//            )
//            Text(
//                text = launch.rocket.name,
//                modifier = Modifier.layoutId(TitleId),
//                style = Typography.h3,
//            )
//        }
//    }
//}