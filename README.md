## 💻 서비스명
### Techno Market
<br>

## 🎯 서비스 설명
- 중고 전자기기를 손쉽게 사고팔 수 있는 웹 서비스로, 불필요한 전자기기를 판매하거나 필요한 제품을 저렴하게 구매할 수 있는 편리한 웹 서비스 입니다.
<br>

## 🛠 개발환경
- 운영체제 : 통합개발환경(IDE) : Intellij
- JDK버전 : JDK17
- DB : MariaDB
- 빌드 툴 / 관리 툴 : Gradle, GitHub
- front : svelte
- backend : java
<br>

## 👨‍💻 팀원
### 😁고광완(조장)
- 프로젝트 총괄
- ppt
- 백엔드
  - 게시글
  - 로그인

### 😎김경호(팀원)
- 프론트
  - 디자인
  - 퍼블리싱
- 백엔드
  - 회원가입
  - 로그인

### 😆서정헌(팀원)
- ppt
- 백엔드
  - 검색
  - 로그인
<br>

## 🌫 흐름도

![흐름도](https://github.com/Techno-Market/techno_market/assets/144635967/e09f813d-4313-4cab-9b5e-4ec6dc58076d)
<br>

## ☁️ ERD


<br>

## 🔥 트러블 슈팅

### 🚨 Issue 1(고광완)

## 🛑 원인
- 저번 프로젝트를 참고해서 핸들러 메소드를 추가하여 정적 리소스 경로가 아닌 컨트롤러의 메소드를 통해 처리하도록 수정하면서 경로가 변경되었지만 프론트에서 해당 경로 미적용

## 🚥 해결
- 핸들러 메소드에서 핸들링하는 mapping url의 경로에 맞게 프론트에서의 경로 수정
<br>

### 🚨 Issue 2(고광완)

## 🛑 원인
- 게시물 삭제 시 댓글이 존재하는 경우 Foreign key 제약 조건으로 인해 오류 발생

## 🚥 해결
- 엔티티의 필드에  cascade = CascadeType.REMOVE 추가해서 해결
<br>

### 🚨 Issue 3(김경호)

## 🛑 원인
- 기존에 jquery를 주로 썼었는데, svelte라는 프론트 언어를 사용하게 되었는데, jquery가 적용되지 않았다.

## 🚥 해결
- 서칭해보니 jquery를 사용하는 것보다 네이티브 javaScript 또는 svelte의 기능을 활용하는 것이 더 가볍고 최신 웹 개발 표준을 준수하는 방법이래서, 기존에 사용했던 jquery로 개발한 코드를 svelte 문법으로 수정하였다.
<br>

### 🚨 Issue 4(김경호)

## 🛑 원인
- 회원정보 수정 작업을 하는데 회원 password 일치 여부를 확인하려는데 자꾸 500에러가 떴다.

## 🚥 해결
- 다른 필드 일치 여부와 마찬가지로 GET 요청을 하고 있었는데, password같이 민감한 정보는 POST 요청을 해야된다고 해서 POST 요청으로 바꾸니 해결되었다.
<br>

### 🚨 Issue 5(서정헌)

## 🛑 원인
- 

## 🚥 해결
- 
<br>

### 🚨 Issue 6(서정헌)

## 🛑 원인
- 

## 🚥 해결
- 
<br>

