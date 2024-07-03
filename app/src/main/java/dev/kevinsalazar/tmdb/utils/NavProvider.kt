package dev.kevinsalazar.tmdb.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

val LocalNavController = compositionLocalOf<NavHostController> {
    error("No LocalNavController provided")
}

@Composable
fun NavControllerProvider(
    navController: NavHostController,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalNavController provides navController,
        content = content
    )
}
