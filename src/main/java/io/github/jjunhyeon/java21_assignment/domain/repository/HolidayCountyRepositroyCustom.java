package io.github.jjunhyeon.java21_assignment.domain.repository;

import java.util.List;

import io.github.jjunhyeon.java21_assignment.domain.entity.HolidayCounty;

/**
* @packageName    : io.github.jjunhyeon.java21_assignment.domain.repository
* @fileName       : HolidayCountyRepositroy.java
* @author         : JunHyeon
* @date           : 2025.12.03
* @description    : 휴일 Enttiy 자식 테이블(County) RepositroyCustom
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.12.03        KimJunHyeon      최초 생성
*/
public interface HolidayCountyRepositroyCustom {

	List<HolidayCounty> findHolidayCountiesByHolidayIds(List<Long> holidayIds);
}
