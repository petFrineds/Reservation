package petfriends.reservation.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import petfriends.reservation.model.Reservation;
import petfriends.reservation.repository.ReservationRepository;
import petfriends.reservation.service.ReservationService;

import javax.validation.Valid;

@RestController
 public class ReservationController {

	 @Autowired
	 ReservationService reservationService;

	 @Autowired
	ReservationRepository reservationRepository;
	 
	 @GetMapping("/reservation/{id}")
	 public Reservation findById(@PathVariable("id") Long id) {
		 return reservationService.findById(id);
	 }

	 @GetMapping("/reservation/users/{userId}")
	 public List<Reservation> findAllByUserId(@PathVariable("userId") String userId) {
		 return reservationService.findAllByUserId(userId);
	 }


	@PostMapping("/reservation")
	public Reservation reserve(@Valid @RequestBody Reservation reservation) {

		 // 기본 - 요청중 상태
		 if(reservation.getStatus() == null){
			 reservation.setStatus(1);
		 }
		return reservationService.save(reservation);
	}


	@PatchMapping("/reservation")
	public Reservation update(@Valid @RequestBody Reservation reservation){

		Optional<Reservation> temp;
		Integer status = reservation.getStatus();

		if(reservationRepository.findById(reservation.getReservedId()).isPresent()) {
			 temp = reservationRepository.findById(reservation.getReservedId());
			 reservation = temp.get();
			 reservation.setStatus(status); //상태 업데이트
		}

		 switch (reservation.getStatus()){
			 // 1-요청중, 2-결재완료, 3-산책시작, 4-산책종료, 5-예약취소
			 case 1: // 요청중
			 case 2: // 결재완료
			 case 3:
			 case 4:
			 case 5:
				 reservationService.save(reservation);
				 break;
		 }

		 return reservation;
	}
//	@PutMapping("/payments/{id}")
//	public Payment refund(@PathVariable Long id) {
//		return paymentService.refund(id);
//	}
//
//	@GetMapping("/points/users/{userId}")
//	public List<Point> findPointAllByUserId(@PathVariable("userId") String userId) {
//		return paymentService.findPointAllByUserId(userId);
//	}


//	 @PostMapping("/reservation")
//	 public Reservation save(){
//
//	 }
 }

 