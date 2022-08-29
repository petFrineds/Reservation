package petfriends.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import petfriends.AbstractEvent;
import petfriends.reservation.model.ReservationStatus;

import javax.persistence.*;
import java.util.Date;


@Data
public class Created extends AbstractEvent {

    public Created(){
        super();
    }
    private Long reservedId;
    private Date startTime;
    private Date endTime;
    private Double amount;
    private Long dogwalkerScheduleId;
    private String dogwalkerId;
    private String dogwalkerName;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status; // 1-요청중, 2-결재완료, 3-산책시작, 4-산책종료, 5-포인트지급, 10-결재취소
    private String userId;
    private String userName;
    private Date regDate;
    private Date updDate;
}