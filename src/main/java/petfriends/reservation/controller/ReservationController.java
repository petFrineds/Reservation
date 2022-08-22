package petfriends.reservation.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.util.UriComponentsBuilder;
import petfriends.reservation.model.Reservation;
import petfriends.reservation.repository.ReservationRepository;
import petfriends.reservation.service.ReservationService;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/")
 public class ReservationController {

	 @Autowired
	 ReservationService reservationService;

	 @Autowired
	 ReservationRepository reservationRepository;

	 @GetMapping("/reservations")
	 public List<Reservation> findAll() {
		return reservationService.findAll();
	 }


	@RequestMapping(method= RequestMethod.GET, path="/reservations/{id}")
	public ResponseEntity<Reservation> findById(@PathVariable Long id){
		Reservation reservation =  reservationRepository.findById(id).get();

		if(reservation != null){
			return new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
		}
		//내용 없을 때
		return new ResponseEntity<Reservation>(reservation, HttpStatus.NO_CONTENT);
	}

	/* 기존
	 @GetMapping("/reservations/{id}")
	 public Reservation findById(@PathVariable("id") Long id) {
		 return reservationService.findById(id);
	 }
	*/

	@RequestMapping(method= RequestMethod.GET, path="/reservations/users/{userId}")
	public ResponseEntity<List<Reservation>> findAllByUserId(@PathVariable String userId){
		List<Reservation> reservations =  reservationService.findAllByUserId(userId);
		if(!reservations.isEmpty()){
			return new ResponseEntity<List<Reservation>>(reservations, HttpStatus.OK);
		}
		//내용 없을 때
		return new ResponseEntity<List<Reservation>>(reservations, HttpStatus.NO_CONTENT);
	}

	/*
	 @GetMapping("/reservations/users/{userId}")
	 public List<Reservation> findAllByUserId(@PathVariable("userId") String userId) {
		 return reservationService.findAllByUserId(userId);
	 }
	*/


	@RequestMapping(value = "/reservations", method = RequestMethod.POST)
	public ResponseEntity<Reservation> reserve(@RequestBody final Reservation reservation,
											   final UriComponentsBuilder ucBuilder) {
		if(reservation.getStatus() == null){
			reservation.setStatus(1);
		}

		Reservation savedReservation = reservationService.save(reservation);

		final HttpHeaders headers = new HttpHeaders();

		headers.setLocation(ucBuilder.path("/reservations/{id}")
				.buildAndExpand(savedReservation.getReservedId()).toUri());

		return new ResponseEntity<Reservation>(headers, HttpStatus.CREATED);
	}

	/*
	 @PostMapping("/reservations")
	 public Reservation reserve(@Valid @RequestBody Reservation reservation) {

		 // 기본 - 요청중 상태
		 if(reservation.getStatus() == null){
			 reservation.setStatus(1);
		 }
		return reservationService.save(reservation);
	 }
	*/



	@Transactional
	@RequestMapping(value = "/reservations/{id}", method = RequestMethod.PATCH)
	public ResponseEntity<Reservation> patchCustomer(@PathVariable("id") final Long id,
												  @RequestBody final Reservation reservation) {

		Optional<Reservation> temp;

		Integer status = reservation.getStatus();
		Reservation savedReservation = null;

		if(reservationRepository.findById(reservation.getReservedId()).isPresent()) {
			temp = reservationRepository.findById(reservation.getReservedId());
			savedReservation = temp.get();
			savedReservation.setStatus(status); //상태 업데이트
		}

		reservationService.save(savedReservation);

		return  new ResponseEntity<Reservation>(savedReservation, HttpStatus.OK);
	}

	/*
	 @PatchMapping("/reservations")
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
	*/

 }

 