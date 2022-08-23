package petfriends.reservation.dto;

import lombok.Data;
import petfriends.AbstractEvent;

import java.util.Date;

public class Canceled extends AbstractEvent {

    public Canceled(){
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


    @Data
    public static class WalkEnded extends AbstractEvent {

        private Long id;
        private String walkStartDate;		// 산책 시작 일시분(실제)
        private String walkEndDate;			// 산책 종료 일시분(실제)
        private Long reservedId;			// 예약ID
        private String userId;				// 회원ID
        private String dogWalkerId;			// 도그워커ID

        public WalkEnded(){
            super();
        }

    }

    @Data
    public static class WalkStarted extends AbstractEvent {

        private Long id;
        private String walkStartDate;		// 산책 시작 일시분(실제)
        private String walkEndDate;			// 산책 종료 일시분(실제)
        private Long reservedId;			// 예약ID
        private Long userId;				// 회원ID
        private Long dogWalkerId;			// 도그워커ID

        public WalkStarted(){
            super();
        }
    }
}