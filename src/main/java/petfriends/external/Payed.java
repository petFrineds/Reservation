package petfriends.external;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Transient;
import petfriends.AbstractEvent;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Timestamp;

public class Payed extends AbstractEvent {

    private Long id;
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReservedId() {
        return reservedId;
    }

    public void setReservedId(Long reservedId) {
        this.reservedId = reservedId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public PayGubun getPayGubun() {
        return payGubun;
    }

    public void setPayGubun(PayGubun payGubun) {
        this.payGubun = payGubun;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Timestamp getPayDate() {
        return payDate;
    }

    public void setPayDate(Timestamp payDate) {
        this.payDate = payDate;
    }

    public Timestamp getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(Timestamp refundDate) {
        this.refundDate = refundDate;
    }

    public Double getCurrentPoint() {
        return currentPoint;
    }

    public void setCurrentPoint(Double currentPoint) {
        this.currentPoint = currentPoint;
    }

}
