package petfriends.reservation.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import petfriends.reservation.model.Reservation;
import petfriends.reservation.repository.ReservationRepository;

@Service
public class ReservationService {
	 
	 @Autowired
	 ReservationRepository reservationRepository;


	 public List<Reservation> findAll(){
		 return reservationRepository.findAll();
	 }
	 public Reservation findById(Long id) {

		 if(reservationRepository.findById(id).isPresent()) {
			 Optional<Reservation> reservation = reservationRepository.findById(id);
			 return reservation.get();
		 }
		 return null;
	 }


	public List<Reservation> findAllByUserId(String userId) {
		return reservationRepository.findAllByUserId(userId);
	}

	public Reservation save(Reservation reservation){

		LocalDateTime current = LocalDateTime.now();
		 if(reservation.getRegDate() == null)
			 reservation.setRegDate( java.sql.Timestamp.valueOf(current) );

		 if(reservation.getUpdDate() == null)
			 reservation.setRegDate( java.sql.Timestamp.valueOf(current) );

		return reservationRepository.save(reservation);
	}


}

