package dev.kevinsalazar.tmdb.screen.detail.model

import androidx.compose.runtime.Stable

@Stable
data class DetailsModel(
    val name: String,
    val backdropImagePath: String,
    val overview: String,
    val seasons: List<Season>,
    val originalName: String,
    val voteAverage: Float? = null
) {
    data class Season(
        val name: String,
        val overview: String,
        val posterImagePath: String,
        val episodeCount: Int
    )
}
