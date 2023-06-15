package com.kenshi.domain.usecase

import androidx.paging.PagingData
import com.kenshi.domain.entity.video.VideoEntity
import com.kenshi.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVideoSearchListUseCase @Inject constructor(
    private val searchRepository: SearchRepository
){
    operator fun invoke(query: String, sortType: String): Flow<PagingData<VideoEntity>> {
        return searchRepository.getVideoSearchList(query, sortType)
    }
}