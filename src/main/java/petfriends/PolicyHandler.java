package petfriends;

import petfriends.config.KafkaProcessor;
import petfriends.external.Payed;
import petfriends.external.WalkEnded;
import petfriends.external.WalkStarted;
import petfriends.reservation.model.Reservation;
import petfriends.reservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }
    
    @Autowired
    ReservationRepository reservationRepository;

    // 상태변경
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPayed_(@Payload Payed payed)
    {
        if(payed.isMe()){
            Optional<Reservation> reservationOptional = reservationRepository.findById(payed.getReservedId());
            Reservation reservation = reservationOptional.get();
            reservation.setStatus(5); // 예약취소
            reservationRepository.save(reservation);
        }
    }

    // 산책 시작
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverWalkStarted_(@Payload WalkStarted walkStarted)
    {
        if(walkStarted.isMe()){
            Optional<Reservation> reservationOptional = reservationRepository.findById(walkStarted.getReservedId());
            Reservation reservation = reservationOptional.get();
            reservation.setStatus(3); // 산책시작
            reservationRepository.save(reservation);
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverWalkEnded_(@Payload WalkEnded walkEnded)
    {
        if(walkEnded.isMe()){
            Optional<Reservation> reservationOptional = reservationRepository.findById(walkEnded.getReservedId());
            Reservation reservation = reservationOptional.get();
            reservation.setStatus(4); // 산책종료
            reservationRepository.save(reservation);
        }
    }
}
