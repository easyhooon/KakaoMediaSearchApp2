package com.kenshi.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kenshi.data.model.image.Image
import com.kenshi.data.service.SearchService
import com.kenshi.data.util.Constants.PAGING_SIZE
import com.kenshi.data.util.Constants.STARTING_PAGE_INDEX
import io.ktor.client.plugins.ResponseException
import timber.log.Timber
import java.io.IOException
import java.nio.channels.UnresolvedAddressException

class ImageSearchPagingSource(
    private val searchService: SearchService,
    private val query: String,
    private val sort: String,
) : PagingSource<Int, Image>() {

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        return try {
            val pageNumber = params.key ?: STARTING_PAGE_INDEX
            val response =
                searchService.getImageSearchResponse(query, sort, pageNumber, params.loadSize)
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