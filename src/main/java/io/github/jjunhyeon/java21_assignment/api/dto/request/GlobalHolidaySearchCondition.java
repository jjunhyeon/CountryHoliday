package io.github.jjunhyeon.java21_assignment.api.dto.request;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.github.jjunhyeon.java21_assignment.api.dto.PageRequestDto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* @packageName    : io.github.jjunhyeon.java21_assignment.api.dto.request
* @fileName       : GlobalHolidaySearchCondition.java
* @author         : JunHyeon
* @date           : 2025.11.29
* @description    : 국가별 공휴일정보 API를 조회하기 위한 검색조건
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.11.29        KimJunHyeon      최초 생성
*/

@Getter
@Setter
@ToString
public class GlobalHolidaySearchCondition extends PageRequestDto {
	
    /** ISO 국가코드 (예: KR, US, JP) */
    @Size(min = 2, max = 2, message = "국가코드는 ISO 2자리 코드값으로 입력해주세요.")
    private String countryCode;

	 /** 국가명 */
	private String countryName;
	
    /** 조회 기준 연도 */
    @Min(value = 1999, message = "입력한 연도 정보를 다시 확인해주세요. 2000년도 이후로 검색 진행해주세요.")
    @Max(value = 2099, message = "입력한 연도 정보를 다시 확인해주세요. 2100년도 이전으로 검색 진행해주세요.")
    private Integer year;

    /** 공휴일 일자 조건 FROM */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate from;

    /** 공휴일 일자 조건 TO */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate to;
    
    /** 매년 날짜가 동일한 공휴일 여부 */
	private String isFixedDateYn;
	
	/** 국가 전체 적용 여부 */
	private String isNationalHolidayYn;
    
    /** 공휴일 유형[Public, Bank, School, Observance, Optional] */
    private List<String> types = new ArrayList<>();
    
	/** 특정 지역 또는 주만 쉬는 경우 지역 리스트 정보 */
 	private List<String> counties = new ArrayList<>();

    /** 날짜 검증 로직: 기간 역순 방지 */
    @JsonIgnore
    public boolean isValidDateRange() {
        return (from == null || to == null) || !from.isAfter(to);
    }
    
    @JsonIgnore
    public boolean isTypesValid() {
        if (types == null || types.isEmpty()) return true; // 선택 안 한 경우 허용
        // 허용된 Enum 값
        Set<String> allowed = Set.of("Public", "Bank", "School", "Observance", "Optional");
        return types.stream().allMatch(allowed::contains);
    }
}
