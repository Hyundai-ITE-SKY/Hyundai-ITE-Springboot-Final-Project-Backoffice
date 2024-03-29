# MSA 기반 PWA 온라인 쇼핑몰 프로젝트 - Back Office

### 🗨️ **개요**

---

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

### ⚡ 구조

---

- 시스템 아키텍쳐

![image](https://user-images.githubusercontent.com/23237567/147749585-0164236d-c0cb-4a1c-8084-d9cfa27c2a1e.png)


- ERD

![image](https://user-images.githubusercontent.com/23237567/147749566-3282b399-9e3e-464c-8b28-410701d3b496.png)


### ⚡ 설명

---

![로그인](https://user-images.githubusercontent.com/23237567/147874982-9b8fefe1-6aef-4092-9064-c16a675138a8.gif)

- 로그인
    - Security 사용
    - 권한이 Admin인 member만 접근할 수 있도록 권한을 설정했다.
        - 권한이 없으면 403 에러 페이지로 이동한다.
    - 로그인 정보가 맞지 않을 시 로그인 버튼 아래에 에러를 출력한다.
    - Admin의 정보를 BackOffice Rest API를 통해 받아온다.

![50_대시보드](https://user-images.githubusercontent.com/23237567/147874800-9c3ea90a-109d-44fa-9278-644e0682c729.gif)
![52_대시보드2](https://user-images.githubusercontent.com/23237567/147874836-24937b93-d040-4f18-97c3-e087720047cf.gif)

- 대시보드
    - 온라인 쇼핑몰 데이터 조회 가능
        - 주문 배송 정보 조회
        - 기간별 매출액 조회
        - 2주간 결제자 결제건수 조회
        - 회원(일반휴면 탈퇴 현황 조회
        - 카테고리별 월 매출 조회
    - 그래프를 통해 유용한 정보 한눈에 확인 가능
        - Chart.js 라이브러리 활용

![59_회원정보관리_회원상세](https://user-images.githubusercontent.com/23237567/147874862-c729c503-67b1-4d3b-b676-f3900954950f.gif)
![60_회원추가](https://user-images.githubusercontent.com/23237567/147874864-c7211b96-eb26-4573-8398-49ba676a0478.gif)

- 회원 정보 관리
    - 회원 정보 조회
        - 등급, 이름, 아이디, 누적결제금액 등
    - 회원 상세정보 조회
        - 각 행을 클릭하면 해당 회원의 상세정보로 이동한다.
    - 회원 계정 추가
        - 계정 추가 버튼을 클릭하면 계정을 등록할 수 있는 폼이 노출된다.
        - 정보 입력 후 저장 버튼을 클릭하면 해당 계정이 추가된다.

![56_회원등급추가](https://user-images.githubusercontent.com/23237567/147874851-2a73eceb-1db0-47bf-b49b-a34985466ea5.gif)
![54_회원 등급 수정](https://user-images.githubusercontent.com/23237567/147874852-0d337e6a-b2d0-45ff-a12f-6ecd3c1b31c8.gif)
![55_회원등급삭제](https://user-images.githubusercontent.com/23237567/147874856-9aba6278-bf18-490b-b7bd-172aff0bbea3.gif)

- 회원 등급 관리
    - 회원 등급 정보 조회 가능
        - 회원의 등급은 누적결제금액에 따라 결정된다.
    - 회원 등급 추가/수정/삭제 가능
        - 등급명, 등급 기준, 등급 혜택
    - 회원 등급 부여
        - 버튼 클릭 시 모든 회원에게 알맞은 등급을 부여한다.
        - 모든 회원의 등급은 매일 새벽 1시에 자동으로 갱신된다.
  
![90_주문목록_주문상세](https://user-images.githubusercontent.com/23237567/147874915-d13d0683-b0ba-4d87-9878-63cbafa81ae0.gif)

- 주문 목록 및 주문 상세정보
    - 회원 DB, 상품 DB, 주문 DB 에 접근하여 모든 주문 정보를 불러온다.
    - 주문 내역의 ROW 클릭 시 주문 상세내역을 조회할 수 있다.
    - 주문자 아이디 클릭 시 고객 상세정보 페이지로 전환된다.
    - 목록 버튼 클릭 시 주문 목록 조회 페이지로 전환된다.
    - 수령인 이름, 주문자 아이디, 주문 기간으로 검색할 수 있다.
    - 페이징 처리를 통해 적당량의 데이터만을 가져와 빠르게 보여준다.

![100_홈화면전시_백](https://user-images.githubusercontent.com/23237567/147874948-9763bcb7-89f5-484a-a60e-b9424b833419.gif)

- 홈 화면 전시 관리
    - 홈 화면의 배치를 관리자가 변경할 수 있다.
    - Jquery의 Sortable을 사용하여 드래그 앤 드롭 방식으로 배치를 변경한다.
    - 적용 버튼 클릭 시 변경사항이 프론트에 반영된다.

![74_상품등록](https://user-images.githubusercontent.com/23237567/147874899-1b943aa0-bc8d-48f4-8d22-a7e308d7d58b.gif)

- 상품 등록/수정/삭제
    - 다중 셀렉트 구현
        - 상위 카테고리 선택에 따라 하위 카테고리가 달라지는 다중 셀렉트를 구현했다.
    - 사용자를 고려한 디자인
        - 상품을 등록하려는 MD가 최대한 효율적으로 등록할 수 있는 UI를 고려했다.
    - URL이 필요한 컬럼의 경우, 적용 버튼을 클릭하여 이미지 미리보기가 가능하도록 설계했다.

![77_상품 검색](https://user-images.githubusercontent.com/23237567/147874903-ae4538e1-e10a-4989-9c04-92b40d7edb48.gif)
![79_상품상세조회 및 삭제](https://user-images.githubusercontent.com/23237567/147874905-3c574cb1-9d36-489e-a581-755e73847f3d.gif)
![80_상품수정](https://user-images.githubusercontent.com/23237567/147874908-e94a07c2-6088-4619-9be1-234b33b91459.gif)

- 상품 검색
    - 화면 상단은 상품 검색 기능을 제공한다.
    - 검색 시 상품 목록에 검색된 결과를 출력한다.
    - 검색 분류를 선택하고 검색어를 입력하면 반영된 결과가 목록에 출력된다.

![재고관리](https://user-images.githubusercontent.com/23237567/147874977-64fdeff0-926c-4d0d-812a-aa61f934acea.gif)

- 재고 관리
    - 상품코드, 색상, 사이즈에 따른 모든 재고 정보를 확인할 수 있다.
    - 변경 버튼 클릭 시 해당 상품의 재고가 변경된다.
    - 상품코드, 브랜드, 상품명에 따라 재고를 검색할 수 있다.
    - 재고의 ROW 클릭 시 해당 상품코드에 해당하는 상품 상세내역을 조회할 수 있다.

![64_리뷰_리뷰상세_수정](https://user-images.githubusercontent.com/23237567/147874872-e6a61d67-9df2-438f-a2ac-e9eba71e310d.gif)
![리뷰 답변 확인](https://user-images.githubusercontent.com/23237567/147874874-fafd3750-a50f-409e-8ecd-f0458dfbc587.gif)

- 상품 리뷰 관리
    - 상품 리뷰 조회
        - 사용자가 작성한 리뷰를 조회할 수 있다.
    - 리뷰 상세정보 조회
        - 각 행을 클릭하면 해당 리뷰의 자세한 내용을 확인할 수 있다.
    - 리뷰 답변 기능
        - MD는 상품 리뷰에 답변을 남길 수 있다.
        - 작성한 리뷰는 프론트에서 확인할 수 있다.

![95_이벤트등록](https://user-images.githubusercontent.com/23237567/147874920-5e179ed3-9900-41a6-a118-c953e120a760.gif)
![97_이벤트수정](https://user-images.githubusercontent.com/23237567/147874926-4c62f3bc-0b8e-409a-bae2-ebcfd9957899.gif)
![98_이벤트삭제](https://user-images.githubusercontent.com/23237567/147874927-4280691a-10cf-4b3f-8150-51967c0be538.gif)

- 이벤트 등록/수정/삭제
    - 관리자가 이벤트를 관리할 수 있다.

![96_이벤트목록_상세](https://user-images.githubusercontent.com/23237567/147874923-98e096a4-28ab-4d18-9310-17bfdb8740a0.gif)
 
- 이벤트 목록 및 이벤트 상세정보
    - 이벤트 목록을 볼 수 있다.
    - 이벤트 목록의 ROW 클릭 시 해당 이벤트 아이디에 해당하는 상품 상세내역을 조회할 수 있다.

![102_이벤트전시_백](https://user-images.githubusercontent.com/23237567/147874932-c8771666-c86f-46d8-9fc3-c3ce116ba425.gif)
    
- 이벤트 전시 관리
    - 이벤트 전시 배치를 관리자가 변경할 수 있다.
    - 위, 아래 버튼 클릭 시 배치가 변경된다.
    - 적용 버튼 클릭 시 변경사항이 프론트에 반영된다.
