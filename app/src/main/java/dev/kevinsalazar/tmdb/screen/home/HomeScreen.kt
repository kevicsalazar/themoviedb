package dev.kevinsalazar.tmdb.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import dev.kevinsalazar.tmdb.R
import dev.kevinsalazar.tmdb.screen.home.HomeContract.Effect
import dev.kevinsalazar.tmdb.screen.home.HomeContract.Event
import dev.kevinsalazar.tmdb.screen.home.HomeContract.State
import dev.kevinsalazar.tmdb.screen.home.model.CategoryModel
import dev.kevinsalazar.tmdb.screen.home.model.TvShowModel
import dev.kevinsalazar.tmdb.ui.components.CategoryItem
import dev.kevinsalazar.tmdb.ui.components.ErrorMessage
import dev.kevinsalazar.tmdb.ui.components.LoadingIndicator
import dev.kevinsalazar.tmdb.ui.components.TabIndicator
import dev.kevinsalazar.tmdb.ui.components.TvShowItem
import dev.kevinsalazar.tmdb.ui.components.tabIndicatorOffset
import dev.kevinsalazar.tmdb.ui.theme.TmdbTheme
import dev.kevinsalazar.tmdb.utils.Constants
import dev.kevinsalazar.tmdb.utils.Constants.POPULAR
import dev.kevinsalazar.tmdb.utils.Constants.TWO
import dev.kevinsalazar.tmdb.utils.LocalNavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val tvShows = viewModel.tvShowsState.collectAsLazyPagingItems()

    val navController = LocalNavController.current

    LaunchedEffect(Unit) {
        if (state.categories.isEmpty()) {
            viewModel.onUIEvent(Event.OnGetCategories)
            viewModel.onUIEvent(Event.OnGetTVShows(POPULAR))
        }
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest {
            when (it) {
                is Effect.Navigate -> navController.navigate(it.route)
            }
        }
    }

    HomeScreen(
        uiState = state,
        tvShows = tvShows,
        onTvShowClick = { id ->
            viewModel.onUIEvent(Event.OnTvShowClick(id))
        },
        onCategoryClick = { category ->
            viewModel.onUIEvent(Event.OnGetTVShows(category))
        }
    )
}

@Composable
fun HomeScreen(
    uiState: State = State(),
    tvShows: LazyPagingItems<TvShowModel>,
    onTvShowClick: (Int) -> Unit = {},
    onCategoryClick: (String) -> Unit = {}
) {

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(top = 16.dp)
    ) {
        CategoryHeader(
            categories = uiState.categories,
            onCategoryClick = onCategoryClick
        )
        if (tvShows.loadState.refresh is LoadState.Loading) {
            LoadingIndicator(
                modifier = Modifier.fillMaxSize(),
                isLoading = true
            )
        } else if (tvShows.loadState.refresh is LoadState.Error) {
            ErrorMessage(
                modifier = Modifier.fillMaxSize(),
                errorMessage = stringResource(id = R.string.unexpected_error),
                onRetry = { tvShows.retry() }
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(TWO),
                contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)
            ) {
                items(
                    count = tvShows.itemCount
                ) { index ->
                    tvShows[index]?.also { tvShow ->
                        TvShowItem(
                            modifier = Modifier.padding(8.dp),
                            tvShow = tvShow,
                            onClick = {
                                onTvShowClick.invoke(tvShow.tvShowId)
                            }
                        )
                    }
                }
            }
            LoadingIndicator(
                modifier = Modifier.fillMaxWidth(),
                isLoading = tvShows.loadState.append is LoadState.Loading
            )
        }
    }
}

@Composable
private fun CategoryHeader(
    categories: List<CategoryModel>,
    onCategoryClick: (String) -> Unit
) {

    var state by rememberSaveable { mutableStateOf(1) }

    if (categories.isNotEmpty()) {
        ScrollableTabRow(
            selectedTabIndex = state,
            divider = {},
            indicator = {
                TabIndicator(
                    Modifier.tabIndicatorOffset(it[state])
                )
            }
        ) {
            categories.forEachIndexed { index, _ ->
                val category = categories[index]
                CategoryItem(
                    category = category,
                    isSelected = state == index,
                    onClick = {
                        state = index
                        onCategoryClick.invoke(category.category)
                    }
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    TmdbTheme {
        val tvShowList = listOf(
            TvShowModel(
                tvShowId = 1,
                originalName = "Avatar"
            ),
            TvShowModel(
                tvShowId = 2,
                originalName = "Avatar"
            ),
            TvShowModel(
                tvShowId = 3,
                originalName = "Avatar"
            )
        )
        val tvShowsState = flowOf(
            PagingData.from(
                data = tvShowList,
                sourceLoadStates = LoadStates(
                    refresh = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false),
                    prepend = LoadState.NotLoading(false),
                ),
            )
        )
        val tvShows = tvShowsState.collectAsLazyPagingItems()
        val categories = listOf(
            CategoryModel(R.string.top_rated_category, Constants.TOP_RATED),
            CategoryModel(R.string.popular_category, POPULAR),
            CategoryModel(R.string.on_tv_category, Constants.ON_THE_AIR),
            CategoryModel(R.string.airing_today_category, Constants.AIRING_TODAY)
        )
        HomeScreen(
            uiState = State(
                categories = categories
            ),
            tvShows = tvShows
        )
    }
}
