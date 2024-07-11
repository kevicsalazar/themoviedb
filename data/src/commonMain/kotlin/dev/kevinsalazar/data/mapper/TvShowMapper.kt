package dev.kevinsalazar.data.mapper

import dev.kevinsalazar.data.networking.model.TvShowResponse
import dev.kevinsalazar.data.utils.constructMediumImageUrl
import domain.entities.TvShow


fun TvShowResponse.toDomain() = TvShow(
    tvShowId = id,
    name = name,
    originalName = originalName,
    posterPath = posterPath?.constructMediumImageUrl()
)
