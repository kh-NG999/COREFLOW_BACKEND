package com.kh.coreflow.conference.model.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ConferenceRoomDto {

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Res {
		private Long roomId;
		private Long updateUserNo;
		private String roomName;
		private String buildingName;
		private String floor;
		private String roomNo;
		private Integer capacity;
		private String detailLocation;
		private String status;
		private Timestamp createDate;
		private Timestamp updateDate;
		private Long createUserNo;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class CreateReq{
		private String roomName;        
        private String buildingName;    
        private String floor;            
        private String roomNo;           
        private Integer capacity;        
        private String detailLocation;   
        private String status;    
	}
	// 회의실 예약
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class ReservationCreateReq{
		private Long roomId;
		private Timestamp startAt;
		private Timestamp endAt;
		private String title;
		private Long eventId;
	}
	// 예약 응답
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class ReservationRes {
		private Long resvId;
        private Long roomId;
        private Long userNo;
        private Timestamp startAt;
        private Timestamp endAt;
        private String status;        // HOLD | CONFIRMED | CANCELLED | EXPIRED
        private Long eventId;         // 일정과 연결되면 세팅(없으면 null)
        private Timestamp expiresAt;  // HOLD 만료시각(없으면 null)
        private String title;         // 예약 제목(없으면 null)
        private Timestamp createDate;
        private Timestamp updateDate;
	}
	// 예약 시간 변경 
    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class ReservationTimeUpdateReq {
        private Timestamp startAt;
        private Timestamp endAt;
    }

    // 예약에 이벤트 연결
    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class ReservationAttachEventReq {
        private Long eventId;
    }
    // 가용성 결과
    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class AvailabilityRes {
        private Long roomId;
        private String roomName;
        private Integer capacity;
        private String buildingName;
        private String floor;
        private boolean available;    // true면 해당 구간 예약 가능
    }
    // 상태 상수
    public static final class ReservationStatus {
        public static final String HOLD = "HOLD";
        public static final String CONFIRMED = "CONFIRMED";
        public static final String CANCELLED = "CANCELLED";
        public static final String EXPIRED = "EXPIRED";
        private ReservationStatus() {}
    }
}


















