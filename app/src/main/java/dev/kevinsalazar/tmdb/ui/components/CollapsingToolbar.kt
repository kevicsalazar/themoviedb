package dev.kevinsalazar.tmdb.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.Visibility

private const val Alpha = 0.75f

val MinToolbarHeight = 80.dp
val MaxToolbarHeight = 275.dp

@Composable
fun CollapsingToolbar(
    modifier: Modifier = Modifier,
    progress: Float,
    background: @Composable () -> Unit,
    navIcon: @Composable () -> Unit,
    label: @Composable () -> Unit,
    title: @Composable () -> Unit,
    subtitle: @Composable () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        alpha = progress * Alpha
                        translationY = -progress / 2
                    }
            ) {
                background.invoke()
            }
            CollapsingToolbarLayout(
                progress = progress,
                navIcon = navIcon,
                label = label,
                title = title,
                subtitle = subtitle
            )
        }
    }
}

@Composable
private fun CollapsingToolbarLayout(
    progress: Float,
    navIcon: @Composable () -> Unit,
    label: @Composable () -> Unit,
    title: @Composable () -> Unit,
    subtitle: @Composable () -> Unit
) {
    MotionLayout(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize(),
        start = ConstraintSet {
            val gradientView = createRefFor("gradient")
            val navView = createRefFor("nav")
            val labelView = createRefFor("label")
            val titleView = createRefFor("title")
            val subtitleView = createRefFor("subtitle")
            constrain(gradientView) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
                width = Dimension.fillToConstraints
                alpha = 0f
            }
            constrain(navView) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }
            constrain(labelView) {
                start.linkTo(parent.start)
                bottom.linkTo(titleView.top, margin = 4.dp)
                visibility = Visibility.Gone
            }
            constrain(titleView) {
                start.linkTo(navView.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
            constrain(subtitleView) {
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
                visibility = Visibility.Gone
            }
        },
        end = ConstraintSet {
            val gradientView = createRefFor("gradient")
            val navView = createRefFor("nav")
            val labelView = createRefFor("label")
            val titleView = createRefFor("title")
            val subtitleView = createRefFor("subtitle")
            constrain(gradientView) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
                width = Dimension.fillToConstraints
            }
            constrain(navView) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }
            constrain(labelView) {
                start.linkTo(parent.start)
                bottom.linkTo(titleView.top, margin = 4.dp)
            }
            constrain(titleView) {
                start.linkTo(parent.start)
                bottom.linkTo(subtitleView.top, margin = 4.dp)
            }
            constrain(subtitleView) {
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
            }
        },
        progress = progress
    ) {
        CollapsingToolbarContent(
            navIcon = navIcon,
            label = label,
            title = title,
            subtitle = subtitle
        )
    }
}

@Composable
private fun CollapsingToolbarContent(
    navIcon: @Composable () -> Unit,
    label: @Composable () -> Unit,
    title: @Composable () -> Unit,
    subtitle: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .layoutId("gradient")
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color.Black)
                )
            )
    )
    Box(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .layoutId("nav")
    ) {
        navIcon.invoke()
    }
    Box(
        modifier = Modifier
            .padding(start = 8.dp)
            .layoutId("label")
    ) {
        label.invoke()
    }
    Box(
        modifier = Modifier
            .padding(start = 8.dp)
            .layoutId("title")
    ) {
        title.invoke()
    }
    Box(
        modifier = Modifier
            .padding(start = 8.dp, bottom = 8.dp)
            .layoutId("subtitle")
    ) {
        subtitle.invoke()
    }
}
