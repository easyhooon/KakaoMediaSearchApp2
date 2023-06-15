package com.kenshi.data.source.local

import kotlinx.coroutines.flow.Flow

interface SearchLocalDataSource {

    suspend fun saveSortMode(mode: String)

    fun getSortMode(): Flow<String>
}