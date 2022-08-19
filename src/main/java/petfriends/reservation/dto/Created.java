package petfriends.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import petfriends.AbstractEvent;

import javax.persistence.Column;
import java.util.Date;

public class Created extends AbstractEvent {

    public Created(){
        super();
    }

    private Long reservedId;

    private Date startTime;

    private Date endTime;

    private Double amount;

    private Long dogwalkerScheduleId;

    private Integer status; // 1-요청중, 2-결재완료, 3-산책시작, 4-산책종료, 5-예약취소

    private String userId;

    private String userNm;


    public Long getReservedId() {
        return reservedId;
    }

    public void setReservedId(Long reservedId) {
        this.reservedId = reservedId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getDogwalkerScheduleId() {
        return dogwalkerScheduleId;
    }

    public void setDogwalkerScheduleId(Long dogwalkerScheduleId) {
        this.dogwalkerScheduleId = dogwalkerScheduleId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }


}