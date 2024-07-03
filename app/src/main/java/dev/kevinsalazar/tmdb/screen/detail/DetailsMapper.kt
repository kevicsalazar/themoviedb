package dev.kevinsalazar.tmdb.screen.detail

import  dev.kevinsalazar.tmdb.screen.detail.model.DetailsModel
import dev.kevinsalazar.domain.entities.Season
import dev.kevinsalazar.domain.entities.TvShowDetails
import javax.inject.Inject

class DetailsMapper @Inject constructor() {

    fun map(details: TvShowDetails): DetailsModel {

        val ratingFactor = RATING_TOTAL / MAX_VOTE

        return DetailsModel(
            name = details.name.orEmpty(),
            overview = details.overview.orEmpty(),
            seasons = details.seasons?.map(::map) ?: emptyList(),
            backdropImagePath = details.backdropImagePath.orEmpty(),
            originalName = details.originalName.orEmpty(),
            voteAverage = details.voteAverage?.times(ratingFactor)
        )
    }

    private fun map(season: Season): DetailsModel.Season {
        return DetailsModel.Season(
            name = season.name.orEmpty(),
            overview = season.overview.orEmpty(),
            posterImagePath = season.posterImagePath.orEmpty(),
            episodeCount = season.episodeCount ?: 1
        )
    }

    companion object {
        const val MAX_VOTE = 10f
        const val RATING_TOTAL = 5f
    }
}
