---------------------------------------------------
1. maria 설치 및 테이블 생성(예제에는 id/passwd : root/1234 , 변경은 application.yml에서 하면 됨. )
---------------------------------------------------
테이블스페이스 : petfriends

테이블생성 Script: CREATE TABLE payment (   
  pay_id BIGINT(20) NOT NULL DEFAULT '0',   
  reserved_id VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',   
  user_id VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',   
  amount DOUBLE NULL DEFAULT NULL,   
  pay_date TIMESTAMP NULL DEFAULT NULL,   
  refund_date TIMESTAMP NULL DEFAULT NULL ) COLLATE='utf8mb4_general_ci' ENGINE=InnoDB ;  

insert샘플: insert into payment (amount, pay_date, refund_date, reserved_id, user_id) values (10000, '2022-03-10 19:22:33.102', null, '22021','soyapayment95');  

---------------------------------------------------  
2. kafka설치  
---------------------------------------------------  
참고사이트 : http://www.msaschool.io/operation/implementation/implementation-seven/  

--------------------------------------------------  
3. Payment(mariadb), Shop(hsqldb) 실행 및 테스트  
--------------------------------------------------  
1) Payment에서 아래와 같이 api 통해 데이터 생성하면, mariadb[payment테이블]에 데이터 저장되고, message publish.  
    - 데이터생성(postman사용) : POST http://localhost:8082/payments/   
                              { "reservedId": "202203271311", "userId": "soya95", "amount": "10000", "payDate": "2019-03-10 10:22:33.102" }  

    - 조회 : GET http://localhost:8082/payments/1  

3) Shop에서 message 받아와 저장 ( 아래 PloycyHandler.java가 실행됨 )  

--------------------------------------------------  
4. 구조  
   -controller  
   -service  
   -repository  
   -dto  
   -model  
   -config : KafkaProcessor.java, WebConfig.java(CORS적용)  
--------------------------------------------------  
5. API  
   해당ID의 결제내역조회 : GET http://localhost:8082/payments/{userId}   
--------------------------------------------------  
