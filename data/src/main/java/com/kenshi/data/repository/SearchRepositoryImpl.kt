package com.kenshi.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.kenshi.data.mapper.toEntity
import com.kenshi.data.source.local.SearchLocalDataSource
import com.kenshi.data.source.remote.SearchRemoteDataSource
import com.kenshi.domain.entity.blog.BlogEntity
import com.kenshi.domain.entity.image.ImageEntity
import com.kenshi.domain.entity.video.VideoEntity
import com.kenshi.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchRemoteDataSource: SearchRemoteDataSource,
    private val searchLocalDataSource: SearchLocalDataSource
): SearchRepository {

    override fun getBlogSearchList(
        query: String,
        sortType: String,
    ): Flow<PagingData<BlogEntity>> {
        return searchRemoteDataSource.getBlogSearchList(query, sortType).map { pagingData ->
            pagingData.map { blog ->
                blog.toEntity()
            }
        }
    }

    override fun getImageSearchList(
        query: String,
        sortType: String,
    ): Flow<PagingData<ImageEntity>> {
        return searchRemoteDataSource.getImageSearchList(query, sortType).map { pagingData ->
            pagingData.map { image ->
                image.toEntity()
            }
        }
    }

    override fun getVideoSearchList(
        query: String,
        sortType: String,
    ): Flow<PagingData<VideoEntity>> {
        return searchRemoteDataSource.getVideoSearchList(query, sortType).map { pagingData ->
            pagingData.map { video ->
                video.toEntity()
            }
        }
    }

    override fun getSortMode(): Flow<String> {
        return searchLocalDataSource.getSortMode()
    }
}