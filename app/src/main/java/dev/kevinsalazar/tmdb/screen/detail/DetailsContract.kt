package dev.kevinsalazar.tmdb.screen.detail

import dev.kevinsalazar.tmdb.screen.detail.model.DetailsModel
import kotlinx.coroutines.flow.StateFlow

interface DetailsContract {

    data class State(
        val isLoading: Boolean = false,
        val errorMessageId: Int? = null,
        val details: DetailsModel? = null
    )

    sealed class Event {
        data object OnGetTVShowDetails : Event()
    }

    val state: StateFlow<State>

    fun onEvent(event: Event)
}
