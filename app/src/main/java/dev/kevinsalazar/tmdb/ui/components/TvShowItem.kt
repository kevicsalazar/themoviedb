package dev.kevinsalazar.tmdb.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.kevinsalazar.tmdb.screen.home.model.TvShowModel
import dev.kevinsalazar.tmdb.utils.Constants.ONE
import dev.kevinsalazar.tmdb.utils.getAsyncImageModel

@Composable
fun TvShowItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    tvShow: TvShowModel?
) {
    Card(
        shape = RoundedCornerShape(CornerSize(12.dp)),
        modifier = modifier,
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = onClick
    ) {
        Column {
            AsyncImage(
                model = getAsyncImageModel(
                    data = tvShow?.posterPath.orEmpty(),
                    context = LocalContext.current
                ),
                contentDescription = tvShow?.originalName,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(168.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier.padding(all = 16.dp),
                text = tvShow?.originalName.orEmpty(),
                style = MaterialTheme.typography.titleMedium,
                maxLines = ONE,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview
@Composable
fun TvShowPreview() {
    TvShowItem(
        modifier = Modifier
            .size(
                width = 156.dp,
                height = 230.dp
            ),
        tvShow = TvShowModel(
            tvShowId = 0,
            name = "",
            originalName = "SpongeBob",
            posterPath = "url"
        ),
        onClick = {}
    )
}
