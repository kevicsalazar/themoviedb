package dev.kevinsalazar.tmdb.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.kevinsalazar.tmdb.R
import dev.kevinsalazar.tmdb.screen.detail.model.DetailsModel
import dev.kevinsalazar.tmdb.utils.Constants
import dev.kevinsalazar.tmdb.utils.getAsyncImageModel

private const val ImageFraction = 0.4f

@Composable
fun SeasonItem(
    modifier: Modifier = Modifier,
    season: DetailsModel.Season
) {
    Card(
        modifier = modifier.height(180.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Top
        ) {
            AsyncImage(
                model = getAsyncImageModel(
                    data = season.posterImagePath,
                    context = LocalContext.current
                ),
                contentDescription = season.name,
                modifier = Modifier
                    .fillMaxWidth(ImageFraction)
                    .fillMaxHeight(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = season.name,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = stringResource(R.string.episodes, season.episodeCount.toString()),
                    modifier = Modifier.padding(vertical = 10.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = season.overview,
                    maxLines = Constants.THREE,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Preview
@Composable
fun SeasonItemPreview() {
    SeasonItem(
        season = DetailsModel.Season(
            name = "Season 1",
            overview = "Lorem ipsum dolor sit amet",
            posterImagePath = "",
            episodeCount = 10
        )
    )
}
