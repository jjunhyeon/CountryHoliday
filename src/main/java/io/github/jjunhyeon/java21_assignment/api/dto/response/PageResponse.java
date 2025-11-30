package io.github.jjunhyeon.java21_assignment.api.dto.response;

import org.springframework.data.domain.Page;

import lombok.Builder;
import lombok.Getter;
/**
* @packageName    : io.github.jjunhyeon.java21_assignment.api.dto.response
* @fileName       : PageResponse.java
* @author         : JunHyeon
* @date           : 2025.11.29
* @description    : 목록 API의 공통 페이징 응답을 위한 Class
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.11.29        KimJunHyeon      최초 생성
*/
@Builder
@Getter
public class PageResponse {

	private Integer page;
	private Integer size;
	private Long totalCount;
	private Integer totalPages;

	public static PageResponse from(Page<?> page) {
		return PageResponse.builder()
				.page(page.getNumber())
				.size(page.getSize())
				.totalCount(page.getTotalElements())
				.totalPages(page.getTotalPages()).build();
	}
}
