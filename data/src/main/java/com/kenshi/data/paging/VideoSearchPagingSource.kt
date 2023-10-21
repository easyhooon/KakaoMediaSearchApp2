package com.kenshi.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kenshi.data.model.video.Video
import com.kenshi.data.service.SearchService
import com.kenshi.data.util.Constants.PAGING_SIZE
import com.kenshi.data.util.Constants.STARTING_PAGE_INDEX
import io.ktor.client.plugins.ResponseException
import timber.log.Timber
import java.io.IOException
import java.nio.channels.UnresolvedAddressException

class VideoSearchPagingSource(
    private val searchService: SearchService,
    private val query: String,
    private val sort: String,
) : PagingSource<Int, Video>() {

    override fun getRefreshKey(state: PagingState<Int, Video>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Video> {
        return try {
            val pageNumber = params.key ?: STARTING_PAGE_INDEX
            val response =
                searchService.getVideoSearchResponse(query, sort, pageNumber, params.loadSize)

            val endOfPaginationReached = response.meta.isEnd

            LoadResult.Page(
                data = response.documents,
                prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber - 1,
                nextKey = if (endOfPaginationReached) {
                    null
                } else {
                    // initial load size = 3 * NETWORK_PAGE_SIZE
                    // ensure we're not requesting duplicating items, at the 2nd request
                    pageNumber + (params.loadSize / PAGING_SIZE)
                },
            )
        } catch (exception: IOException) {
            Timber.e(exception)
            LoadResult.Error(exception)
        } catch (exception: ResponseException) {
            Timber.e(exception)
            LoadResult.Error(exception)
        } catch (exception: UnresolvedAddressException) {
            Timber.e(exception)
            LoadResult.Error(exception)
        }
    }
}