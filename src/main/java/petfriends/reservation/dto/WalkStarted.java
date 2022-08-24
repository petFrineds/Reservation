package petfriends.reservation.dto;

import petfriends.AbstractEvent;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class WalkStarted extends AbstractEvent {

    private Long id;
    

    private String walkStartDate;		// 산책 시작 일시분(실제)
    private String walkEndDate;			// 산책 종료 일시분(실제)
    
	@Enumerated(EnumType.STRING)
	private SmsStatus smsStatus;		// SMS 발송 상태 (START, END)
	
    private Long reservedId;			// 예약ID
    private String userId;				// 회원ID
    private String dogWalkerId;			// 도그워커ID

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public SmsStatus getSmsStatus() {
		return smsStatus;
	}

	public void setSmsStatus(SmsStatus smsStatus) {
		this.smsStatus = smsStatus;
	}

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

	public String getDogWalkerId() {
		return dogWalkerId;
	}

	public void setDogWalkerId(String dogWalkerId) {
		this.dogWalkerId = dogWalkerId;
	}

	
	
}