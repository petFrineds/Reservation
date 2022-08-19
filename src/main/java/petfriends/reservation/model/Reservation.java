package petfriends.reservation.model;

import java.util.Date;

import javax.persistence.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonFormat;

import petfriends.ReservationApplication;
import petfriends.reservation.dto.Created;
import petfriends.reservation.dto.StatusUpdated;

@Entity
@Table(name="reservation")
@Slf4j
public class Reservation {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private Long reservedId;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "start_time")
	private Date startTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "end_time")
	private Date endTime;

	private Double amount;

	@Column(name="dogwalker_schedule_id")
	private Long dogwalkerScheduleId;

    @Column(name="status")
    private Integer status; // 1-요청중, 2-결재완료, 3-산책시작, 4-산책종료, 5-예약취소
	@Column(name="user_id")
	private String userId;

	@Column(name="user_nm")
	private String userNm;


    @PostPersist
    public void onPostPersist(){
//        Created created = new Created();
//        BeanUtils.copyProperties(this, created);
//        created.publishAfterCommit();

        log.info("!!!!!!!!!!!!!!!!!onPostPersist --> " + this.getReservedId().toString() + " // " + this.getStatus());

    }

    @PostUpdate
    public void onPostUpdate(){
//        StatusUpdated statusUpdated = new StatusUpdated();
//        BeanUtils.copyProperties(this, statusUpdated);
//        statusUpdated.publishAfterCommit();

        log.info("!!!!!!!!!!!!!!!!!onPostUpdate1 --> " + this.getReservedId().toString() + " // " + this.getStatus());
        if(this.getStatus() == 5) { //예약취소일때
            log.info("!!!!!!!!!!!!!!!!!onPostUpdate2 --> " + this.getReservedId().toString() + " // " + this.getStatus());
            String reservedId = this.getReservedId().toString();
            ReservationApplication.applicationContext.getBean(petfriends.external.PaymentService.class)
                    .doPayment(reservedId);


        }
    }




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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


}
