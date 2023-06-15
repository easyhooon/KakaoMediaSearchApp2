package com.kenshi.data.service

import com.kenshi.data.model.blog.BlogSearchResponse
import com.kenshi.data.model.image.ImageSearchResponse
import com.kenshi.data.model.video.VideoSearchResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchService @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun getBlogSearchResponse(
        query: String,
        sort: String,
        page: Int,
        size: Int,
    ): BlogSearchResponse {
        return httpClient.get("/v2/search/blog") {
            parameter("query", query)
            parameter("sort", sort)
            parameter("page", page)
            parameter("size", size)
        }.body()
    }

    suspend fun getImageSearchResponse(
        query: String,
        sort: String,
        page: Int,
        size: Int,
    ): ImageSearchResponse {
        return httpClient.get("/v2/search/image") {
            parameter("query", query)
            parameter("sort", sort)
            parameter("page", page)
            parameter("size", size)
        }.body()
    }

    suspend fun getVideoSearchResponse(
        query: String,
        sort: String,
        page: Int,
        size: Int,
    ): VideoSearchResponse {
        return httpClient.get("/v2/search/vclip") {
            parameter("query", query)
            parameter("sort", sort)
            parameter("page", page)
            parameter("size", size)
        }.body()
    }
}


