package dev.kevinsalazar.tmdb.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.kevinsalazar.tmdb.screen.detail.DetailScreen
import dev.kevinsalazar.tmdb.screen.home.HomeScreen
import dev.kevinsalazar.tmdb.utils.LocalNavController

@Composable
fun MainNavHost(
    modifier: Modifier
) {
    val navController = LocalNavController.current
    NavHost(
        navController = navController,
        startDestination = NavigationScreen.HomeScreen,
        modifier = modifier
    ) {
        composable<NavigationScreen.HomeScreen> {
            HomeScreen()
        }
        composable<NavigationScreen.DetailScreen> {
            DetailScreen()
        }
    }
}
