package com.kenshi.data.source.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kenshi.data.model.blog.Blog
import com.kenshi.data.model.image.Image
import com.kenshi.data.model.video.Video
import com.kenshi.data.paging.BlogSearchPagingSource
import com.kenshi.data.paging.ImageSearchPagingSource
import com.kenshi.data.paging.VideoSearchPagingSource
import com.kenshi.data.service.SearchService
import com.kenshi.data.util.Constants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRemoteDataSourceImpl @Inject constructor(
    private val searchService: SearchService
) : SearchRemoteDataSource {

    override fun getBlogSearchList(
        query: String,
        sortType: String,
    ): Flow<PagingData<Blog>> {
        val pagingSourceFactory = { BlogSearchPagingSource(searchService, query, sortType) }

        return Pager(
            // pager 를 구현하기 위해서는
            // pagingConfig 를 통해 parameter 를 전달 해줘야함
            config = PagingConfig(
                // 어떤 기기로 동작 시키든 뷰홀더에 표시할 데이터가 모자르지 않을 정도의 값으로 설정
                pageSize = Constants.PAGING_SIZE,
                // true -> repository 의 전체 데이터 사이즈를 받아와서 recyclerview 의 placeholder 를 미리 만들어 놓음
                // 화면에 표시 되지 않는 항목은 null로 표시
                // 필요할 때 필요한 만큼만 로딩 하려면 false
                enablePlaceholders = false,
            ),
            // api 호출 결과를 팩토리에 전달
            pagingSourceFactory = pagingSourceFactory
            // 결과를 flow 로 변환
        ).flow
    }

    override fun getImageSearchList(
        query: String,
        sortType: String,
    ): Flow<PagingData<Image>> {
        val pagingSourceFactory = { ImageSearchPagingSource(searchService, query, sortType) }

        return Pager(
            // pager 를 구현하기 위해서는
            // pagingConfig 를 통해 parameter 를 전달 해줘야함
            config = PagingConfig(
                // 어떤 기기로 동작 시키든 뷰홀더에 표시할 데이터가 모자르지 않을 정도의 값으로 설정
                pageSize = Constants.PAGING_SIZE,
                // true -> repository 의 전체 데이터 사이즈를 받아와서 recyclerview 의 placeholder 를 미리 만들어 놓음
                // 화면에 표시 되지 않는 항목은 null로 표시
                // 필요할 때 필요한 만큼만 로딩 하려면 false
                enablePlaceholders = false,
            ),
            // api 호출 결과를 팩토리에 전달
            pagingSourceFactory = pagingSourceFactory
            // 결과를 flow 로 변환
        ).flow
    }

    override fun getVideoSearchList(
        query: String,
        sortType: String,
    ): Flow<PagingData<Video>> {
        val pagingSourceFactory = { VideoSearchPagingSource(searchService, query, sortType) }

        return Pager(
            // pager 를 구현하기 위해서는
            // pagingConfig 를 통해 parameter 를 전달 해줘야함
            config = PagingConfig(
                // 어떤 기기로 동작 시키든 뷰홀더에 표시할 데이터가 모자르지 않을 정도의 값으로 설정
                pageSize = Constants.PAGING_SIZE,
                // true -> repository 의 전체 데이터 사이즈를 받아와서 recyclerview 의 placeholder 를 미리 만들어 놓음
                // 화면에 표시 되지 않는 항목은 null로 표시
                // 필요할 때 필요한 만큼만 로딩 하려면 false
                enablePlaceholders = false,
            ),
            // api 호출 결과를 팩토리에 전달
            pagingSourceFactory = pagingSourceFactory
            // 결과를 flow 로 변환
        ).flow
    }
}