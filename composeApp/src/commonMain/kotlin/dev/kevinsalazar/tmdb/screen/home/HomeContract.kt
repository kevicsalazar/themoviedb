package dev.kevinsalazar.tmdb.screen.home

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import dev.kevinsalazar.tmdb.navigation.NavigationScreen
import dev.kevinsalazar.tmdb.screen.home.model.CategoryModel
import dev.kevinsalazar.tmdb.screen.home.model.TvShowModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface HomeContract {

    @Stable
    data class State(
        val categories: List<CategoryModel> = emptyList()
    )

    sealed class Event {
        data class OnGetTVShows(val category: String) : Event()
        data object OnGetCategories : Event()
        data class OnTvShowClick(val id: Int) : Event()
    }

    sealed interface Effect {
        data class Navigate(val route: NavigationScreen) : Effect
    }

    val state: StateFlow<State>
    val effect: SharedFlow<Effect>
    val tvShowsState: MutableStateFlow<PagingData<TvShowModel>>

    fun onUIEvent(event: Event)
}
