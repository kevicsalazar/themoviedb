package dev.kevinsalazar.tmdb.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.kevinsalazar.tmdb.R
import dev.kevinsalazar.tmdb.utils.Constants

@Composable
fun TvShowSummary(
    modifier: Modifier = Modifier,
    overview: String
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.summary),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = overview,
            style = MaterialTheme.typography.titleMedium,
            maxLines = Constants.FIVE,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview
@Composable
fun TvShowDetailsPreview() {
    TvShowSummary(
        modifier = Modifier.background(Color.White),
        overview = "Lorem ipsum dolor sit amet"
    )
}
