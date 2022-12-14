package petfriends.reservation.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

//		final HttpHeaders headers = new HttpHeaders();
//
//		headers.setLocation(ucBuilder.path("/reservations/{id}")
//				.buildAndExpand(savedReservation.getReservedId()).toUri());

		//예약 전체 넘기는 걸로 변경
		return new ResponseEntity<Reservation>(savedReservation, HttpStatus.OK);
	}

	@Transactional
	@RequestMapping(value = "/reservations/{id}", method = RequestMethod.PATCH)
	public ResponseEntity<Reservation> patchReservation(@RequestHeader(value="Authorization") String token,
														@PathVariable("id") final Long id,
												  @RequestBody final Reservation reservation) {

		Optional<Reservation> temp;
		Reservation savedReservation = null;

		//String token = requestHeader.get("Authorization").toString();

		log.info("1. PATCH >>>> Reserved Id = " + id);

		if (reservationRepository.findById(id).isPresent()) {
			temp = reservationRepository.findById(id);
			savedReservation = temp.get();

			log.info("2. find By Id is Present >>>> DogwalkerId = " + savedReservation.getDogwalkerId());
		}

		log.info("3. TOKEN >>>>  " +  token);

		savedReservation.setStatus(ReservationStatus.CANCEL); //상태 업데이트
		savedReservation.setToken(token);
		reservationService.save(savedReservation);
		log.info("4. 현 상태 >>>>  = " + savedReservation.getStatus());

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


	@DeleteMapping("/reservations/{id}")
	public HttpStatus deleteReservation(@PathVariable("id") Long id){
		reservationRepository.deleteById(id);

		return HttpStatus.OK;
	}

}

 