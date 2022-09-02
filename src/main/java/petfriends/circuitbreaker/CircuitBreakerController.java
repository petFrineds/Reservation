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

    @GetMapping("/reservations/circuitbreaker/payments/{param}")
    public String getVenhMsaSupport(@PathVariable String param){
        return circuitBreakerService.getCircuitBreaker(param);
    }

    @GetMapping("/reservations/circuitbreaker/no/payments/{param}")
    public String getVenhMsaSupportFail(@PathVariable String param){
        return circuitBreakerService.getNoCircuitBreak(param);
    }


}