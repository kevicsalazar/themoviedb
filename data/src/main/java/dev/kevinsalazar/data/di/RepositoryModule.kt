package dev.kevinsalazar.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.kevinsalazar.data.repositories.DefaultTvShowRepository
import dev.kevinsalazar.domain.repositories.TvShowRepository

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindTvShowRepository(repository: DefaultTvShowRepository): TvShowRepository

}
