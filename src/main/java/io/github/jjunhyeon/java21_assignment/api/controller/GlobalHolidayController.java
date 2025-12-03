package io.github.jjunhyeon.java21_assignment.api.controller;

import java.time.Year;
import java.util.regex.Pattern;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.jjunhyeon.java21_assignment.api.dto.request.GlobalHolidaySearchCondition;
import io.github.jjunhyeon.java21_assignment.api.dto.response.GlobalHolidayDataResponse;
import io.github.jjunhyeon.java21_assignment.api.dto.response.GlobalHolidayPageResponse;
import io.github.jjunhyeon.java21_assignment.api.dto.response.PageResponse;
import io.github.jjunhyeon.java21_assignment.domain.service.GlobalHolidayService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

/**
* @packageName    : io.github.jjunhyeon.java21_assignment.api.controller
* @fileName       : HolidayController.java
* @author         : JunHyeon
* @date           : 2025.11.29
* @description    : 국가 공휴일 컨트롤러
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.11.29        KimJunHyeon      최초 생성
*/
@RestController
@RequiredArgsConstructor
@RequestMapping("/holidays")
public class GlobalHolidayController {

	private final GlobalHolidayService globalHolidayService;

	@GetMapping
	public ResponseEntity<GlobalHolidayPageResponse> searchGlobalHolidayByCondition(
			@Valid GlobalHolidaySearchCondition request) {

		if(request.getPage() < 1) {
			throw new ValidationException("페이지 번호는 1이상으로 입력해주세요.");
		} else if(request.getSize() < 1) {
			throw new ValidationException("페이지 사이즈는 1이상으로 입력해주세요.");
		}
		
		if (!request.isValidDateRange()) {
			throw new ValidationException("기간 조건이 잘못되었습니다. 시작일자값이 종료일자보다 이후일 수 없습니다.");
		}

		if (!request.isTypesValid()) {
			throw new ValidationException("공휴일 유형은 [Public, Bank, School, Observance, Optional] 중 하나로 선택해주세요.");
		}

		Page<GlobalHolidayDataResponse> list = globalHolidayService.searchGlobalHolidayByCondition(request);
		return ResponseEntity.ok(GlobalHolidayPageResponse.builder().searchCondition(request)
				.pageResponse(PageResponse.from(list)).data(list.getContent()).build());
	}

	/** 2. 특정 연도·국가 데이터 재동기화 (Upsert) */
	@PostMapping("/refresh")
	public ResponseEntity<Void> refreshHolidays(@RequestParam("countryCode") String code, @RequestParam("baseYear") Integer year) {
		validateParams(code, year);
		globalHolidayService.refreshHolidays(code, year);
		return ResponseEntity.ok().build();
	}

	/** 3. 특정 연도·국가 데이터 삭제 */
	@DeleteMapping
	public ResponseEntity<String> deleteHolidays(@RequestParam("countryCode") String code, @RequestParam("baseYear") Integer year) {
		validateParams(code, year);
		long count = globalHolidayService.deleteHolidays(code.toUpperCase(), year);
		return ResponseEntity.ok(count + "건 삭제 완료되었습니다.");
	}

	/** 공통 파라미터 검증 메서드 */
	private void validateParams(String countryCode, Integer year) {
		final int CURRENT_YEAR = Year.now().getValue();
		final Pattern COUNTRY_CODE_PATTERN = Pattern.compile("^[A-Z]{2}$");

		if (countryCode == null || countryCode.isBlank()) {
			throw new ValidationException("countryCode는 필수 입력값입니다.");
		}
		if (!COUNTRY_CODE_PATTERN.matcher(countryCode.toUpperCase()).matches()) {
			throw new ValidationException("countryCode는 2자리 국가코드 값으로 입력해주세요.");
		}
		if (year == null) {
			throw new ValidationException("연도값은 필수 입력값입니다.");
		}
		if (year < 2000 || year > CURRENT_YEAR) {
			throw new ValidationException(String.format("연도(YEAR)는 2000년도부터 현재년도(%d) 사이의 값으로 입력해주세요.", CURRENT_YEAR));
		}
	}
}
