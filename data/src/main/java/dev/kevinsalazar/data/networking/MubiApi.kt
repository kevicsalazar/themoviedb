package dev.kevinsalazar.data.networking

import dev.kevinsalazar.data.networking.model.TvShowDetailsResponse
import dev.kevinsalazar.data.networking.model.TvShowMainResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MubiApi {

    @GET("tv/{category}")
    suspend fun getTvShows(
        @Path("category") category: String,
        @Query("page") page: Int? = null,
        @Query("api_key") apiKey: String,
    ): TvShowMainResponse

    @GET("tv/{series_id}")
    suspend fun getTvShowDetail(
        @Path("series_id") seriesId: Int,
        @Query("api_key") apiKey: String,
    ): TvShowDetailsResponse
}
