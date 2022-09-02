package petfriends.circuitbreaker;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CircuitBreakerController {

    @Autowired
    private final CircuitBreakerService circuitBreakerService;

    String str = "abc";


    @GetMapping("/reservations/circuitbreaker/{param}")
    public String getVenhMsa(@PathVariable String param) {
        return "VehnMsa Param" + param;
    }

    @GetMapping("/reservations/cb/sleep/{param}")
    public String testCircuitWithSleep(@PathVariable String param){
        return circuitBreakerService.testSleepWithCircuitBreaker(param);
    }

    @GetMapping("/reservations/cb/no/sleep/{param}")
    public String testNoCircuitWithSleep(@PathVariable String param){
        return circuitBreakerService.testSleepWithNoCircuitBreaker(param);
    }

    @GetMapping("/reservations/cb/exception/{param}")
    public String testCircuitWithException(@PathVariable String param){
        return circuitBreakerService.testMessageWithCircuitBreaker(param);
    }

    @GetMapping("/reservations/cb/no/exception/{param}")
    public String testNoCircuitWithException(@PathVariable String param){
        return circuitBreakerService.testMessageWithNoCircuitBreaker(param);
    }


}