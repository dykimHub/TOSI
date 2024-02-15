# 👪 Tosi(The only story in the world) :books:
![tosi](/uploads/250f8a2563c1215cc494f3ea1f9f468b/tosi.png)

## :loud_sound: 주제

- TTS (텍스트 음성 변환)를 활용한 어린이 문학 및 동화 낭독 서비스

## :sparkling_heart: 소개

- 등장인물을 어린이 이름으로 바꾸어 TTS로 책을 낭독하고, AI로 커스텀 동화책을 생성하거나 동화 속 인물과 소통합니다.

## :date: 기간

- 2024.01.03 ~ 2024.02.17

## :keyboard: 참여 인원 및 역할

| 이름   | 역할                  | 기능   | 
| ------ | --------------------- |-------|
| 천우진 | FullStack, Team Leader, Git Manager              | 메인 페이지, 동화 목록, 동화 검색  
| 우지민 | FullStack, FrontEnd leader              | TTS 자동 재생 / 정지 / 배속 / 볼륨 적용(일반 동화, 커스텀 동화)  
| 김다윤 | FullStack               | 등장인물 이름 변경 로직, AWS S3, 동화 페이지 구성, 관심 동화 등록   
| 양성주 | FullStack, UI 설계              | AI를 활용한 커스텀 동화 및 썸네일 생성 / 저장, 다른 회원과 커스텀 동화 공유 |
| 이아진 | FullStack, BackEnd leader, Infra              | AI를 활용한 등장인물과의 채팅, 도커라이징  
| 김소연 | FullStack                | 회원   | 회원가입, 자동 로그인, 마이페이지, 나의 책장

## :runner: 주차별 진행 상황

| 주차   | 진행 상황              |  
| ------ | ---------------------|
| 1주차 | 기획서, 페르소나 분석, 기능 정의서, ERD, 메뉴 구조도  |
| 2주차 | 화면정의서, ERD, 시퀀스 다이어그램, 플로우차트, 시스템 아키텍쳐              | 동화 구연  | 
| 3주차 | DB 구축, REST API 구현, 외부 API 솔루션 개발, vue 페이지 구현               | 동화 구연  | 
| 4주차 |               | 커스텀 동화  | 
| 5주차 |               | 등장인물과의 채팅  | 
| 6주차 |               | 회원   |       

## :deciduous_tree: Stacks

##### Environment
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Visual Studio Code](https://img.shields.io/badge/Visual%20Studio%20Code-007ACC?style=for-the-badge&logo=Visual%20Studio%20Code&logoColor=white)  

##### BackEnd
![Spring Boot](https://img.shields.io/badge/spring%20boot-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)

##### FrontEnd
![Vue.js](https://img.shields.io/badge/vue.js-%2335495e.svg?style=for-the-badge&logo=vuedotjs&logoColor=%234FC08D)
![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)

##### DataBase
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)

##### Version Control
![GitLab](https://img.shields.io/badge/gitlab-%23181717.svg?style=for-the-badge&logo=gitlab&logoColor=white)

##### Communication
![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white)
![Jira](https://img.shields.io/badge/jira-%230A0FFF.svg?style=for-the-badge&logo=jira&logoColor=white)

## 💎 목차

1. 프로젝트 설명

<br />

# ⚙︎ 프로젝트 설명

## ⭐️ 기획 배경

##### 어린이 문해력 저하  
코로나19로 인해 영상 매체 시청시간이 늘어나면서, 어린이들의 문해력 저하가 사회 문제로 대두되었습니다. 접근성 높은 문학 콘텐츠를 제공함으로써 문해력 향상을 도모하고 독서 습관이 감소하는 추세에 대응하고자 합니다.

##### 돌봄 인력 부족
핵가족화가 진행되면서 지역사회와의 연결이 약화되었습니다. 아이들과 함께 보낼 수 있는 시간이 상대적으로 적은 양육자를 보조하여 동화를 읽어주는 역할을 합니다.

## ⭐️ 기획 목적

##### 문해력 개발 촉진
자신의 이름이 들어간 동화로 내용에 더 집중할 수 있도록 합니다. 재미있게 많은 문학을 접하게 함으로써 건강한 독서 습관을 형성할 수 있습니다. 

##### 양육자 고충 해소
자녀와 보내는 질적인 시간이 부족한 맞벌이 가정에게 도움을 줄 수 있습니다. 양육자가 바빠도 아이들이 동화를 듣고 즐길 수 있습니다.

## ⭐️ 기대 효과	

##### 언어 습득과 사회성 강화
자신의 이름에 반응하면서 언어 습득에 긍정적 영향을 미칠 수 있습니다. 동화 속 캐릭터들과 상호작용하며 어휘력을 향상시키고 사회적으로 행동하는 법을 배웁니다.

##### 창의력의 증진 
어린이들이 스토리텔링에 더욱 몰입하여 다양한 상황과 캐릭터를 상상하며 창의적인 사고를 발달시킬 수 있습니다.

##### 학습 동기 부여
어린이들이 더 적극적으로 교육 콘텐츠에 참여하고, 독서에 대한 동기를 높일 수 있는 방향을 제시합니다.

## ⭐️ 타깃

- 육아를 보조해 줄 질적인 도구가 필요한 양육자
- 이야기 듣기를 좋아하는 어린이
- 독서를 지루해하는 어린이

## ⭐️ 주요 기능
##### 동화 구연
- 책장에 있는 책을 선택하면 책이 열립니다.
- 등장인물의 이름을 아이의 이름으로 바꿔서 동화를 읽어 줍니다.
- 동화를 읽어줄 목소리를 선택할 수 있습니다.
##### 도서관 & 내 책장
- 많은 사람들이 책장에 추가한 동화가 상위에 노출됩니다.
- 동화를 제목으로 검색할 수 있습니다.
- 원하는 책을 내 책장에 등록할 수 있습니다.
##### 등장인물과의 대화
- 동화를 다 읽으면 동화 속 등장인물을 선택하여 질문을 할 수 있습니다.
##### 내가 쓰는 동화
- 원하는 이름, 배경, 상황을 입력하면 이야기가 자동으로 생성됩니다.
- 생성된 동화는 내 책장에 추가하여 다시 볼 수 있습니다.





     





