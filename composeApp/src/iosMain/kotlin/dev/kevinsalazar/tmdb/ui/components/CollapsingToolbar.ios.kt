package dev.kevinsalazar.tmdb.ui.components

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp

actual val MinToolbarHeight = 100.dp

private const val NavIconPosition = 1
private const val LabelPosition = 2
private const val TitlePosition = 3
private const val SubtitlePosition = 4

@Composable
actual fun CollapsingToolbarLayout(
    progress: Float,
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier
            .statusBarsPadding(),
        content = content
    ) { measurables, constraints ->

        val placeables = measurables.map {
            it.measure(constraints)
        }
        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight
        ) {

            val navIcon = placeables[NavIconPosition]
            val label = placeables[LabelPosition]
            val title = placeables[TitlePosition]
            val subtitle = placeables[SubtitlePosition]

            navIcon.placeRelative(
                x = 0,
                y = 0,
            )
            label.placeRelative(
                x = lerp(
                    start = navIcon.width,
                    stop = 0,
                    fraction = progress
                ),
                y = lerp(
                    start = 0,
                    stop = constraints.maxHeight - label.height - title.height - subtitle.height,
                    fraction = progress
                )
            )
            title.placeRelative(
                x = lerp(
                    start = navIcon.width,
                    stop = 0,
                    fraction = progress
                ),
                y = lerp(
                    start = 0,
                    stop = constraints.maxHeight - title.height - subtitle.height,
                    fraction = progress
                )
            )
            subtitle.placeRelative(
                x = lerp(
                    start = navIcon.width,
                    stop = 0,
                    fraction = progress
                ),
                y = lerp(
                    start = 0,
                    stop = constraints.maxHeight - subtitle.height,
                    fraction = progress
                )
            )
        }
    }
}
