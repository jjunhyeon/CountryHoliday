package io.github.jjunhyeon.java21_assignment.domain.repository;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import io.github.jjunhyeon.java21_assignment.domain.entity.HolidayCounty;
import io.github.jjunhyeon.java21_assignment.domain.entity.QHolidayCounty;
import lombok.RequiredArgsConstructor;

/**
* @packageName    : io.github.jjunhyeon.java21_assignment.domain.repository
* @fileName       : HolidayCountyRepositroyCustomImpl.java
* @author         : JunHyeon
* @date           : 2025.12.03
* @description    : 휴일 Enttiy 자식 테이블(County) RepositroyCustomImpl
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.12.03        KimJunHyeon      최초 생성
*/
@RequiredArgsConstructor
public class HolidayCountyRepositroyCustomImpl implements HolidayCountyRepositroyCustom{

	private final JPAQueryFactory queryFactory;

	@Override
	public List<HolidayCounty> findHolidayCountiesByHolidayIds(List<Long> holidayIds) {
		QHolidayCounty hc = QHolidayCounty.holidayCounty;
		return queryFactory.selectFrom(hc).where(hc.holiday.holidaySn.in(holidayIds)).fetch();
	}
}
