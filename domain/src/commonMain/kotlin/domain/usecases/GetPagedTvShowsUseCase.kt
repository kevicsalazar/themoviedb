package domain.usecases

import domain.repositories.TvShowRepository

class GetPagedTvShowsUseCase(
    private val tvShowRepository: TvShowRepository
) {

    operator fun invoke(category: String) = tvShowRepository.getPagedTvShows(category)
}
