package dev.kevinsalazar.data.di

import dev.kevinsalazar.data.networking.MubiApi
import dev.kevinsalazar.data.networking.restHttpClient
import dev.kevinsalazar.data.repositories.DefaultTvShowRepository
import dev.kevinsalazar.domain.repositories.TvShowRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module

val dataModule = module {

    single { MubiApi(restHttpClient) }

    factoryOf(::DefaultTvShowRepository) withOptions { bind<TvShowRepository>() }
}
