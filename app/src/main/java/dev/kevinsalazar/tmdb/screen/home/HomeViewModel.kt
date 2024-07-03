package dev.kevinsalazar.tmdb.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.kevinsalazar.domain.usecases.GetPagedTvShowsUseCase
import dev.kevinsalazar.tmdb.R
import dev.kevinsalazar.tmdb.navigation.NavigationScreen
import dev.kevinsalazar.tmdb.screen.home.model.CategoryModel
import dev.kevinsalazar.tmdb.screen.home.model.TvShowModel
import dev.kevinsalazar.tmdb.utils.Constants.AIRING_TODAY
import dev.kevinsalazar.tmdb.utils.Constants.ON_THE_AIR
import dev.kevinsalazar.tmdb.utils.Constants.POPULAR
import dev.kevinsalazar.tmdb.utils.Constants.TOP_RATED
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTvShowsUseCase: GetPagedTvShowsUseCase,
    private val homeMapper: HomeMapper
) : ViewModel(), HomeContract {

    private val _state = MutableStateFlow(HomeContract.State())
    override val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<HomeContract.Effect>()
    override val effect = _effect.asSharedFlow()

    override val tvShowsState = MutableStateFlow<PagingData<TvShowModel>>(PagingData.empty())

    override fun onUIEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.OnGetTVShows -> fetchTvShows(event.category)
            is HomeContract.Event.OnGetCategories -> onGetCategories()
            is HomeContract.Event.OnTvShowClick -> onTvShowClick(event.id)
        }
    }

    private fun fetchTvShows(category: String) = viewModelScope.launch {
        getTvShowsUseCase(category)
            .map { it.map(homeMapper::map) }
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect {
                tvShowsState.value = it
            }
    }

    private fun onGetCategories() {
        val categories = listOf(
            CategoryModel(R.string.top_rated_category, TOP_RATED),
            CategoryModel(R.string.popular_category, POPULAR),
            CategoryModel(R.string.on_tv_category, ON_THE_AIR),
            CategoryModel(R.string.airing_today_category, AIRING_TODAY)
        )
        _state.update {
            it.copy(categories = categories)
        }
    }

    private fun onTvShowClick(id: Int) {
        viewModelScope.launch {
            val route = NavigationScreen.DetailScreen(id)
            _effect.emit(HomeContract.Effect.Navigate(route))
        }
    }
}
