package com.kh.coreflow.calendar.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class EventDto {

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class EventRowDTO {
		private Long eventId; // 이벤트 ID
		private Long calId; // 캘린더 ID
		private String title; // 제목
		private String startAt; // ISO 문자열 (SQL에서 TO_CHAR)
		private String endAt; // ISO 문자열
		private String allDayYn; // 'Y' / 'N'

	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Res {
		private Long eventId;
		private Long calId;
		private String title;
		private LocalDateTime startAt;
		private LocalDateTime endAt;
		private String allDayYn; // 'Y'/'N'
		private String locationText;
		private String note;
		private Long roomId; // nullable
		private String status; // e.g. 'CONFIRMED','TENTATIVE','DELETED'
		private Long labelId; // nullable
		private String eventType; // 'MEETING','TASK',...
		private String rrule; // nullable
		private String exdates; // nullable
		private Long createByUserNo;
		private LocalDateTime createDate;
		private Long updateUserNo;
		private LocalDateTime updateDate;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Create {
		// selectKey로 채워질 필드(INSERT 후 사용)
		private Long eventId;
		private Long calId;
		private String title;
		private LocalDateTime startAt;
		private LocalDateTime endAt;
		private Boolean allDay; // true면 allDayYn='Y'
		private String locationText;
		private String note;
		private Long roomId; // 선택
		private String eventType; // 선택
		private Long labelId; // 선택
		private String rrule; // 선택
		private String exdates; // 선택
	}

	@FunctionalInterface
	public interface TimeConverter {
		LocalDateTime parse(String iso);

		default String format(LocalDateTime ldt, boolean allDay) {
			if (ldt == null)
				return null;
			// allDay면 날짜만 반환하고 싶다면 여기에서 date-only로 변경 가능
			return ldt.toString();
		}
	}

	/**
	 * ISO 문자열 <-> LocalDateTime 변환기 - 서버 내부는 Asia/Seoul 기준으로 LocalDateTime 운용 -
	 * allDay=true면 날짜만 들어오는 경우가 있어 보정 처리
	 */
	public static TimeConverter seoul() {
		return new TimeConverter() {
			@Override
			public LocalDateTime parse(String iso) {
				if (iso == null)
					return null;
				// 다양한 형태의 ISO 문자열을 수용
				// 1) 2025-09-02T10:00:00
				// 2) 2025-09-02
				// 3) 2025-09-02T10:00:00+09:00
				try {
					if (iso.length() == 10) {
						// date-only
						LocalDate d = LocalDate.parse(iso);
						return d.atStartOfDay();
					}
					// Offset 포함
					if (iso.contains("+") || iso.endsWith("Z")) {
						OffsetDateTime odt = OffsetDateTime.parse(iso);
						return odt.atZoneSameInstant(ZoneId.of("Asia/Seoul")).toLocalDateTime();
					}
					// 기본 ISO
					return LocalDateTime.parse(iso);
				} catch (Exception e) {
					// 마지막 fallback
					return LocalDateTime.parse(iso, DateTimeFormatter.ISO_DATE_TIME);
				}
			}

			@Override
			public String format(LocalDateTime ldt, boolean allDay) {
				if (ldt == null)
					return null;
				if (allDay) {
					return ldt.toLocalDate().toString(); // allDay는 date-only로 응답(FC가 자동 처리)
				}
				return ldt.toString(); // 2025-09-02T10:00:00
			}
		};
	}
}