package petfriends.reservation.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import jdk.jfr.Timestamp;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.util.UriComponentsBuilder;
import petfriends.reservation.model.Reservation;
import petfriends.reservation.model.ReservationStatus;
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


	@RequestMapping(method= RequestMethod.GET, path="/reservations/users/{userId}")
	public ResponseEntity<List<Reservation>> findAllByUserId(@PathVariable String userId){
		List<Reservation> reservations =  reservationService.findAllByUserId(userId);
		if(!reservations.isEmpty()){
			return new ResponseEntity<List<Reservation>>(reservations, HttpStatus.OK);
		}
		//내용 없을 때
		return new ResponseEntity<List<Reservation>>(reservations, HttpStatus.NO_CONTENT);
	}


	@RequestMapping(value = "/reservations", method = RequestMethod.POST)
	public ResponseEntity<Reservation> reserve(@RequestBody final Reservation reservation,
											   final UriComponentsBuilder ucBuilder) {
		if(reservation.getStatus() == null){
			reservation.setStatus(ReservationStatus.REQUEST);
		}

		Reservation savedReservation = reservationService.save(reservation);

		final HttpHeaders headers = new HttpHeaders();

		headers.setLocation(ucBuilder.path("/reservations/{id}")
				.buildAndExpand(savedReservation.getReservedId()).toUri());

		return new ResponseEntity<Reservation>(headers, HttpStatus.CREATED);
	}

	@Transactional
	@RequestMapping(value = "/reservations/{id}", method = RequestMethod.PATCH)
	public ResponseEntity<Reservation> patchReservation(@PathVariable("id") final Long id,
												  @RequestBody final Reservation reservation) {

		Optional<Reservation> temp;

		ReservationStatus status = reservation.getStatus();
		Reservation savedReservation = null;


		if(reservationRepository.findById(reservation.getReservedId()).isPresent()) {
			temp = reservationRepository.findById(reservation.getReservedId());
			savedReservation = temp.get();
		}

		// 시간 변환
		LocalDateTime currentTime = LocalDateTime.now();
		currentTime = currentTime.minusHours(-24); // 24시간 전시간

		LocalDateTime startTime = new java.sql.Timestamp( savedReservation.getStartTime().getTime() )
				.toLocalDateTime();

		if( startTime.isAfter(currentTime) )
			return new ResponseEntity<>( savedReservation,  HttpStatus.EXPECTATION_FAILED);

		else {
			savedReservation.setStatus(status); //상태 업데이트
			reservationService.save(savedReservation);
			return new ResponseEntity<Reservation>(savedReservation,   HttpStatus.OK);
		}


	}

 }

 