# MSA 기반 PWA 온라인 쇼핑몰 프로젝트 - Back Office

### 🗨️ **개요**

---

- 현대 IT&E 개발자 양성과정의 최종 프로젝트이다.
- 더 한섬(THE HANDSOME)에서 제공받은 상품 데이터를 활용하여 쇼핑몰 홈페이지를 제작한다.
- 상품 전시가 가능한 모바일 환경의 프론트를 제작한다.
- 회원 및 상품 정보를 관리할 수 있는 백오피스를 제작한다.
- MSA 환경으로 개발한다.

### 🗓️ 개발기간/인원

---

- 2021.11 ~ 2021.12 / 5주
- 3인 프로젝트

### ⚙️ 개발환경

---

- Vue.js
- Thymeleaf
- Springboot
- Mybatis
- Oracle

### 🗞️ 담당 역할 - Back Office

---

🍉 **서민철 (팀장)**

- 대시보드
- 회원 정보 관리
- 회원 등급 관리
- 상품 리뷰 관리

🍊 **고정민**

- 대시보드
- 로그인
- 상품 정보 등록
- 상품 정보 조회 및 삭제
- 상품 정보 수정
- 검색 기능 구현

🍋 유채연

- 대시보드
- 상품 재고 관리
- 주문 목록 관리
- 전시 관리
- 프로모션 관리

### ⚡ 구조

---

- 시스템 아키텍쳐

![image](https://user-images.githubusercontent.com/23237567/147749585-0164236d-c0cb-4a1c-8084-d9cfa27c2a1e.png)


- ERD

![image](https://user-images.githubusercontent.com/23237567/147749566-3282b399-9e3e-464c-8b28-410701d3b496.png)


### ⚡ 설명

---

![헤더](https://user-images.githubusercontent.com/23237567/147749638-7d581a88-67ec-4726-be0c-efb89cb51f68.gif)

- 헤더 (header)
    - 사이드바 열기
        - 좌측 상단의 버튼을 클릭하면 사이드바가 튀어나온다.
    - 메인페이지 이동
        - 현재 페이지의 정보를 표시한다.
    - 페이지 표시
        - 중앙의 로고를 클릭하면 메인 페이지로 이동한다.
    - 이벤트/신상품/베스트상품 탭
        - 메인 페이지에서는 각각 이벤트/신상품/베스트상품 페이지로 이동할 수 있는 탭이 표시된다.
