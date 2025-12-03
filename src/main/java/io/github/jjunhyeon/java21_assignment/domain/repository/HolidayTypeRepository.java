package io.github.jjunhyeon.java21_assignment.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.jjunhyeon.java21_assignment.domain.entity.HolidayType;
import io.github.jjunhyeon.java21_assignment.domain.entity.id.HolidayTypeId;

/**
* @packageName    : io.github.jjunhyeon.java21_assignment.domain.repository
* @fileName       : HolidayTypeRepository.java
* @author         : JunHyeon
* @date           : 2025.12.03
* @description    :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.12.03        KimJunHyeon      최초 생성
*/
public interface HolidayTypeRepository extends JpaRepository<HolidayType, HolidayTypeId>, HolidayTypeCustomRepository{

}
