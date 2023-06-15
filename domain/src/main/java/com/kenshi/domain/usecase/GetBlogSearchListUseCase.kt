package com.kenshi.domain.usecase

import androidx.paging.PagingData
import com.kenshi.domain.entity.blog.BlogEntity
import com.kenshi.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBlogSearchListUseCase @Inject constructor(
    private val searchRepository: SearchRepository
){
    operator fun invoke(query: String, sortType: String): Flow<PagingData<BlogEntity>> {
        return searchRepository.getBlogSearchList(query, sortType)
    }
}