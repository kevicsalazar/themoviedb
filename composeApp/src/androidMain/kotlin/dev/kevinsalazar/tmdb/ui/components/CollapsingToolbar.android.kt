package dev.kevinsalazar.tmdb.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.Visibility

actual val MinToolbarHeight = 80.dp

@Composable
actual fun CollapsingToolbarLayout(
    progress: Float,
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    MotionLayout(
        modifier = modifier
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
        content.invoke()
    }
}
