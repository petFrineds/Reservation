---------------------------------------------------
1. maria 설치 및 테이블 생성(예제에는 id/passwd : root/1234 , 변경은 application.yml에서 하면 됨. )
---------------------------------------------------
테이블스페이스 : create database petfriends;

mariadb 5.5 버전에서는 TIMESTAMP를 한 컬럼만 사용이 가능하고 NOW() 함수를 쓸수가 없다.... 

CREATE TABLE reservation (
id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
start_time DATETIME NULL DEFAULT NULL,
end_time DATETIME NULL DEFAULT NULL,

dogwalker_schedule_id BIGINT(20) NOT NULL,
dogwalker_id NVARCHAR(50) NULL,
dogwalker_name NVARCHAR(50) NULL,

amount DOUBLE NULL DEFAULT NULL,
status NVARCHAR(20) NOT NULL DEFAULT 'REQUEST',

user_id NVARCHAR(50) NOT NULL,
user_name NVARCHAR(50) NOT NULL,

reg_date DATETIME,
upd_date DATETIME
) COLLATE='utf8mb4_general_ci' ENGINE=InnoDB ;
 
insert샘플:
insert into reservation (start_time, end_time, dogwalker_schedule_id, dogwalker_id, dogwalker_name, amount, status, user_id, user_name) 
values ("2022-08-22 19:00:00", "2022-08-22 21:00:00", 5, "kiki_id", "kiki",  40000, "REQUEST", "hihi_id", "hihi");

insert into reservation (start_time, end_time, dogwalker_schedule_id, dogwalker_id, dogwalker_name, amount, status, user_id, user_name)
values ("2022-08-22 21:00:00", "2022-08-22 23:00:00", 2, "jaekie_id", "jaekie",  80000, "REQUEST", "soya95", "soya");


insert into payment (pay_id, amount, pay_date, refund_date, reserved_id, user_id) values 
(20220212, 40000, '2022-03-10 19:22:33.102', null, 1 , 'geny_id');


---------------------------------------------------  
2. 배포 방법
---------------------------------------------------  
ec2에 reservation 테이블 만들기, 데이터 insert 
git clone https://github.com/petFrineds/Reservation.git
mvn install
aws ecr create-repository --repository-name reservation-backend -- image-scanning-configuration scanOnPush=true --region ${AWS_REGION}
docker build -t reservation-backend .
docker tag reservation-backend:latest 811288377093.dkr.ecr.$AWS_REGION.amazonaws.com/reservation-backend:latest
docker push 811288377093.dkr.ecr.us-west-2.amazonaws.com/reservation-backend:latest
aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin 811288377093.dkr.ecr.us-west-2.amazonaws.com/
cd manifests
-- 여기서 부터는 ec2-user 사경
kubectl apply -f manifests/reservation-deployment.yaml
kubectl get deploy
kubectl apply -f manifests/reservation-service.yaml
kubectl get service
kubectl get ingress

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
