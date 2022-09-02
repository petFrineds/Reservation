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
    public String testSleepWithCircuitBreaker(String param) {
        String message = paymentService.testPaymentSleep(param);
        return message;
    }

    public String testSleepWithNoCircuitBreaker(String param) {
        String message = paymentService.testPaymentSleep(param);
        return message;
    }

    @CircuitBreaker(name = CIRCUIT_INSTANCE, fallbackMethod = "getCircuitBreakerFallback")
    public String testMessageWithCircuitBreaker(String param) {
        String message = paymentService.testPaymentMessage(param);
        return message;
    }

    public String testMessageWithNoCircuitBreaker(String param) {
        String message = paymentService.testPaymentMessage(param);
        return message;
    }

    private String getCircuitBreakerFallback(Throwable t) {
        return "getCircuitBreakerFallback! exception type: " + t.getClass() + "exception, message: " + t.getMessage();
    }

}