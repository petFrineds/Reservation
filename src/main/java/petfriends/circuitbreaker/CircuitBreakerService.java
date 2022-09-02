package petfriends.circuitbreaker;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import petfriends.external.PaymentService;

@Slf4j
@Service
public class CircuitBreakerService {



    @Autowired
    private PaymentService paymentService;

    private static final String CIRCUIT_INSTANCE = "venhMsa";

    @CircuitBreaker(name = CIRCUIT_INSTANCE, fallbackMethod = "getCircuitBreakerFallback")
    public String getCircuitBreaker(String param) {
        String message = paymentService.getUserPayments(param);
        return message;
    }

    public String getNoCircuitBreak(String param) {
        String message = paymentService.getUserPayments(param);
        return message;
    }

    private String getCircuitBreakerFallback(Throwable t) {
        return "getCircuitBreakerFallback! exception type: " + t.getClass() + "exception, message: " + t.getMessage();
    }

}