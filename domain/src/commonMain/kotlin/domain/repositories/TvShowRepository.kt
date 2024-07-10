package domain.repositories

import androidx.paging.PagingData
import domain.entities.TvShow
import domain.entities.TvShowDetails
import domain.errors.DataError
import domain.values.Result
import kotlinx.coroutines.flow.Flow

interface TvShowRepository {
    fun getPagedTvShows(category: String): Flow<PagingData<TvShow>>

    suspend fun getTvShowDetails(id: Int): Result<TvShowDetails, DataError>
}
