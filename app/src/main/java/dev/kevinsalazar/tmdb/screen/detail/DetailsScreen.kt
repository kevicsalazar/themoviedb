package dev.kevinsalazar.tmdb.screen.detail

import androidx.compose.animation.core.FloatExponentialDecaySpec
import androidx.compose.animation.core.animateDecay
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import dev.kevinsalazar.tmdb.screen.detail.model.DetailsModel
import dev.kevinsalazar.tmdb.ui.components.AnimatableText
import dev.kevinsalazar.tmdb.ui.components.CollapsingToolbar
import dev.kevinsalazar.tmdb.ui.components.ErrorMessage
import dev.kevinsalazar.tmdb.ui.components.LoadingIndicator
import dev.kevinsalazar.tmdb.ui.components.MaxToolbarHeight
import dev.kevinsalazar.tmdb.ui.components.MinToolbarHeight
import dev.kevinsalazar.tmdb.ui.components.RatingBar
import dev.kevinsalazar.tmdb.ui.components.SeasonItem
import dev.kevinsalazar.tmdb.ui.components.TvShowSummary
import dev.kevinsalazar.tmdb.ui.states.ToolbarState
import dev.kevinsalazar.tmdb.ui.states.scrollflags.ExitUntilCollapsedState
import dev.kevinsalazar.tmdb.ui.theme.TmdbTheme
import dev.kevinsalazar.tmdb.utils.LocalNavController
import dev.kevinsalazar.tmdb.utils.getAsyncImageModel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch


@Composable
fun DetailScreen(
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsState()

    val navController = LocalNavController.current

    LaunchedEffect(Unit) {
        viewModel.onEvent(DetailsContract.Event.OnGetTVShowDetails)
    }

    DetailScreen(
        uiState = uiState,
        onRetry = {
            viewModel.onEvent(DetailsContract.Event.OnGetTVShowDetails)
        },
        onBack = {
            navController.popBackStack()
        }
    )
}

@Composable
fun DetailScreen(
    uiState: DetailsContract.State,
    onRetry: () -> Unit = {},
    onBack: () -> Unit = {}
) {

    LoadingIndicator(
        modifier = Modifier.fillMaxSize(),
        isLoading = uiState.isLoading
    )

    uiState.errorMessageId?.also {
        ErrorMessage(
            modifier = Modifier.fillMaxSize(),
            errorMessage = stringResource(id = it),
            onRetry = onRetry
        )
    }

    uiState.details?.also {
        DetailsScaffold(
            modifier = Modifier
                .testTag("root")
                .fillMaxSize(),
            details = it,
            onBack = onBack
        )
    }
}

@Composable
fun DetailsScaffold(
    modifier: Modifier = Modifier,
    details: DetailsModel,
    onBack: () -> Unit
) {

    val toolbarHeightRange = with(LocalDensity.current) {
        MinToolbarHeight.roundToPx()..MaxToolbarHeight.roundToPx()
    }
    val toolbarState = rememberToolbarState(toolbarHeightRange)
    val listState = rememberLazyListState()

    val scope = rememberCoroutineScope()

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                toolbarState.scrollTopLimitReached =
                    listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0
                toolbarState.scrollOffset -= available.y
                return Offset(0f, toolbarState.consumed)
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                if (available.y > 0) {
                    scope.launch {
                        animateDecay(
                            initialValue = toolbarState.height + toolbarState.offset,
                            initialVelocity = available.y,
                            animationSpec = FloatExponentialDecaySpec()
                        ) { value, _ ->
                            toolbarState.scrollTopLimitReached =
                                listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0
                            toolbarState.scrollOffset -= (value - (toolbarState.height + toolbarState.offset))
                            if (toolbarState.scrollOffset == 0f) scope.coroutineContext.cancelChildren()
                        }
                    }
                }

                return super.onPostFling(consumed, available)
            }
        }
    }

    Box(
        modifier = modifier
            .nestedScroll(nestedScrollConnection)
    ) {
        DetailsContent(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    translationY = toolbarState.height + toolbarState.offset
                }
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = { scope.coroutineContext.cancelChildren() }
                    )
                },
            listState = listState,
            details = details
        )
        CollapsingToolbar(
            modifier = Modifier
                .fillMaxWidth()
                .height(with(LocalDensity.current) { toolbarState.height.toDp() })
                .graphicsLayer { translationY = toolbarState.offset },
            progress = toolbarState.progress,
            background = {
                AsyncImage(
                    model = getAsyncImageModel(
                        data = details.backdropImagePath,
                        context = LocalContext.current
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            },
            navIcon = {
                IconButton(
                    onClick = {
                        onBack.invoke()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            label = {
                Text(
                    text = details.originalName,
                    color = Color.White
                )
            },
            title = {
                AnimatableText(
                    text = details.name,
                    startStyle = MaterialTheme.typography.titleMedium,
                    endStyle = MaterialTheme.typography.displayMedium,
                    progress = toolbarState.progress,
                    color = Color.White
                )
            },
            subtitle = {
                details.voteAverage?.also {
                    RatingBar(
                        rating = it,
                        iconColor = Color.Yellow
                    )
                }
            }
        )
    }
}

@Composable
fun DetailsContent(
    modifier: Modifier,
    listState: LazyListState,
    details: DetailsModel
) {
    LazyColumn(
        modifier = modifier
            .navigationBarsPadding(),
        state = listState,
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 16.dp,
            end = 16.dp,
            bottom = MinToolbarHeight
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TvShowSummary(
                overview = details.overview
            )
        }
        items(details.seasons) { season ->
            SeasonItem(season = season)
        }
    }
}

@Composable
private fun rememberToolbarState(toolbarHeightRange: IntRange): ToolbarState {
    return rememberSaveable(saver = ExitUntilCollapsedState.Saver) {
        ExitUntilCollapsedState(toolbarHeightRange)
    }
}

@Preview
@Composable
fun DetailScreenPreview() {
    TmdbTheme {
        DetailScreen(
            uiState = DetailsContract.State(
                details = DetailsModel(
                    name = "Avatar: The Last Airbender",
                    backdropImagePath = ".../9jUuxbMSp3cwC2DDrSAs2F43Ric.jpg",
                    overview = "In a war-torn world of elemental magic...",
                    seasons = listOf(
                        DetailsModel.Season(
                            name = "Book One: Water",
                            overview = "Katara and Sokka (a brother ...",
                            posterImagePath = ".../tUG6h0rMtQyOgvqI8r9AqxlKoUP.jpg",
                            episodeCount = 20
                        )
                    ),
                    originalName = "Avatar: The Last Airbender",
                    voteAverage = 4.36f
                )
            )
        )
    }
}
