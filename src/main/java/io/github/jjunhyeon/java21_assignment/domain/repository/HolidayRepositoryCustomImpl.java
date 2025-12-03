package io.github.jjunhyeon.java21_assignment.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.github.jjunhyeon.java21_assignment.api.dto.request.GlobalHolidaySearchCondition;
import io.github.jjunhyeon.java21_assignment.api.dto.response.GlobalHolidayDataResponse;
import io.github.jjunhyeon.java21_assignment.domain.entity.Holiday;
import io.github.jjunhyeon.java21_assignment.domain.entity.QHoliday;
import lombok.RequiredArgsConstructor;

/**
* @packageName    : io.github.jjunhyeon.java21_assignment.domain.repository
* @fileName       : GlobalHolidayRepositoryCustomImpl.java
* @author         : JunHyeon
* @date           : 2025.11.29
* @description    : 국가 공휴일 관리 RepositoryCustomImpl
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.11.29        KimJunHyeon      최초 생성
*/
@RequiredArgsConstructor
public class HolidayRepositoryCustomImpl implements HolidayRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<GlobalHolidayDataResponse> searchGlobalHolidayByCondition(GlobalHolidaySearchCondition condition) {

		QHoliday h = QHoliday.holiday;
		Pageable pageable = condition.toPageable();

		List<GlobalHolidayDataResponse> data = queryFactory
				.select(Projections.fields(GlobalHolidayDataResponse.class, h.holidaySn, h.holidayYmd,
						h.localHolidayName, h.holidayName, h.countryCode, h.isFixedDateYn, h.isNationalHolidayYn,
						h.launchYear))
				.from(h).where(buildHolidaySearchCondition(condition, h)).offset(pageable.getOffset())
				.limit(pageable.getPageSize()).fetch();

		long totalCount = queryFactory.select(h.count()).from(h).where(buildHolidaySearchCondition(condition, h))
				.fetchOne();

		return new PageImpl<>(data, pageable, totalCount);
	}

	/**
	 * Holiday 검색 조건을 QueryDSL BooleanBuilder 형태로 생성한다. - 전달받은 검색 조건이 null이 아닐 경우에만
	 * Query 조건으로 추가한다. - 값이 존재하지 않는 조건은 자동으로 무시되어 전체 검색이 가능하다.
	 */
	private BooleanBuilder buildHolidaySearchCondition(GlobalHolidaySearchCondition condition, QHoliday h) {
		BooleanBuilder builder = new BooleanBuilder();
		// 국가코드 비교 조건
		if (condition.getCountryCode() != null) {
			builder.and(h.countryCode.eq(condition.getCountryCode().toUpperCase()));
		}

		// 휴일명명 비교 조건
		if (condition.getHolidayName() != null) {
			builder.and(h.holidayName.like(condition.getHolidayName() + "%"));
		}
		
		// 로컬휴일명 비교 조건
		if (condition.getLocalHolidayName() != null) {
			builder.and(h.localHolidayName.like(condition.getLocalHolidayName() + "%"));
		}
		
		// 년도 비교 조건
		if (condition.getYear() != null) {
			builder.and(h.holidayYmd.year().eq(condition.getYear()));
		}

		// from 일자 비교 조건
		if (condition.getFrom() != null) {
			builder.and(h.holidayYmd.goe(condition.getFrom()));
		}

		// to 일자 비교조건
		if (condition.getTo() != null) {
			builder.and(h.holidayYmd.loe(condition.getTo()));
		}

		// isFixedDateYn 비교 조건
		if (condition.getIsFixedDateYn() != null) {
			builder.and(h.isFixedDateYn.eq(condition.getIsFixedDateYn().toUpperCase()));
		}

		// isNationalHolidayYn 비교 조건
		if (condition.getIsNationalHolidayYn() != null) {
			builder.and(h.isNationalHolidayYn.eq(condition.getIsNationalHolidayYn().toUpperCase()));
		}

		// 특정 지역 또는 주만 쉬는 경우 지역 리스트 정보
		if (condition != null && !condition.getCounties().isEmpty()) {
			builder.and(h.counties.any().county.in(condition.getCounties()));
		}

		if (condition != null && !condition.getTypes().isEmpty()) {
			builder.and(h.types.any().type.in(condition.getTypes()));
		}

		return builder;
	}

	@Override
	public long deleteAllByCountryCodeAndYear(String countryCode, Integer year) {
		QHoliday h = QHoliday.holiday;
		return queryFactory.delete(h).where(h.countryCode.eq(countryCode).and(h.holidayYmd.year().eq(year))).execute();
	}

	@Override
	public List<Holiday> findByCountryCodeAndYear(String countryCode, Integer year) {
		QHoliday h = QHoliday.holiday;
		return queryFactory.selectFrom(h).where(h.countryCode.eq(countryCode).and(h.holidayYmd.year().eq(year)))
				.fetch();
	}
}
