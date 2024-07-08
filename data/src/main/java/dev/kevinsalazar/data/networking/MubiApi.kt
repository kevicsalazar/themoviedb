package dev.kevinsalazar.data.networking

import dev.kevinsalazar.data.networking.model.TvShowDetailsResponse
import dev.kevinsalazar.data.networking.model.TvShowMainResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.koin.core.parameter.parametersOf

class MubiApi(
    private val client: HttpClient
) {

    suspend fun getTvShows(
        category: String,
        page: Int? = null
    ) =
        client.get("tv/$category") {
            parametersOf(
                "page" to page
            )
            contentType(ContentType.Application.Json)
        }.body<TvShowMainResponse>()

    suspend fun getTvShowDetail(
        seriesId: Int,
    ) = client.get("tv/$seriesId") {
        contentType(ContentType.Application.Json)
    }.body<TvShowDetailsResponse>()
}
