package io.github.jjunhyeon.java21_assignment.domain.repository;

import org.springframework.data.domain.Page;

import io.github.jjunhyeon.java21_assignment.api.dto.GlobalHolidayDto;
import io.github.jjunhyeon.java21_assignment.api.dto.request.GlobalHolidaySearchCondition;

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

	Page<GlobalHolidayDto> searchGlobalHolidayByCondition(GlobalHolidaySearchCondition condition);
	
	long deleteAllByCountryCodeAndYear(String countryCode, Integer Year);
}
