package petfriends.reservation.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import jdk.jfr.Timestamp;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
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
@Slf4j
 public class ReservationController {

	 @Autowired
	 ReservationService reservationService;

	 @Autowired
	 ReservationRepository reservationRepository;

	 @GetMapping("/reservations")
	 public ResponseEntity<List<Reservation>> findAll() {
		 List<Reservation> reservations = reservationService.findAll();
		return new ResponseEntity<List<Reservation>>( reservations, HttpStatus.OK);
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

		//ReservationStatus status = reservation.getStatus();
		Reservation savedReservation = null;

		log.info("1. PATCH >>>> Reserved Id = " + id);

		if (reservationRepository.findById(id).isPresent()) {
			temp = reservationRepository.findById(id);
			savedReservation = temp.get();

			log.info("2. find By Id is Present >>>> DogwalkerId = " + savedReservation.getDogwalkerId());
		}

		// 시간 변환
		LocalDateTime currentTime = LocalDateTime.now();
		currentTime = currentTime.minusHours(-24); // 24시간 전시간

		LocalDateTime startTime = new java.sql.Timestamp(savedReservation.getStartTime().getTime())
				.toLocalDateTime();

		if (startTime.isAfter(currentTime)) {
			new RuntimeException("24 시간 이내에는 취소가 불가능합니다.");
		}else {
			log.info("3. 24시간 이내 아님!  ");
			savedReservation.setStatus(ReservationStatus.CANCEL); //상태 업데이트
			reservationService.save(savedReservation);
			log.info("4. 현 상태 >>>>  = " + savedReservation.getStatus());
		}

		return new ResponseEntity<Reservation>(savedReservation, HttpStatus.OK);

	}


	@RequestMapping(value = "/reservations/hpa", method = RequestMethod.GET)
	public String helloWordforHPA(){
		double x = 0.0001;
		String hostname = "";
		for(int i=0; i<=10000000; i++){
			x+=Math.sqrt(x);
		}
		try{
			hostname = InetAddress.getLocalHost().getHostName();
		}catch(UnknownHostException e){
			e.printStackTrace();
		}
		return "HPA Test (" + hostname + ")\n";
	}


}

 