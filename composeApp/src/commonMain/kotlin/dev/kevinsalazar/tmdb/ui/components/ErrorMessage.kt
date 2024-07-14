package dev.kevinsalazar.tmdb.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import themoviedb.composeapp.generated.resources.Res
import themoviedb.composeapp.generated.resources.error_icon
import themoviedb.composeapp.generated.resources.retry
import themoviedb.composeapp.generated.resources.unexpected_error

@Composable
fun ErrorMessage(
    modifier: Modifier = Modifier,
    errorMessage: String?,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = stringResource(Res.string.error_icon),
            tint = Color.Red,
            modifier = Modifier.size(48.dp)
        )
        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            text = errorMessage ?: stringResource(Res.string.unexpected_error),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Button(
            onClick = { onRetry() }
        ) {
            Text(text = stringResource(Res.string.retry))
        }
    }
}

@Preview
@Composable
fun ErrorMessagePreview() {
    ErrorMessage(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        errorMessage = stringResource(Res.string.unexpected_error),
        onRetry = {}
    )
}
