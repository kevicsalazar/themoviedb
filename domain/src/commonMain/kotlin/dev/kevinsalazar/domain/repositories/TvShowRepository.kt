package dev.kevinsalazar.domain.repositories

import androidx.paging.PagingData
import dev.kevinsalazar.domain.entities.TvShow
import dev.kevinsalazar.domain.entities.TvShowDetails
import dev.kevinsalazar.domain.errors.DataError
import dev.kevinsalazar.domain.values.Result
import kotlinx.coroutines.flow.Flow

interface TvShowRepository {
    fun getPagedTvShows(category: String): Flow<PagingData<TvShow>>

    suspend fun getTvShowDetails(id: Int): Result<TvShowDetails, DataError>
}
