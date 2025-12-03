package io.github.jjunhyeon.java21_assignment.domain.repository;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import io.github.jjunhyeon.java21_assignment.domain.entity.HolidayType;
import io.github.jjunhyeon.java21_assignment.domain.entity.QHolidayType;
import lombok.RequiredArgsConstructor;

/**
* @packageName    : io.github.jjunhyeon.java21_assignment.domain.repository
* @fileName       : HolidayTypeRepositoryImpl.java
* @author         : JunHyeon
* @date           : 2025.12.03
* @description    :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.12.03        KimJunHyeon      최초 생성
*/
@RequiredArgsConstructor
public class HolidayTypeCustomRepositoryImpl implements HolidayTypeCustomRepository {
	
	private final JPAQueryFactory queryFactory;
	@Override
	public List<HolidayType> findHolidayTypesByHolidayIds(List<Long> holidayIds) {
		QHolidayType ht = QHolidayType.holidayType;
		return queryFactory.selectFrom(ht).where(ht.holiday.holidaySn.in(holidayIds)).fetch();
	}
}
