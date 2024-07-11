package dev.kevinsalazar.domain.usecases

import dev.kevinsalazar.domain.repositories.TvShowRepository

class GetPagedTvShowsUseCase(
    private val tvShowRepository: TvShowRepository
) {

    operator fun invoke(category: String) = tvShowRepository.getPagedTvShows(category)
}
