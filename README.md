# MiniProject

<p align="center">
<img src="./md_attachment/Logo.png" width="300" height="300">
</p>  

<div align="center">

[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2FGomDiing%2FKh_Miniproject&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)

</div>

<br>

## 01. MovieScript 
> 학원 미니 프로젝트
>
> 개발 기간 : 2022.10 ~ 2022.11  

<br>

### 배포 주소
> http://www.moviescript.site  

<br>


### 개발팀 소개
|백엔드|프론트엔드|프론트엔드|프론트엔드|프론트엔드|
|:-----:|:-----:|:-----:|:-----:|:-----:|
| <img src="./md_attachment/profile_GomDiing.jpeg" width="120"/>|<img src="./md_attachment/profile_hjm8727.png" width="120"/>|<img src="./md_attachment/profile_hrhr7.png" width="120"/>|<img src="./md_attachment/profile_jimin0601.png" width="120"/>|<img src="./md_attachment/profile_Ryel1580.jpeg" width="120"/>|
|[GomDiing](https://github.com/GomDiing)|[hjm8727](https://github.com/hjm8727)|[hrhr7](https://github.com/hrhr7)|[jimin0601](https://github.com/jimin0601)|[Ryel1580](https://github.com/Ryel1580)|  

<br>

### 프로젝트 소개
비회원도 영화 정보를 열람할 수 있는 해외 사이트 [IMDB](https://www.imdb.com/)에서 아이디어 착안했습니다. 영화를 추천받고 관련 정보와 리뷰를 볼 수 있는 사이트를 목표로 한 프로젝트입니다.

<br>

## 02. 시작 가이드

> [!CAUTION]
> 보안 문제로 Backend & Frontend & Python 설정 파일을 업로드하지 않았습니다.  
> 제대로 작동하지 않으니 [배포 주소](#배포-주소)로 접속해주세요.


### 요구사항
해당 프로젝트는 다음과 같은 Application이 필요합니다.
- npm
- Node.js
  
#### 설치

```
$ git clone https://github.com/GomDiing/Kh_Miniproject.git  
$ cd Kh_Miniproject
```  

#### Backend
```
$ cd spring
$ ./gradlew build
$ cd build/libs
$ java -jar demo-0.0.1-SNAPSHOT.jar
```
 
#### Frontend
```
$ cd frontend
$ npm install
$ yarn start
```

#### Python
````
$ cd python
$ pip3 install requests flask sqlalchemy==1.4.4 apscheduler mysql-connector
$ python apiMain.py
````

<br>

## 03. 기술 스택
<h3 align="left"> Tools </h3>
<div align="left">
<img src="https://img.shields.io/badge/DBeaver-382923?style=for-the-badge&logo=DBeaver&logoColor=white" />&nbsp
<img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white" />&nbsp
<img src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white" />&nbsp
<img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=Postman&logoColor=white" />&nbsp
<img src="https://img.shields.io/badge/Visual%20Studio%20Code-0078d7.svg?style=for-the-badge&logo=visual-studio-code&logoColor=white"/>&nbsp
<img src="https://img.shields.io/badge/Intellij IDEA-000000?style=for-the-badge&logo=Intellij IDEA&logoColor=white" />&nbsp
</div>

<h3 align="left"> Frontend </h3>
<div align="left">
<img src="https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=React&logoColor=white" />&nbsp
<img src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=HTML5&logoColor=white" />&nbsp
<img src="https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=CSS3&logoColor=white" />&nbsp
<img src="https://img.shields.io/badge/Bootstrap-7952B3?style=for-the-badge&logo=Bootstrap&logoColor=white" />&nbsp
<img src="https://img.shields.io/badge/Ant Design-0170FE?style=for-the-badge&logo=Ant Design&logoColor=white" />&nbsp
</div>

<h3 align="left"> Backend </h3>
<div align="left">
<img src="https://img.shields.io/badge/Java-000000?style=for-the-badge&logo=OpenJDK&logoColor=white" />&nbsp
<img src="https://img.shields.io/badge/Python-3776AB?style=for-the-badge&logo=Python&logoColor=white" />&nbsp
<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white" />&nbsp
<img src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=for-the-badge&logoColor=white" />&nbsp
<img src="https://img.shields.io/badge/SQLAlchemy-D71F00?style=for-the-badge&logo=SqlAlchemy&logoColor=white" />&nbsp
<img src="https://img.shields.io/badge/Flask-000000?style=for-the-badge&logo=Flask&logoColor=white" />&nbsp
</div>

<h3 align="left"> Deploy </h3>
<div align="left">
<img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white" />&nbsp
<img src="https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=MariaDB&logoColor=white" />&nbsp
</div>

<br>

## 04. 화면 구성
|메인 페이지|영화 페이지|
|:---:|:---:|
|![image](./md_attachment/page_main.png)|![image](./md_attachment/page_movie-detail.png)|
|조회 페이지|리뷰 페이지|
|![image](./md_attachment/page_inquiry-category.png)|![image](./md_attachment/page_review.png)

<br>

## 05. API 주소

> [!NOTE]
> API 문서는 [해당 링크](https://telling-list-c32.notion.site/API-44cc96085fb246f6a5c635ece5276122)에서 자세히 보실 수 있습니다.

|API 목차|API 상세|
|:---:|:---:|
|<img src="./md_attachment/page_api-toc.png" width="210"/>|<img src="./md_attachment/page_api-detail.png" width="210"/>|
|API 히스토리|API 요구사항|
|<img src="./md_attachment/page_api-history.png" width="210"/>|<img src="./md_attachment/page_api-require.png" width="210"/>|

<br>

## 06. 주요 기능
1. 카테고리별 분류를 통한 영화 추천
    - 최신 / 인기 / 최고 평점 / 개봉 예정작 4개 카테고리를 분류
    - 각 카테고리별 추천 영화 
2. 비회원도 무료로 사용 가능
    - 기본적인 기능은 비회원인 상태에서도 사용 가능
3. 리뷰 작성 기능 제공
    - 회원 가입 기능을 통해 리뷰 작성 가능

<br>

## 07. 아키텍쳐
<div align="center">
<img src="./md_attachment/architecture.png" width="480">
</div>

```
.
├── md_attachment       // README 첨부파일 폴더
├── python              // Python 폴더, TMDB API 서버 기능
├── react               // Frontend 폴더
│   ├── public
│   └── src 
│       ├── api
│       ├── images
│       ├── pages
│       │   ├── Cards
│       │   ├── Category
│       │   ├── Login
│       │   ├── MainPage
│       │   ├── Menu
│       │   └── MyPage
│       └── util
└── spring              // Backend 폴더
    ├── build
    ├── gradle
    └── src             // Spring 소스코드
        └── main/java/comprehensive/demo
            └── demo
                ├── controller     
                ├── dto             
                ├── entity
                ├── exception       // 예외처리
                ├── interceptor     // 로그 기록
                ├── repository
                ├── response        // 응답
                ├── service
                ├── test
                └── vo

```

<br>

## 08. 개발 문서
### ERD 다이어그램
<div align="center">
<img src="./md_attachment/erd_diagram.png" width="360"/>
</div>

### 플로우차트
<div align="center">
<img src="./md_attachment/flowchart.jpg" width="360"/>
</div>

### 프로세스 회원가입
<div align="center">
<img src="./md_attachment/process.jpg" width="360"/>
</div>


### 프로젝트 진행도

> [!NOTE]
> 프로젝트 진행도는 [해당 링크](https://telling-list-c32.notion.site/5510047431ae46a5b029100433bb5679?pvs=25)에서 자세히 보실 수 있습니다.


![image](./md_attachment/project_progress.png)

### 프로젝트 회고

> [!NOTE]
> 프로젝트 회고는 [해당 링크](https://gomdiing.github.io/miniproject/miniproject_artifact/)에서 자세히 보실 수 있습니다.

![image](./md_attachment/review.png)