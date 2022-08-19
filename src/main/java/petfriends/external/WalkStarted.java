package petfriends.external;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonFormat;

import petfriends.AbstractEvent;
//import petfriends.reservation.model.SmsStatus;

public class WalkStarted extends AbstractEvent {

    private Long id;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    //private LocalDateTime walkStartDate;			// 산책 시작 일시(실제)

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    //private LocalDateTime walkEndDate;				// 산책 종료 일시(실제)

    private String walkStartDate;		// 산책 시작 일시분(실제)
    private String walkEndDate;			// 산책 종료 일시분(실제)

//    @Enumerated(EnumType.STRING)
//    private SmsStatus smsStatus;		// SMS 발송 상태 (START, END)

    private Long reservedId;			// 예약ID
    private Long userId;				// 회원ID
    private Long dogWalkerId;			// 도그워커ID

    public WalkStarted(){
        super();
    }

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDogWalkerId() {
        return dogWalkerId;
    }

    public void setDogWalkerId(Long dogWalkerId) {
        this.dogWalkerId = dogWalkerId;
    }

//    public SmsStatus getSmsStatus() {
//        return smsStatus;
//    }
//
//    public void setSmsStatus(SmsStatus smsStatus) {
//        this.smsStatus = smsStatus;
//    }

    public String getWalkStartDate() {
        return walkStartDate;
    }

    public void setWalkStartDate(String walkStartDate) {
        this.walkStartDate = walkStartDate;
    }

    public String getWalkEndDate() {
        return walkEndDate;
    }

    public void setWalkEndDate(String walkEndDate) {
        this.walkEndDate = walkEndDate;
    }

}