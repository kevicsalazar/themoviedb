package dev.kevinsalazar.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.kevinsalazar.data.mapper.toDomain
import dev.kevinsalazar.data.networking.MubiApi
import dev.kevinsalazar.data.networking.model.TvShowMainResponse
import dev.kevinsalazar.data.paging.TvShowPagingSource
import dev.kevinsalazar.data.paging.TvShowPagingSource.Companion.ITEMS_PER_PAGE
import dev.kevinsalazar.domain.entities.TvShow
import dev.kevinsalazar.domain.entities.TvShowDetails
import dev.kevinsalazar.domain.errors.DataError
import dev.kevinsalazar.domain.repositories.TvShowRepository
import dev.kevinsalazar.domain.values.Result
import io.ktor.client.plugins.ClientRequestException
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.Flow

class DefaultTvShowRepository(
    private val mubiApi: MubiApi
) : TvShowRepository {

    override fun getPagedTvShows(category: String): Flow<PagingData<TvShow>> {
        return handlePagedRequest { page ->
            mubiApi.getTvShows(
                category = category,
                page = page
            )
        }
    }

    override suspend fun getTvShowDetails(id: Int): Result<TvShowDetails, DataError> {
        return handleRequest {
            mubiApi.getTvShowDetail(
                seriesId = id
            ).toDomain()
        }
    }

    private fun handlePagedRequest(
        fetcher: suspend (Int) -> TvShowMainResponse
    ): Flow<PagingData<TvShow>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                TvShowPagingSource(fetcher = fetcher)
            }
        ).flow
    }

    private suspend fun <T> handleRequest(fetcher: suspend () -> T): Result<T, DataError> {
        return try {
            Result.Success(fetcher.invoke())
        } catch (e: IOException) {
            Result.Error(DataError.Network.NO_INTERNET, e.stackTraceToString())
        } catch (e: ClientRequestException) {
            Result.Error(DataError.Network.SERVER_ERROR, e.stackTraceToString())
        }
    }
}
