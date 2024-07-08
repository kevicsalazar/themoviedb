package dev.kevinsalazar.domain.usecases

import dev.kevinsalazar.domain.repositories.TvShowRepository

class GetTvShowDetailsUseCase(
    private val tvShowRepository: TvShowRepository
) {

    suspend operator fun invoke(id: Int) = tvShowRepository.getTvShowDetails(id)
}
