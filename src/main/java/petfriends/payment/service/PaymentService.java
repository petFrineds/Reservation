package petfriends.payment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import petfriends.payment.model.Payment;
import petfriends.payment.repository.PaymentRepository;

@Service
public class PaymentService {
	 
	 @Autowired
	 PaymentRepository paymentRepository;
	 
	 public List<Payment> findAllByUserId(String userId) {
		 return paymentRepository.findAllByUserId(userId);
	 } 
		 
}

