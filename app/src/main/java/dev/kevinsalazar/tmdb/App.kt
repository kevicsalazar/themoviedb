package dev.kevinsalazar.tmdb

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dev.kevinsalazar.data.di.dataModule
import dev.kevinsalazar.domain.di.domainModule
import dev.kevinsalazar.tmdb.di.appModule
import dev.kevinsalazar.tmdb.navigation.MainNavHost
import dev.kevinsalazar.tmdb.ui.theme.TmdbTheme
import dev.kevinsalazar.tmdb.utils.NavControllerProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                appModule,
                dataModule,
                domainModule
            )
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TmdbTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavControllerProvider(
                        navController = navController
                    ) {
                        MainNavHost(
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }
}
