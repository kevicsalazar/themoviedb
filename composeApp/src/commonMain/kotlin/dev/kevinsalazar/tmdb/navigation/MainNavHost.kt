package dev.kevinsalazar.tmdb.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.kevinsalazar.tmdb.screen.detail.DetailScreen
import dev.kevinsalazar.tmdb.screen.home.HomeScreen
import dev.kevinsalazar.tmdb.utils.NavControllerProvider

@Composable
fun MainNavHost(
    modifier: Modifier
) {

    val navController = rememberNavController()

    NavControllerProvider(
        navController = navController
    ) {
        NavHost(
            navController = navController,
            startDestination = NavigationScreen.HomeScreen,
            modifier = modifier
        ) {
            composable<NavigationScreen.HomeScreen> {
                HomeScreen()
            }
            composable<NavigationScreen.DetailScreen> { backStackEntry ->
                val route = backStackEntry.toRoute<NavigationScreen.DetailScreen>()
                DetailScreen(
                    id = route.seriesId
                )
            }
        }
    }
}
