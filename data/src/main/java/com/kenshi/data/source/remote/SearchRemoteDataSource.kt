package com.kenshi.data.source.remote

import androidx.paging.PagingData
import com.kenshi.data.model.blog.Blog
import com.kenshi.data.model.image.Image
import com.kenshi.data.model.video.Video
import kotlinx.coroutines.flow.Flow

interface SearchRemoteDataSource {

    fun getBlogSearchList(
        query: String,
        sortType: String,
    ): Flow<PagingData<Blog>>

    fun getImageSearchList(
        query: String,
        sortType: String,
    ): Flow<PagingData<Image>>

    fun getVideoSearchList(
        query: String,
        sortType: String,
    ): Flow<PagingData<Video>>
}