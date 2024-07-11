package dev.kevinsalazar.domain.di

import dev.kevinsalazar.domain.usecases.GetPagedTvShowsUseCase
import dev.kevinsalazar.domain.usecases.GetTvShowDetailsUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::GetPagedTvShowsUseCase)
    factoryOf(::GetTvShowDetailsUseCase)
}
