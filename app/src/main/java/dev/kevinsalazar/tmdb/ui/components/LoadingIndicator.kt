package dev.kevinsalazar.tmdb.ui.components

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag

@Composable
fun LoadingIndicator(
    modifier: Modifier,
    isLoading: Boolean = true
) {
    if (isLoading) {
        Surface(
            modifier = modifier,
            color = Color.Transparent
        ) {
            CircularProgressIndicator(
                modifier = Modifier.wrapContentSize(Alignment.Center)
                    .testTag("loading"),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
