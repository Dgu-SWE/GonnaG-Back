# GonnaG-Back

동국대학교 데이터베이스에 접근하여 챗봇에게 데이터를 전달하고 프론트엔드에 데이터를 반환하는 Spring Boot 기반 백엔드 서버 프로젝트입니다.

## 📋 프로젝트 개요

GonnaG는 동국대학교 학생들을 위한 졸업 요건 관리 및 학사 정보 조회 시스템입니다. 이 프로젝트는 백엔드 서버로, PostgreSQL 데이터베이스에 저장된 동국대학교 학사 정보를 조회하고, 챗봇 서비스와 연동하여 학생들에게 맞춤형 정보를 제공합니다.

## 🛠 기술 스택

- **Java**: 17
- **Spring Boot**: 3.4.11
- **Spring Data JPA**: 데이터베이스 접근
- **Spring Security**: 인증 및 보안
- **PostgreSQL**: 데이터베이스
- **JWT (jjwt)**: 토큰 기반 인증
- **Lombok**: 보일러플레이트 코드 감소
- **Gradle**: 빌드 도구

## 📁 프로젝트 구조

```
src/main/java/swe/gonnag/
├── controller/          # REST API 컨트롤러
│   ├── CourseHistoryController.java    # 수강 이력 조회
│   ├── MCPController.java              # MCP (Model Context Protocol) 엔드포인트
│   ├── NoticeController.java           # 공지사항 조회
│   └── UserController.java             # 사용자 인증 및 정보 조회
│
├── service/            # 비즈니스 로직
│   ├── ChatService.java                # 챗봇 서비스 (외부 모델 서버 연동)
│   ├── CourseHistoryService.java       # 수강 이력 서비스
│   ├── GetCommonNoticeService.java     # 전체 공지 조회 서비스
│   ├── GetMajorNoticeService.java      # 학과 공지 조회 서비스
│   ├── GetUserInfoService.java         # 사용자 정보 및 졸업 진행 상황 서비스
│   ├── MCPService.java                 # MCP 데이터 제공 서비스
│   └── UserLoginService.java           # 사용자 로그인 서비스
│
├── domain/
│   ├── dto/            # 데이터 전송 객체
│   │   ├── MCP/        # MCP 관련 DTO
│   │   ├── request/    # 요청 DTO
│   │   └── response/   # 응답 DTO
│   │
│   ├── entity/         # JPA 엔티티
│   │   ├── AnnouncementEntity.java     # 공지사항
│   │   ├── ChatMessageEntity.java      # 채팅 메시지
│   │   ├── ClassEntity.java            # 수업 정보
│   │   ├── ProgramEntity.java          # 프로그램 정보
│   │   ├── RequirementEntity.java      # 졸업 요건
│   │   ├── TranscriptEntity.java       # 수강 기록
│   │   └── UserEntity.java             # 사용자 정보
│   │
│   └── enums/          # 열거형
│       ├── ChatRole.java               # 채팅 역할 (USER, ASSISTANT)
│       └── CourseType.java             # 과목 유형
│
├── repository/         # 데이터 접근 계층
│   ├── AnnouncementRepository.java
│   ├── ChatMessageRepository.java
│   ├── ClassRepository.java
│   ├── ProgramRepository.java
│   ├── RequirementRepository.java
│   ├── TranscriptRepository.java
│   └── UserRepository.java
│
├── exception/          # 예외 처리
│   ├── CustomException.java
│   ├── ErrorCode.java
│   └── GlobalExceptionHandler.java
│
└── util/               # 유틸리티
    └── common/
        ├── CommonResponseDto.java      # 공통 응답 DTO
        ├── ExceptionDto.java
        ├── JwtFilter.java              # JWT 필터
        ├── JwtUtil.java                # JWT 유틸리티
        ├── config/                     # 설정
        └── security/                   # 보안 관련
```

## 🚀 주요 기능

### 1. 사용자 인증
- **로그인** (`POST /api/signin`): 학번과 비밀번호로 로그인, JWT 토큰 발급
- **로그아웃** (`GET /api/signout`): 로그아웃 처리
- JWT 기반 인증으로 API 접근 제어

### 2. 사용자 정보 조회
- **사용자 정보** (`GET /api/user`): 현재 로그인한 사용자의 기본 정보 조회
- **졸업 진행 상황** (`GET /api/progress`): 졸업 요건 대비 현재 진행 상황 조회
  - 취득 학점, 전공, 필수교양, 일반교양 등 카테고리별 진행률 제공

### 3. 수강 이력 조회
- **수강 이력** (`GET /api/history`): 사용자의 수강 기록 조회

### 4. 공지사항 조회
- **학과 공지** (`GET /api/notice`): 사용자 소속 학과의 공지사항 조회
- **전체 공지** (`GET /api/notice/all`): 전체 공지사항 조회

### 5. 챗봇 서비스
- **메시지 전송** (`POST /api/chat`): 챗봇에게 메시지 전송 및 응답 수신
- **채팅 기록** (`GET /api/chat/history`): 사용자의 채팅 기록 조회
- 외부 모델 서버와 연동하여 AI 기반 응답 제공
- 대화 기록을 데이터베이스에 저장하여 컨텍스트 유지

### 6. MCP (Model Context Protocol) 엔드포인트
챗봇이 사용할 수 있는 데이터 제공 API:

- **기본 정보** (`GET /mcp`): MCP 서비스 기본 정보
- **사용자 정보** (`POST /mcp/user-info`): 사용자 정보 및 졸업 진행 상황
- **수업 정보** (`GET /mcp/classes`): 전체 수업 정보 조회
- **학업이수 가이드** (`POST /mcp/guide`): 사용자 전공에 해당하는 졸업 요건 가이드
- **공지사항** (`GET /mcp/announcements`): 전체 공지사항 조회

## 🔧 설정

### application.yml

```yaml
spring:
  application:
    name: gonnag
  
  datasource:
    url: jdbc:postgresql://[DB_HOST]:[PORT]/[DB_NAME]
    username: [USERNAME]
    password: [PASSWORD]
  
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        show_sql: true
        format_sql: true

jwt:
  secret-key: [JWT_SECRET_KEY]
  expiration-ms: 3600000  # 1시간
```

### 데이터베이스 스키마

주요 엔티티:
- **Users**: 사용자 정보 (학번, 이름, 학년, 전공 등)
- **Transcripts**: 수강 기록
- **Classes**: 수업 정보
- **Requirements**: 졸업 요건
- **Programs**: 프로그램 정보
- **Announcements**: 공지사항
- **ChatMessages**: 채팅 메시지 기록

## 📦 빌드 및 실행

### 요구사항
- Java 17 이상
- PostgreSQL 데이터베이스
- Gradle

### 빌드
```bash
./gradlew build
```

### 실행
```bash
./gradlew bootRun
```

또는

```bash
java -jar build/libs/gonnag-0.0.1-SNAPSHOT.jar
```

## 🔐 보안

- Spring Security를 사용한 인증 및 인가
- JWT 토큰 기반 인증
- 비밀번호 암호화 (데이터베이스에 저장된 비밀번호는 암호화되어야 함)
- API 엔드포인트별 인증 필요 여부 설정

## 📝 API 응답 형식

모든 API는 `CommonResponseDto`로 래핑되어 반환됩니다:

```json
{
  "success": true,
  "data": { ... },
  "message": null
}
```

에러 발생 시:
```json
{
  "success": false,
  "data": null,
  "message": "에러 메시지"
}
```

## 🔗 외부 연동

### 모델 서버
챗봇 서비스는 외부 모델 서버와 연동됩니다:
- URL: `https://port-0-gonnag-chat-mihqm6p4c9febe90.sel3.cloudtype.app/api/chat`
- GPT-4o 모델 사용
- 대화 기록을 컨텍스트로 전달

## 📄 라이선스

이 프로젝트는 동국대학교 소프트웨어공학과 졸업 프로젝트입니다.

## 👥 개발팀

SWEngineering Team

