package dev.kevinsalazar.domain.usecases

import dev.kevinsalazar.domain.repositories.TvShowRepository
import javax.inject.Inject

class GetTvShowDetailsUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository
) {

    suspend operator fun invoke(id: Int) = tvShowRepository.getTvShowDetails(id)
}
