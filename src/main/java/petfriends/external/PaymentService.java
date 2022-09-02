package petfriends.external;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@FeignClient(name="Payment", url="${api.url.payment}")
public interface PaymentService {

    @RequestMapping(method= RequestMethod.PUT, path="/payments/{id}")
    public void doPayment(@PathVariable String id);


    @RequestMapping(method= RequestMethod.GET, path="/payments/message/{param}")
    public String getUserPayments(@PathVariable String param);
}