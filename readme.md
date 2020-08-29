# 🥞 와플 Waple : 우리만의 플레이스

> 한개의 와플 빵 위에 각자의 취향에 맞는 토핑을 올려 먹듯,
> 하나의 지도에 모임별 기록을 담아내는 와플 서비스!!!

### [WaPle 놀러가기](http://i3a204.p.ssafy.io:8888)

<br><br>

# 😎 와플만의 특징

- **각자 모임별로 만드는 맞춤형 지도**
- 친구들과 함께 만드는 지도
- 그 시절, 그 상황이 기억나는 지도
- 그룹 내 멤버들끼리 지도 공유
- 직접 지정한 테마 별 지도 보기
- 방문했던 모든 장소에 대한 리뷰 남기기
- 간편하게 약속 잡기
- 캘린더에서 약속 모아보기

<br><br>

# 🤷 Why WaPle?

- 공유성
  - 원하는 장소를 원하는 그룹과 함께 볼 수 있다
  - 같은 그룹 내에서 쓰여진 리뷰를 읽을 수 있다
- 기록성
  - 그룹 내의 약속을 기록할 수 있다
  - 방문 내역을 리뷰 기능을 통해 기록할 수 있다
- 폐쇄성
  - 초대된 멤버만 그룹에 속할 수 있다

<br><br>

# 🚀 기능

## 검색
![검색](./docs/image/search.png)
- 카카오 지도 API 사용
- 장소의 위치와 정보를 확인할 수 있다

<br>

## 북마크 등록
![북마크 등록](./docs/image/addbookmark.png)
- 검색 후 그룹과 공유하고 싶은 장소를 북마크에 등록

<br>

## 테마별 필터 기능
![필터1](./docs/image/filter1.png)
![필터2](./docs/image/filter2.png)
- 등록된 북마크를 그룹, 테마별로 선택하여 볼 수 있다

<br>

## 북마크 수정
![북마크 수정](./docs/image/modifybookmark.PNG)
- 저장된 북마크는 다른 테마에 쉽게 추가할 수 있다
- 모든 선택이 해제되면 북마크 삭제가 가능하다

<br>

## 약속 만들기
![약속 만들기](./docs/image/appointment.png)
- 편리하게 그룹원들과 약속을 잡을 수 있다
- 약속 내에 여러 개의 장소를 추가할 수 있다

<br>

## 약속 장소 정하기
![장소 정하기](./docs/image/vote.png)
- 저장된 장소들 중 가고 싶은 곳에 투표를 할 수 있다
- 여러 장소에 투표가 가능하다

<br>

## 리뷰 쓰기
![리뷰 쓰기](./docs/image/reviewwrite.png)
- 저장한 장소에 대해 리뷰 작성을 할 수 있다
- 제목, 함께 다녀온 그룹, 방문 날짜, 내용, 사진을 넣을 수 있다

<br>

## 리뷰 읽기
![리뷰 읽기](./docs/image/reviewread.png)
- 본인이 속한 그룹의 리뷰들을 읽을 수 있다

<br>

## 리뷰에 등록된 사진 보기
![리뷰 사진 보기](./docs/image/reviewphoto.png)
- 리뷰에 등록된 사진은 확대하여 볼 수 있다

<br>

## 내 그룹
![내 그룹](./docs/image/group.PNG)
- 내가 속한 그룹의 정보를 볼 수 있다
- 그룹 이름 변경 가능
- 테마 추가, 수정 가능

<br>

## 멤버 초대하기
![멤버 초대하기](./docs/image/addmember.PNG)
- 카카오톡 메시지 공유를 통해 멤버를 초대할 수 있다

<br>

## 일정 확인하기
![일정 확인하기](./docs/image/appointmentlist.PNG)
- 달력을 통해 내가 속한 그룹의 약속을 확인할 수 있다
- 어떤 그룹과 어떤 장소에 가기로 했는지 확인할 수 있다

<br><br>

# 🔥 개발
## 👩‍👧‍👦 Team Toppings

- 박혜진
  - **팀장**
  - 사과잼
  - 서버
  - [github](https://github.com/du-dung)
- 오인호
  - 노잼
  - 서버
  - [github](https://github.com/kickrisk)
- 최윤주
  - 루비잼
  - 프론트
  - [github](https://github.com/yeomju311)
  - [blog](https://yeomju311.github.io/)

<br>

## 📅 개발기간
2020.07.07 ~ 2020.08.21

<br>

## 💻 사용 기술
### backend

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.3.1-brightgreen)
![Swagger2](https://img.shields.io/badge/Swagger2-3.0.0-green)
![MyBatis](https://img.shields.io/badge/MyBatis-2.1.3-red)
- `RESTful`
- `MariaDB`
- Library
  - Java-JWT
  - aspectj

<br>

### Frontend

![Vue](https://img.shields.io/badge/Vue-^2.6.11-brightgreen)
![Vue-router](https://img.shields.io/badge/Vue--router-%5E3.2.0-green)
![Vuex](https://img.shields.io/badge/Vuex-%5E3.4.0-yellowgreen)
![Vuetify](https://img.shields.io/badge/Vuetify-%5E2.2.11-9cf)
<br>
![Vue CLI](https://img.shields.io/badge/%40vue%2Fcli-~4.4.0-yellow)
![eslint-airbnb](https://img.shields.io/badge/%40vue%2Feslint--config--airbnb-%5E5.0.2-ff69b4)

- API
  - Kakao Login
  - Kakao Map
  - Kakao Link
- Library
  - axios
  - vue-session
  - moment
  - vue-toastification
  - vue-upload-multiple-image
  - vee-validate

<br>

### 관리

- docker
- git
- JIRA
- AWS EC2
- nginx