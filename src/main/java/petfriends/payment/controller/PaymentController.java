package petfriends.payment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import petfriends.payment.model.Payment;
import petfriends.payment.service.PaymentService;

 @RestController
 public class PaymentController {

	 @Autowired
	 PaymentService paymentService;
	 
	 @GetMapping("/payments/{userId}")
	 public List<Payment> findAllByUserId(@PathVariable("userId") String userId) {
		 return paymentService.findAllByUserId(userId);
	 }
 }

 