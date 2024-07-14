package dev.kevinsalazar.tmdb.di

import dev.kevinsalazar.tmdb.screen.detail.DetailsMapper
import dev.kevinsalazar.tmdb.screen.detail.DetailsViewModel
import dev.kevinsalazar.tmdb.screen.home.HomeMapper
import dev.kevinsalazar.tmdb.screen.home.HomeViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val appModule = module {

    viewModelOf(::HomeViewModel)
    viewModel { (id: Int) -> DetailsViewModel(id, get(), get())}

    factoryOf(::HomeMapper)
    factoryOf(::DetailsMapper)

}
