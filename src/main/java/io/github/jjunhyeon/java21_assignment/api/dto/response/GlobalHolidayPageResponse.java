package io.github.jjunhyeon.java21_assignment.api.dto.response;

import java.util.List;

import org.springframework.data.domain.Page;

import io.github.jjunhyeon.java21_assignment.api.dto.request.GlobalHolidaySearchCondition;
import lombok.Builder;
import lombok.Getter;

/**
* @packageName    : io.github.jjunhyeon.java21_assignment.api.response
* @fileName       : GlobalHolidayResponse.java
* @author         : JunHyeon
* @date           : 2025.11.29
* @description    : 국가별 휴일 정보 조회 Page Response
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.11.29        KimJunHyeon      최초 생성
*/
@Builder
@Getter
public class GlobalHolidayPageResponse {

	/** 실제 데이터 */ 
	private List<GlobalHolidayDataResponse> data;
	
	/** 검색조건 정보 */ 
	private GlobalHolidaySearchCondition searchCondition;
	
	/** 페이지 응답정보 */ 
	private PageResponse pageResponse;
	
	public static GlobalHolidayPageResponse from(Page<GlobalHolidayDataResponse> page, GlobalHolidaySearchCondition request) {
		return GlobalHolidayPageResponse.builder()
				.searchCondition(request)
				.pageResponse(PageResponse.from(page))
				.data(page.getContent()).build();
	}
}
