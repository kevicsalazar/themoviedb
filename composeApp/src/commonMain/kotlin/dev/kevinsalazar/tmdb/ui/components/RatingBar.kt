package dev.kevinsalazar.tmdb.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.StarHalf
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.kevinsalazar.tmdb.ui.theme.TmdbTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.round

private const val RatingTotal = 5

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Float,
    total: Int = RatingTotal,
    iconColor: Color
) {
    val colored = rating.toInt()
    val halfColored = round(rating - colored).toInt()
    val uncolored = total - colored - halfColored

    Row(modifier = modifier) {
        repeat(colored) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Star",
                tint = iconColor
            )
        }
        repeat(halfColored) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.StarHalf,
                contentDescription = "StarHalf",
                tint = iconColor
            )
        }
        repeat(uncolored) {
            Icon(
                imageVector = Icons.Outlined.StarOutline,
                contentDescription = "StarOutline",
                tint = iconColor
            )
        }
    }
}

@Preview
@Composable
fun RatingBarPreview() {
    TmdbTheme {
        RatingBar(
            rating = 2.6f,
            iconColor = MaterialTheme.colorScheme.primary
        )
    }
}
