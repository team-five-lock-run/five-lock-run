# 🚂 오락실행

이 프로젝트는 기차 예매 서비스를 제공하는 백엔드 서버입니다. 
사용자는 기차, 날짜, 시간, 좌석을 선택해 실시간으로 예약하고, 결제 후 예매 내역을 확인하거나 티켓을 발급받을 수 있습니다.

열차 정보 관리, 대기열 시스템, 좌석 예약 처리를 중심으로 전체 흐름을 구성하였으며, 다수의 사용자가 동시에 열차 조회 및 예약을 시도하는 상황에서도 안정적으로 요청을 처리할 수 있도록 설계하였습니다.

<br>

## 🛠 기술 스택

* Java 17
* Spring Boot 3.4.5
* Spring Data JPA
* Spring Validation
* Spring Security
* MySQL
* Redis (Spring Data Redis, Redisson)
* QueryDSL 5.0.0 (Jakarta)
* JWT (Json Web Token) 기반 인증
* BCrypt 비밀번호 암호화
* Gradle (Groovy DSL)
* Lombok
* Docker
* GitHub Actions (CI/CD)
* JUnit 5, Spring Boot Test, Spring Security Test

<br>

## ⚙️ 팀원별 주요 기능

### 사용자 [@SaltBr](https://github.com/SaltBr)

* 회원가입, 로그인, 로그아웃, 회원 탈퇴
* 마이페이지 정보 조회 및 수정

### 열차 운영 [@dawn0920](https://github.com/dawn0920)

* 역, 노선, 열차, 차량, 좌석, 열차 스케줄 등록 및 관리
* 열차 및 노선 활성화 / 비활성화 설정
* 차량 및 좌석 구성 변경

### 가격 정책 [@withong](https://github.com/withong)

* 좌석 등급별 가격 등록, 수정, 삭제
* 프리미엄/일반 등급별 가격 목록 및 단건 조회

### 대기열 시스템 [@exmrim](https://github.com/exmrim)

* 사용자 대기열 입장 및 순번 부여
* 순번 실시간 조회, 수동 이탈, 예약 완료 시 자동 이탈 처리
* 예매 진입 흐름 제어를 통한 서버 안정성 확보

### 예약 - 결제 대기 [@withong](https://github.com/withong)

* Redis에 좌석 예약 정보 저장
* Redisson 분산 락 적용을 통한 동시성 제어
* 사용자 예약 목록 조회, 좌석별 예약 정보 조회, 단건 조회 및 취소
* SCAN 명령어 기반 예약 목록 조회 최적화

### 예매 - 결제 완료 [@gonaeun](https://github.com/gonaeun)

* 예매 정보 저장, 취소 및 티켓 발급
* 사용자 예매 목록 조회 및 단건 조회

### 배포 및 CI/CD [@gonaeun](https://github.com/gonaeun)

* Docker Compose로 Spring Boot, MySQL, Redis 통합 실행 환경 구성
* .env와 docker 프로파일을 활용한 환경 분리
* GitHub Actions로 빌드 및 테스트 자동화 설정

