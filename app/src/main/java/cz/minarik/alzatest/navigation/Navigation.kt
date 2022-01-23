package cz.minarik.alzatest.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cz.minarik.alzatest.common.Constants
import cz.minarik.alzatest.ui.screens.home.HomeScreen
import cz.minarik.alzatest.ui.screens.products.ProductList

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.HomePage.route) {
        composable(route = Screen.HomePage.route) {
            HomeScreen(navController = navController)
        }

        composable(
            route = Screen.ProductList.route + "/{${Constants.argCategoryId}}/{${Constants.argCategoryName}}",
            arguments = listOf(
                navArgument(Constants.argCategoryId) {
                    type = NavType.StringType
                },
                navArgument(Constants.argCategoryName) {
                    type = NavType.StringType
                },
            )
        ) {
            ProductList(
                navController = navController,
                categoryId = it.arguments?.getString(Constants.argCategoryId),
                categoryName = it.arguments?.getString(Constants.argCategoryName),
            )
        }
    }
}