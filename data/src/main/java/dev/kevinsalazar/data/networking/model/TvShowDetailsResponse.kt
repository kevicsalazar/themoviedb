package dev.kevinsalazar.data.networking.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvShowDetailsResponse(
    val adult: Boolean,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    val id: Int,
    val name: String,
    @SerialName("original_name")
    val originalName: String,
    val overview: String,
    val seasons: List<Season>,
    @SerialName("vote_average")
    val voteAverage: Float
) {
    @Serializable
    data class Season(
        @SerialName("episode_count")
        val episodeCount: Int,
        val id: Int,
        val name: String,
        val overview: String,
        @SerialName("poster_path")
        val posterPath: String?
    )
}
