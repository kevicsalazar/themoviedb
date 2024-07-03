package dev.kevinsalazar.tmdb.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.lerp
import androidx.compose.ui.tooling.preview.Preview
import dev.kevinsalazar.tmdb.ui.theme.TmdbTheme

@Composable
fun AnimatableText(
    modifier: Modifier = Modifier,
    text: String,
    startStyle: TextStyle,
    endStyle: TextStyle,
    progress: Float,
    color: Color
) {

    val textStyle by remember(progress) {
        derivedStateOf {
            lerp(startStyle, endStyle, progress)
        }
    }

    Text(
        modifier = modifier,
        text = text,
        style = textStyle,
        color = color
    )
}

@Preview
@Composable
fun AnimatableTextStartPreview() {
    TmdbTheme {
        AnimatableText(
            text = "Challenge",
            startStyle = MaterialTheme.typography.titleMedium,
            endStyle = MaterialTheme.typography.displayMedium,
            progress = 0f,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview
@Composable
fun AnimatableTextEndPreview() {
    TmdbTheme {
        AnimatableText(
            text = "Challenge",
            startStyle = MaterialTheme.typography.titleMedium,
            endStyle = MaterialTheme.typography.displayMedium,
            progress = 1f,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
