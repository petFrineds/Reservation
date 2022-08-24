package petfriends.reservation.dto;

import lombok.Data;
import petfriends.AbstractEvent;

import java.sql.Timestamp;

@Data
public class Payed extends AbstractEvent {

    private Long id; //pay_id
    private Long reservedId;
    private String userId;
    private String userName;
    private PayType payType;
    private PayGubun payGubun;
    private String cardNumber;
    private Double amount;
    private Timestamp payDate;
    private Timestamp refundDate;
    private Double currentPoint;

}
