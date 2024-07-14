package dev.kevinsalazar.tmdb.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kevinsalazar.domain.usecases.GetTvShowDetailsUseCase
import dev.kevinsalazar.domain.values.onFailure
import dev.kevinsalazar.domain.values.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import themoviedb.composeapp.generated.resources.Res
import themoviedb.composeapp.generated.resources.unexpected_error


class DetailsViewModel(
    private val id: Int,
    private val getTvShowDetailsUseCase: GetTvShowDetailsUseCase,
    private val detailsMapper: DetailsMapper
) : ViewModel(), DetailsContract {

    private val _state = MutableStateFlow(DetailsContract.State())
    override val state = _state.asStateFlow()

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
        getTvShowDetailsUseCase(id)
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
                        errorMessageId = Res.string.unexpected_error,
                        isLoading = false
                    )
                }
            }
    }
}
