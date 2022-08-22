---------------------------------------------------
1. maria 설치 및 테이블 생성(예제에는 id/passwd : root/1234 , 변경은 application.yml에서 하면 됨. )
---------------------------------------------------
테이블스페이스 : create database petfriends;

mariadb 5.5 버전에서는 TIMESTAMP를 한 컬럼만 사용이 가능하고 NOW() 함수를 쓸수가 없다.... 

CREATE TABLE reservation (
id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
start_time DATETIME NULL DEFAULT NULL,
end_time DATETIME NULL DEFAULT NULL,
amount DOUBLE NULL DEFAULT NULL,
status INT(20) NOT NULL DEFAULT '1',
dogwalker_schedule_id BIGINT(20) NOT NULL,
user_id NVARCHAR(50) NOT NULL,
user_nm NVARCHAR(50) NOT NULL,
reg_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
upd_time TIMESTAMP 
) COLLATE='utf8mb4_general_ci' ENGINE=InnoDB ;
 
insert샘플:
insert into reservation (start_time, end_time, amount, status, dogwalker_schedule_id, user_id, user_nm) 
values ("2022-03-10 19:00:00", "2022-03-10 21:00:00", 40000, 1, 10001, "geny_id", "geny");


insert into payment (pay_id, amount, pay_date, refund_date, reserved_id, user_id) values (12, 10000, '2022-03-10 19:22:33.102', null, 6 , 'soyapayment95');


---------------------------------------------------  
2. 배포 방법
---------------------------------------------------  
ec2에 reservation 테이블 만들기, 데이터 insert 
git clone~
create acr ~ repository
docker build -t reservation-backend .
docker tag reservation-backend:latest 811288377093.dkr.ecr.$AWS_REGION.amazonaws.com/reservation-backend:latest

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
