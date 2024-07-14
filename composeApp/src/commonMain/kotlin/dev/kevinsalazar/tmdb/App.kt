package dev.kevinsalazar.tmdb

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.kevinsalazar.data.di.dataModule
import dev.kevinsalazar.domain.di.domainModule
import dev.kevinsalazar.tmdb.di.appModule
import dev.kevinsalazar.tmdb.navigation.MainNavHost
import dev.kevinsalazar.tmdb.ui.theme.TmdbTheme
import org.koin.compose.KoinApplication


@Composable
fun App() {
    KoinApplication(
        application = {
            modules(
                appModule,
                dataModule,
                domainModule
            )
        }
    ) {
        TmdbTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                MainNavHost(
                    modifier = Modifier
                )
            }
        }
    }
}
