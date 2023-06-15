package com.kenshi.data.di

import com.kenshi.data.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.net.URL
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideKtorHttpClient(): HttpClient {
        // CIO(Coroutine I/O) 를 client 엔진으로 사용
        return HttpClient(CIO) {
            defaultRequest {
                header("Authorization", BuildConfig.KAKAO_API_KEY)
                url {
                    val parsedUrl = URL(BuildConfig.KAKAO_API_URL)
                    protocol = URLProtocol.HTTPS
                    host = parsedUrl.host
                }
            }
            install(ContentNegotiation) {
                json(Json {
                    // 기본 값을 가진 프로퍼티를 JSON에 포함시킴
                    encodeDefaults = true
                    // 알 수 없는 값 무시(모델에 없고, json에 있는 경우)
                    ignoreUnknownKeys = true
                    // Json String 이쁘게 출력
                    prettyPrint = true
                    // 따옴표 규칙 완화(RFC-4627)
                    isLenient = true
                })
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.BODY
            }
            install(HttpTimeout) {
                socketTimeoutMillis = 15_000
                requestTimeoutMillis = 15_000
                connectTimeoutMillis = 15_000
            }
        }
    }
}