# 카카오 API를 사용한 블로그/동영상/사진 검색 앱 

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
- [ ] 기존의 뷰의 Theme 을 Compose Theme 으로 그대로 옮겨, 똑같은 UI 만들기 
- [ ] Paging3 Library LoadState 를 이용한 인터넷 에러 핸들링
- [ ] SortType(정확도순, 최신순) 변경하는 화면 구현 
- [ ] Convention Plugin 적용
- [ ] 테스트 코드 작성 

