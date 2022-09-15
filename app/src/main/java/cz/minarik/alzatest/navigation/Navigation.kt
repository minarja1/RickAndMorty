package cz.minarik.alzatest.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cz.minarik.alzatest.common.Constants
import cz.minarik.alzatest.ui.screens.characters.detail.CharacterDetailScreen
import cz.minarik.alzatest.ui.screens.home.HomeScreen
import java.net.URLEncoder

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.HomePage.route) {
        composable(route = Screen.HomePage.route) {
            HomeScreen {
                navController.navigate(
                    Screen.ProductDetail.withArgs(
                        it.id,
                        URLEncoder.encode(it.name ?: "", Constants.UTF_8)
                    )
                )
            }
        }

        composable(
            route = Screen.ProductDetail.route + "/{${Constants.argProductId}}/{${Constants.argProductName}}",
            arguments = listOf(
                navArgument(Constants.argProductId) {
                    type = NavType.StringType
                },
                navArgument(Constants.argProductName) {
                    type = NavType.StringType
                },
            )
        ) {
            CharacterDetailScreen(
                onBackClicked = { navController.navigateUp() },
                characterId = it.arguments?.getString(Constants.argProductId),
                characterName = it.arguments?.getString(Constants.argProductName),
            )
        }
    }
}

