package dev.kevinsalazar.tmdb.navigation

import kotlinx.serialization.Serializable

sealed class NavigationScreen {
    @Serializable
    data object HomeScreen : NavigationScreen()

    @Serializable
    data class DetailScreen(
        val seriesId: Int
    ) : NavigationScreen()
}

