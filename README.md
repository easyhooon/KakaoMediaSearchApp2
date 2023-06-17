# 카카오 API를 사용한 블로그/동영상/사진 검색 앱 

# How to build #
AGP 버전 상, Android Studio Flamingo 버전 이상에서 Build가 가능합니다.

빌드 전, 'secrets.defaults.properties'에 다음과 같이 API KEY 값을 추가해야 합니다.
```
# secrets.defaults.properties
KAKAO_API_URL="https://dapi.kakao.com/"
KAKAO_API_KEY="KakaoAK {Kakao API KEY}
```

e.g.
```
# secrets.defaults.properties
KAKAO_API_URL="https://dapi.kakao.com/"
KAKAO_API_KEY="KakaoAK d87cd5634a458257e3d4asdens2f35"
```

# ToDo
- [x] 멀티 모듈화
- [x] secrets.defaults.properties 파일을 통해 API KEY를 관리 (공개 Repository에는 이 파일을 포함하지 않음)
- [x] Ktor를 이용한 네트워크 환경 구축 
- [x] Paging3 Library를 사용하여 미디어 검색 기능 구현 
- [x] 기존의 뷰 시스템을 통해 미디어 검색 화면 구성(블로그/동영상/사진 API 를 시용하여 각각의 탭 내에서 검색 결과 리스트 조회)
- [ ] Jetpack Compose를 사용하여 미디어 검색 화면 구성(블로그/동영상/사진 API 를 시용하여 각각의 탭 내에서 검색 결과 리스트 조회) 
- [ ] Convention Plugin 적용
- [ ] 테스트 코드 작성 

