# 🚂 오락실행
이 프로젝트는 명절 기간 수많은 사용자가 동시에 접속해 기차표를 예매하는 상황을 가정한 웹 기반 예매 시스템입니다.
실시간 좌석 예약 기능에 Redis와 Redisson 기반의 분산 락을 적용하여 동시성 문제를 해결하고, 예약과 예매 과정을 분리하여 무결성과 성능을 동시에 확보하는 구조로 설계되었습니다.
* 실시간성 높은 예약 처리와 동시성 제어 구현
* 예약은 Redis, 예매는 MySQL에 저장하는 구조 설계
* 테스트를 통해 하나의 좌석에 대해 단 1명만 예약 가능한 시스템 검증
* 시스템 과부하를 방지하기 위한 Redis 성능 최적화 적용

<br>

## 기술 스택

### Backend

* Java 17
* Spring Boot 3.4.5

    * Spring Web
    * Spring Data JPA
    * Spring Security
    * Spring Validation
* QueryDSL 5.0.0 (Jakarta)

### 데이터베이스 및 캐시

* MySQL
* Redis (Spring Data Redis + Redisson)

### 인증 및 보안

* JWT (JJWT 0.11.5)
* BCrypt (at.favre.bcrypt 0.10.2)

### 기타 라이브러리

* Lombok
* Jakarta Annotation / Persistence API

### 테스트

* JUnit 5
* Spring Boot Test
* Spring Security Test

### 빌드 & 배포

* Gradle (Groovy DSL)
* Docker
* GitHub Actions (CI/CD)

<br>

## 기능 설명

### 사용자

* 회원가입, 로그인, 로그아웃
* 마이페이지 조회 및 수정
* 회원 탈퇴

### 열차 운영

* 역, 노선, 열차 등록 및 비활성화/활성화
* 열차 차량 및 좌석 구성 수정
* 열차 스케줄 생성 및 조회

### 가격 정책

* 가격 등록, 수정, 삭제
* 좌석 등급별 가격 목록 조회

### 예약

* 실시간 좌석 예약 (Redis + 분산 락 적용)
* 사용자 예약 목록 조회
* 좌석별 예약 정보 조회
* 예약 단건 조회 및 취소
* 예약 키 기반 Redis 최적화 조회 (SCAN 방식 적용)

### 예매

* 결제 완료 후 예매 정보 저장 (MySQL)
* 사용자 예매 목록 조회
* 예매 단건 조회 및 취소
* 예매 정보 기반 티켓 발급

### 대기열 시스템

* 대기열 입장, 순번 조회, 이탈
* 예약 완료 시 대기열 자동 이탈 처리
* 실시간 알림 구조 설계
