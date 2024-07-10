package dev.kevinsalazar.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.kevinsalazar.data.mapper.toDomain
import domain.entities.TvShow
import dev.kevinsalazar.data.networking.model.TvShowMainResponse
import io.ktor.client.plugins.ClientRequestException
import io.ktor.serialization.JsonConvertException
import io.ktor.utils.io.errors.IOException

class TvShowPagingSource(
    private val fetcher: suspend (Int) -> TvShowMainResponse
) : PagingSource<Int, TvShow>() {

    companion object {
        const val INDEX_ONE = 1
        const val ITEMS_PER_PAGE = 20
    }

    override fun getRefreshKey(state: PagingState<Int, TvShow>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(INDEX_ONE)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(INDEX_ONE)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShow> {
        val currentPage = params.key ?: INDEX_ONE
        return try {
            val response = fetcher.invoke(currentPage)
            LoadResult.Page(
                data = response.result.map { it.toDomain() },
                prevKey = if (currentPage == 1) null else (currentPage - 1),
                nextKey = if (currentPage == response.totalPages) null else (currentPage + 1)
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: ClientRequestException) {
            LoadResult.Error(e)
        } catch (e: JsonConvertException) {
            LoadResult.Error(e)
        }
    }
}
