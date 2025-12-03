package io.github.jjunhyeon.java21_assignment.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;

import io.github.jjunhyeon.java21_assignment.api.dto.request.GlobalHolidaySearchCondition;
import io.github.jjunhyeon.java21_assignment.api.dto.response.GlobalHolidayDataResponse;
import io.github.jjunhyeon.java21_assignment.domain.entity.Holiday;

/**
* @packageName    : io.github.jjunhyeon.java21_assignment.domain.repository
* @fileName       : GlobalHolidayRepositoryCustom.java
* @author         : JunHyeon
* @date           : 2025.11.29
* @description    : 국가 공휴일 관리 RepositoryCustom
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.11.29        KimJunHyeon      최초 생성
*/
public interface HolidayRepositoryCustom {

	Page<GlobalHolidayDataResponse> searchGlobalHolidayByCondition(GlobalHolidaySearchCondition condition);
	
	List<Holiday> findByCountryCodeAndYear(String countryCode, Integer Year);
	
	long deleteAllByCountryCodeAndYear(String countryCode, Integer Year);
}
