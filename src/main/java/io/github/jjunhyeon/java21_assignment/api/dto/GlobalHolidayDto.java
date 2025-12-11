package io.github.jjunhyeon.java21_assignment.api.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.jjunhyeon.java21_assignment.domain.entity.Holiday;
import io.github.jjunhyeon.java21_assignment.domain.entity.HolidayCounty;
import io.github.jjunhyeon.java21_assignment.domain.entity.HolidayType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* @packageName    : io.github.jjunhyeon.java21_assignment.api.dto
* @fileName       : HolidayDto.java
* @author         : JunHyeon
* @date           : 2025.11.29
* @description    : 국가별 연휴 정보를 매핑하기 위한 DTO
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.11.29        KimJunHyeon      최초 생성
*/
@Getter
@Setter
@ToString
public class GlobalHolidayDto {

	/** 공휴일 */
	@JsonAlias("date")
	private LocalDate holidayYmd;

	/** 현지공휴일명 */
	@JsonProperty("localName")
	private String localHolidayName;

	/** 국가명 */
	@JsonAlias("name")
	private String holidayName;

	/** 국가코드 */
	private String countryCode;

	/** 공휴일 유형[Public, Bank, School, Observance, Optional] */
	private List<String> types;

	/** 매년 날짜가 동일한 공휴일 여부 */
	@JsonAlias("fixed")
	private String isFixedDateYn;

	/** 국가 전체 적용 여부 */
	@JsonAlias("global")
	private String isNationalHolidayYn;

	/** 특정 지역 또는 주만 쉬는 경우 지역 리스트 정보 */
	private List<String> counties;

	/** 공휴일이 법적으로 지정된 첫 해 (없으면 null) */
	private String launchYear;
	
	/** 동기화여부 */
	private boolean isSyncRequired = false;

	public Holiday toHolidayEntity() {
		Holiday entity = new Holiday();
		BeanUtils.copyProperties(this, entity);
		if (this.isFixedDateYn != null) {
			entity.setIsFixedDateYn(this.isFixedDateYn.equals("true") ? "Y" : "N");
		}

		if (this.isNationalHolidayYn != null) {
			entity.setIsNationalHolidayYn(this.isNationalHolidayYn.equals("true") ? "Y" : "N");
		}

		if (this.types != null && !this.types.isEmpty()) {
			List<HolidayType> holidayType = this.types.stream().map(typeStr -> {
				HolidayType ht = new HolidayType();
				ht.setType(typeStr);
				ht.setHoliday(entity);
				return ht;
			}).toList();
			entity.setTypes(holidayType);
		}

		if (this.counties != null && this.counties.isEmpty()) {
			List<HolidayCounty> holidayCounty = this.counties.stream().map(county -> {
				HolidayCounty hc = new HolidayCounty();
				hc.setCounty(county);
				hc.setHoliday(entity);
				return hc;
			}).toList();
			entity.setCounties(holidayCounty);
		}

		return entity;
	}
}