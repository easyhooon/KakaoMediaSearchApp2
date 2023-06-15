package com.kenshi.domain.usecase

import androidx.paging.PagingData
import com.kenshi.domain.entity.image.ImageEntity
import com.kenshi.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImageSearchListUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    operator fun invoke(query: String, sortType: String): Flow<PagingData<ImageEntity>> {
        return searchRepository.getImageSearchList(query, sortType)
    }
}