package com.kenshi.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kenshi.data.model.video.Video
import com.kenshi.data.service.SearchService
import com.kenshi.data.util.Constants.PAGING_SIZE
import com.kenshi.data.util.Constants.STARTING_PAGE_INDEX
import io.ktor.client.plugins.ResponseException
import java.io.IOException

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
            val response = searchService.getVideoSearchResponse(query, sort, pageNumber, params.loadSize)
            val endOfPaginationReached = response.meta.isEnd
            val data = response.documents
            val prevKey = if (pageNumber == STARTING_PAGE_INDEX)
                null else pageNumber - 1
            val nextKey = if (endOfPaginationReached) {
                null
            } else {
                pageNumber + (params.loadSize / PAGING_SIZE)
            }
            LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: ResponseException) {
            LoadResult.Error(exception)
        }
    }
}