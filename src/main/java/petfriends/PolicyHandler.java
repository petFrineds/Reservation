package petfriends;

import petfriends.config.KafkaProcessor;
import petfriends.reservation.dto.Payed;
import petfriends.reservation.dto.WalkEnded;
import petfriends.reservation.dto.WalkStarted;
import petfriends.reservation.model.Reservation;
import petfriends.reservation.model.ReservationStatus;
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

    // 상태변경 - 결재완료
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPayed_(@Payload Payed payed)
    {
        if(payed.isMe()){
            Optional<Reservation> reservationOptional = reservationRepository.findById(payed.getReservedId());

            if(reservationOptional.isPresent()) {
                Reservation reservation = reservationOptional.get();
                reservation.setStatus(ReservationStatus.PAYED);
                reservationRepository.save(reservation);
            }else{
                new RuntimeException("결재완료에 해당하는 예약 번호가 존재하지 않습니다.");
            }
        }
    }

    // 산책 시작
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverWalkStarted_(@Payload WalkStarted walkStarted)
    {
        if(walkStarted.isMe()){
            Optional<Reservation> reservationOptional = reservationRepository.findById(walkStarted.getReservedId());
            if(reservationOptional.isPresent()) {
                Reservation reservation = reservationOptional.get();
                reservation.setStatus(ReservationStatus.START); // 산책시작
                reservationRepository.save(reservation);
            }else{
                new RuntimeException("산책 시작에 해당하는 예약 번호가 존재하지 않습니다.");

            }
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverWalkEnded_(@Payload WalkEnded walkEnded)
    {
        if(walkEnded.isMe()){
            Optional<Reservation> reservationOptional = reservationRepository.findById(walkEnded.getReservedId());

            if(reservationOptional.isPresent()) {
                Reservation reservation = reservationOptional.get();
                reservation.setStatus(ReservationStatus.END); // 산책종료
                reservationRepository.save(reservation);
            }else{
                new RuntimeException("산책완료에 해당하는 예약 번호가 존재하지 않습니다.");

            }
        }
    }
}
