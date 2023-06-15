package com.kenshi.domain.usecase

import com.kenshi.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSortModeUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    operator fun invoke(): Flow<String> {
        return searchRepository.getSortMode()
    }
}