package io.github.jjunhyeon.java21_assignment.domain.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.jjunhyeon.java21_assignment.domain.entity.Holiday;

/**
* @packageName    : io.github.jjunhyeon.java21_assignment.domain.repository
* @fileName       : GlobalHolidayRepository.java
* @author         : JunHyeon
* @date           : 2025.11.29
* @description    : 국가 공휴일 관리 Repository
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.11.29        KimJunHyeon      최초 생성
*/
public interface HolidayRepository extends JpaRepository<Holiday, Long>, HolidayRepositoryCustom{
	Holiday findByCountryCodeAndHolidayYmd(String countryCode, LocalDate holidayYmd);
}
