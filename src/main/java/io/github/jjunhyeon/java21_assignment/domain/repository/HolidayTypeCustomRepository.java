package io.github.jjunhyeon.java21_assignment.domain.repository;

import java.util.List;

import io.github.jjunhyeon.java21_assignment.domain.entity.HolidayType;

/**
* @packageName    : io.github.jjunhyeon.java21_assignment.domain.repository
* @fileName       : HolidayTypeCustomRepository.java
* @author         : JunHyeon
* @date           : 2025.12.03
* @description    :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.12.03        KimJunHyeon      최초 생성
*/
public interface HolidayTypeCustomRepository {

	List<HolidayType> findHolidayTypesByHolidayIds(List<Long> holidayIds);
}
