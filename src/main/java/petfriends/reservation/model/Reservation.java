package petfriends.reservation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import petfriends.ReservationApplication;
import petfriends.reservation.dto.Created;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="reservation")
@Slf4j
@Data
public class Reservation {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private Long reservedId;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "start_time")
	private Date startTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "end_time")
	private Date endTime;

    @Column(name="amount")
	private Double amount;

	@Column(name="dogwalker_schedule_id")
	private Long dogwalkerScheduleId;

    @Column(name="dogwalker_id")
    private String dogwalkerId;

    @Column(name="dogwalker_name")
    private String dogwalkerName;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private ReservationStatus status; // 1-요청중, 2-결재완료, 3-산책시작, 4-산책종료, 5-포인트지급, 10-결재취소

	@Column(name="user_id")
	private String userId;

	@Column(name="user_name")
	private String userName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="reg_date")
    private Date regDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="upd_date")
    private Date updDate;

    @PostPersist
    public void onPostPersist(){

        //예약 생성
        Created created = new Created();
        BeanUtils.copyProperties(this, created);
        created.publishAfterCommit();
        log.info("!!!!!!!!!!!!!!!!!onPostPersist --> " + this.getReservedId().toString() + " // " + this.getStatus());

    }
    @PostUpdate
    public void onPostUpdate(){

        log.info("!!!!!!!!!!!!!!!!!onPostUpdate1 --> " + this.getReservedId().toString() + " // " + this.getStatus());

        if(this.getStatus() == ReservationStatus.CANCEL) { //예약취소일때
            log.info("!!!!!!!!!!!!!!!!!onPostUpdate2 --> " + this.getReservedId().toString() + " // " + this.getStatus());
            String reservedId = this.getReservedId().toString();
            ReservationApplication.applicationContext.getBean(petfriends.external.PaymentService.class)
                    .doPayment(reservedId);
        }
    }

    private String getCircuitBreakerFallback(Throwable t) {
        return "getCircuitBreakerFallback! exception type: " + t.getClass() + "exception, message: " + t.getMessage();
    }

}
