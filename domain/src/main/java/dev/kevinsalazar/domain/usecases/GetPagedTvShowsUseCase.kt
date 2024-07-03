package dev.kevinsalazar.domain.usecases

import dev.kevinsalazar.domain.repositories.TvShowRepository
import javax.inject.Inject

class GetPagedTvShowsUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository
) {

    operator fun invoke(category: String) = tvShowRepository.getPagedTvShows(category)
}
