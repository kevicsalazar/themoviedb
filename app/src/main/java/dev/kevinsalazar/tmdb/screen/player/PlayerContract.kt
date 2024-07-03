package dev.kevinsalazar.tmdb.screen.player

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow


interface PlayerContract {

    data class State(
        val title: String = "",
        val videoUrl: String = "",
        val loading: Boolean = true,
    )

    sealed class Event {
        data object LoadVideo : Event()
        data object OnBackPressed : Event()
    }

    sealed class Effect {
        data object OnBackPressed : Effect()
    }

    val state: StateFlow<State>
    val effect: SharedFlow<Effect>

    fun onEvent(event: Event)
}
