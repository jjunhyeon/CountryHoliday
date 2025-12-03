package io.github.jjunhyeon.java21_assignment.api.dto.response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
* @packageName    : io.github.jjunhyeon.java21_assignment.api.dto.response
* @fileName       : HolidayResponse.java
* @author         : JunHyeon
* @date           : 2025.12.01
* @description    :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.12.01        KimJunHyeon      최초 생성
*/
@Getter
@Setter
public class GlobalHolidayDataResponse {

	/** 공휴일 일련번호 */
	private Long holidaySn;
	
	/** 공휴일 */
	private LocalDate holidayYmd;

	/** 현지공휴일명 */
	private String localHolidayName;

	/** 휴일명 */
	private String holidayName;

	/** 국가코드 */
	private String countryCode;

	/** 공휴일 유형[Public, Bank, School, Observance, Optional] */
	private List<String> types = new ArrayList<>();

	/** 매년 날짜가 동일한 공휴일 여부 */
	private String isFixedDateYn;

	/** 국가 전체 적용 여부 */
	private String isNationalHolidayYn;

	/** 특정 지역 또는 주만 쉬는 경우 지역 리스트 정보 */
	private List<String> counties = new ArrayList<>();

	/** 공휴일이 법적으로 지정된 첫 해 (없으면 null) */
	private String launchYear;
}
