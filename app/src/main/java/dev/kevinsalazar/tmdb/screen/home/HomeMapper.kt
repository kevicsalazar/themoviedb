package dev.kevinsalazar.tmdb.screen.home

import  dev.kevinsalazar.tmdb.screen.home.model.TvShowModel
import dev.kevinsalazar.domain.entities.TvShow
import javax.inject.Inject

class HomeMapper @Inject constructor() {

    fun map(tvShow: TvShow): TvShowModel {
        return TvShowModel(
            tvShowId = tvShow.tvShowId,
            name = tvShow.name,
            originalName = tvShow.originalName,
            posterPath = tvShow.posterPath
        )
    }

}
