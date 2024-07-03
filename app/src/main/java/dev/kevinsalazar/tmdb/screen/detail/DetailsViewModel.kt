package dev.kevinsalazar.tmdb.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.kevinsalazar.domain.usecases.GetTvShowDetailsUseCase
import dev.kevinsalazar.domain.values.onFailure
import dev.kevinsalazar.domain.values.onSuccess
import dev.kevinsalazar.tmdb.R
import dev.kevinsalazar.tmdb.navigation.NavigationScreen.DetailScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getTvShowDetailsUseCase: GetTvShowDetailsUseCase,
    private val detailsMapper: DetailsMapper,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), DetailsContract {

    private val _state = MutableStateFlow(DetailsContract.State())
    override val state = _state.asStateFlow()

    private val route get() = savedStateHandle.toRoute<DetailScreen>()

    override fun onEvent(event: DetailsContract.Event) {
        when (event) {
            is DetailsContract.Event.OnGetTVShowDetails -> fetchTvShowDetails()
        }
    }

    private fun fetchTvShowDetails() = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true,
                errorMessageId = null
            )
        }
        getTvShowDetailsUseCase(route.seriesId)
            .onSuccess { data ->
                _state.update {
                    it.copy(
                        details = detailsMapper.map(data),
                        errorMessageId = null,
                        isLoading = false
                    )
                }
            }
            .onFailure { _ ->
                _state.update {
                    it.copy(
                        errorMessageId = R.string.unexpected_error,
                        isLoading = false
                    )
                }
            }
    }
}
