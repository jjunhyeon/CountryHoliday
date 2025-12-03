package io.github.jjunhyeon.java21_assignment.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.jjunhyeon.java21_assignment.domain.entity.HolidayCounty;
import io.github.jjunhyeon.java21_assignment.domain.entity.id.HolidayCountyId;

/**
* @packageName    : io.github.jjunhyeon.java21_assignment.domain.repository
* @fileName       : HolidayCountyRepository.java
* @author         : JunHyeon
* @date           : 2025.12.03
* @description    : 휴일 Enttiy 자식 테이블(County) Repositroy Repository
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.12.03        KimJunHyeon      최초 생성
*/
public interface HolidayCountyRepository extends JpaRepository<HolidayCounty, HolidayCountyId>, HolidayCountyRepositroyCustom {

}
