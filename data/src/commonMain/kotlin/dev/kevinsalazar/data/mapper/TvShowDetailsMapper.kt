package dev.kevinsalazar.data.mapper

import domain.entities.Season
import domain.entities.TvShowDetails
import dev.kevinsalazar.data.networking.model.TvShowDetailsResponse
import dev.kevinsalazar.data.utils.constructMediumImageUrl
import dev.kevinsalazar.data.utils.constructOriginalImageUrl


fun TvShowDetailsResponse.toDomain(): TvShowDetails {
    return TvShowDetails(
        name = name,
        overview = overview,
        seasons = seasons.map { it.toDomain() },
        backdropImagePath = backdropPath?.constructOriginalImageUrl(),
        voteAverage = voteAverage,
        originalName = originalName
    )
}

private fun TvShowDetailsResponse.Season.toDomain(): Season {
    return Season(
        name = name,
        overview = overview,
        posterImagePath = posterPath?.constructMediumImageUrl(),
        episodeCount = episodeCount
    )
}
