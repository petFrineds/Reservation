package petfriends;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import petfriends.config.KafkaProcessor;
import petfriends.payment.dto.OrderCancelled;
import petfriends.payment.repository.PaymentRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }
    
    @Autowired
    PaymentRepository paymentRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderCancelled_(@Payload OrderCancelled orderCancelled){

    	if(orderCancelled.isMe()){
            System.out.println("##### listener  : " + orderCancelled.toJson());
            
            //List<Payment> list = paymentRepository.findByOrderId(orderCancelled.getId()); // mariadb  추가하면서 주석
            
            //for(Payment payment : list){
            	// payment.setCancelYn("Y"); // 테이블 변경하면서 주석처리 2202.06.27
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                // view 레파지 토리에 save
            //	paymentRepository.save(payment);
            //}
            
        }
    }

}
