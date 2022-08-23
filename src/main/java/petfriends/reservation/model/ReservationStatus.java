package petfriends.reservation.model;

public enum ReservationStatus {

    REQUEST, //예약중 : 0
    PAYED,   //결재완료 : 1
    START,   //산책시작 : 2
    END,     //산책종료 : 3

    POINT,   // 포인트 지급 : 4
    CANCEL   // 예약취소 : 5
}
