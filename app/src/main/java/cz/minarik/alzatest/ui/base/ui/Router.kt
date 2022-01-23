//package cz.minarik.alzatest.ui.base.ui
//
//import androidx.compose.runtime.Composable
//import androidx.navigation.NavBackStackEntry
//import androidx.navigation.NavGraphBuilder
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import cz.minarik.alzatest.ui.dashboard.DashboardScreen
//import cz.minarik.alzatest.ui.login.LoginScreen
//
//@Composable
//fun Router(
//    navController: NavHostController,
//) {
//
//    NavHost(
//        navController = navController,
//        startDestination = Location.Login.route,
//        // TODO - když bude startDestination state - změní se pro NavControllera startDestination ??
//    ) {
//
//        composeScreen(Location.Login) {
//            LoginScreen(navController = navController)
//        }
//
//        composeScreen(Location.Dashboard) {
//            DashboardScreen()
//        }
//    }
//}
//
//sealed interface Location {
//
//    val route: String get() = this::class.java.simpleName
//    val isReturnable: Boolean get() = true
//
//    object Login : NoArgLoc
//
//    object Dashboard : NoArgLoc {
//        override val isReturnable = false
//    }
//
//    sealed interface NoArgLoc : Location {
//
//        fun navigate(navHostController: NavHostController) {
//            navHostController.navigate(route) {
//                if (isReturnable.not())
//                    popUpTo(navHostController.graph.startDestinationId) {
//                        inclusive = true
//                    }
//            }
//        }
//
//        fun composable(
//            navGraphBuilder: NavGraphBuilder,
//            content: @Composable (NavBackStackEntry) -> Unit,
//        ) {
//            navGraphBuilder.composable(
//                route = route,
//            ) {
//                content(it)
//            }
//        }
//    }
//}
//
//
//fun NavGraphBuilder.composeScreen(
//    location: Location.NoArgLoc,
//    content: @Composable (NavBackStackEntry) -> Unit,
//) {
//    location.composable(this, content)
//}
//
///*
//
//    sealed interface OneArgLoc<in In, out Out> : Location {
//
//        val serialize: (In) -> String
//        val deserialize: (String) -> Out
//
//        val argName: String
//            get() = "arg"
//
//        fun navigate(navHostController: NavHostController, arg: In) {
//            val serializedArg = serialize(arg).let { URLEncoder.encode(it, "utf-8") }
//            navHostController.navigate(route = "$route/$serializedArg") {
//                if (isReturnable.not())
//                    popUpTo(navHostController.graph.startDestinationId) {
//                        inclusive = true
//                    }
//            }
//        }
//
//        fun composable(
//            navGraphBuilder: NavGraphBuilder,
//            content: @Composable (NavBackStackEntry, Out) -> Unit,
//        ) {
//            navGraphBuilder.composable(
//                route = "$route/{$argName}",
//                arguments = listOf(navArgument(argName) {
//                    type = NavType.StringType
//                }),
//            ) { navBackStackEntry ->
//                val arg: Out = navBackStackEntry.arguments?.getString(argName)!!
//                    .let { URLDecoder.decode(it, "utf-8") }
//                    .let(deserialize)
//                content(navBackStackEntry, arg)
//            }
//        }
//    }
//
// */