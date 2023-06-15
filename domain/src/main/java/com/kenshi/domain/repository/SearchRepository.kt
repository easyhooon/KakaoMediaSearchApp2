package com.kenshi.domain.repository

import androidx.paging.PagingData
import com.kenshi.domain.entity.blog.BlogEntity
import com.kenshi.domain.entity.image.ImageEntity
import com.kenshi.domain.entity.video.VideoEntity
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun getImageSearchList(
        query: String,
        sortType: String,
    ): Flow<PagingData<ImageEntity>>

    fun getVideoSearchList(
        query: String,
        sortType: String,
    ): Flow<PagingData<VideoEntity>>

    fun getBlogSearchList(
        query: String,
        sortType: String,
    ): Flow<PagingData<BlogEntity>>

    fun getSortMode(): Flow<String>
}