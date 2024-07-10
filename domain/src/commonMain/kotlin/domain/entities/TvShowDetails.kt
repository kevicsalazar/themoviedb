package domain.entities

data class TvShowDetails(
    val name: String? = null,
    val backdropImagePath: String? = null,
    val overview: String? = null,
    val seasons: List<Season>? = emptyList(),
    val originalName: String? = null,
    val voteAverage: Float? = null
)
