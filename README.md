# 카카오 API를 사용한 블로그/동영상/사진 검색 앱 
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-blue.svg)](https://kotlinlang.org)
[![Gradle](https://img.shields.io/badge/gradle-8.0-green.svg)](https://gradle.org/)
[![Android Studio](https://img.shields.io/badge/Android%20Studio-2022.3.1%20%28Giraff%29-green)](https://developer.android.com/studio)
[![minSdkVersion](https://img.shields.io/badge/minSdkVersion-23-red)](https://developer.android.com/distribute/best-practices/develop/target-sdk)
[![targetSdkVersion](https://img.shields.io/badge/targetSdkVersion-34-orange)](https://developer.android.com/distribute/best-practices/develop/target-sdk)
<br/>


# How to build #
AGP 버전 상, Android Studio Flamingo 버전 이상에서 Build가 가능합니다.

빌드 전, 'secrets.properties' 파일을 생성하여 다음과 같이 API KEY 값을 추가해야 합니다.
```
# secrets.properties

KAKAO_API_URL="https://dapi.kakao.com/"
KAKAO_API_KEY="KakaoAK {Kakao REST API KEY}
```

e.g.
```
# secrets.properties

KAKAO_API_URL="https://dapi.kakao.com/"
KAKAO_API_KEY="KakaoAK c9bf3b615072a170b64208b1eee62e0d"
```

# ToDo
- [x] 멀티 모듈화
- [x] secrets.properties 파일을 통해 API KEY를 관리 (공개 Repository에는 이 파일을 포함하지 않음)
- [x] Ktor를 이용한 네트워크 환경 구축 
- [x] Paging3 Library를 사용하여 미디어 검색 기능 구현 
- [x] 기존의 뷰 시스템을 통해 미디어 검색 화면 구성(블로그/동영상/사진 API 를 시용하여 각각의 탭 내에서 검색 결과 리스트 조회)
- [x] Jetpack Compose를 사용하여 미디어 검색 화면 구성(블로그/동영상/사진 API 를 시용하여 각각의 탭 내에서 검색 결과 리스트 조회)
- [x] Paging3 Library LoadState 를 이용한 인터넷 에러 핸들링
- [ ] 정렬 기능 구현 (최신순, 정확도순)
- [ ] 기존의 뷰의 Theme 을 Compose Theme 으로 그대로 옮겨, 똑같은 UI 만들기 
- [ ] Convention Plugin 적용
- [ ] 테스트 코드 작성 

## Features

## Development

### Environment

- IDE : Android Studio Giraffe
- JDK : Java 17을 실행할 수 있는 JDK
- Kotlin Language : 1.9

### Language

- Kotlin

### Libraries

- Androidx
  - Activity & Activity Compose
  - AppCompat
  - Core
  - ConstraintLayout
  - Lifecycle & AAC ViewModel Compose
  - Material3
  - StartUp
  - Splash
  - Paging3 & Paging3 Compose

- Kotlinx
  - Coroutines
  - Serialization

- Dagger Hilt
- Ktor
- Timber
- Coil & Coil Compose

#### Gradle Dependency

- [Gradle Version Catalog](https://docs.gradle.org/current/userguide/platforms.html)

## Architecture
Based on [Google App Architecture](https://developer.android.com/topic/architecture) similar to Clean Architecture

<img width="760" alt="image" src="https://github.com/easyhooon/watcha-assignment/assets/51016231/2837a3b6-32f8-46aa-a102-3fb5b3e3826a">

<img width="760" alt="image" src="https://github.com/easyhooon/watcha-assignment/assets/51016231/b29020a1-69aa-482b-8af4-ddb27122a440">

## Package Structure
```
├── app
│   └── application
├── build-logic
├── buildSrc
├── data
│   ├── datasource
│   ├── di
│   ├── mapper
│   ├── model
│   ├── paging
│   ├── repository
│   ├── paging
│   └── servce
├── domain
│   ├── entity
│   ├── repository
│   └── usecase
├── presentation
│   ├── ui
│   │   ├── compose
│   │   └── view
│   └── viewmodel
└── gradle
    └── libs.versions.toml
```


