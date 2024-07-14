package dev.kevinsalazar.tmdb.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


private const val Alpha = 0.75f

expect val MinToolbarHeight: Dp
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
                modifier = Modifier
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
                        .layoutId("nav")
                ) {
                    navIcon.invoke()
                }
                Box(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .alpha(progress)
                        .layoutId("label"),
                ) {
                    label.invoke()
                }
                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .layoutId("title")
                        .defaultMinSize(minHeight = 44.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    title.invoke()
                }
                Box(
                    modifier = Modifier
                        .padding(start = 8.dp, bottom = 8.dp)
                        .alpha(progress)
                        .layoutId("subtitle"),
                ) {
                    subtitle.invoke()
                }
            }
        }
    }
}

@Composable
expect fun CollapsingToolbarLayout(
    progress: Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
)
