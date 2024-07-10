package domain.di

import domain.usecases.GetPagedTvShowsUseCase
import domain.usecases.GetTvShowDetailsUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::GetPagedTvShowsUseCase)
    factoryOf(::GetTvShowDetailsUseCase)
}
