package io.github.jjunhyeon.java21_assignment.api.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* @packageName    : io.github.jjunhyeon.java21_assignment.api.dto
* @fileName       : PageRequestDTO.java
* @author         : JunHyeon
* @date           : 2025.11.29
* @description    : [공통] 페이지 요청 DTO
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.11.29        KimJunHyeon      최초 생성
*/
@Getter
@Setter
@ToString
public class PageRequestDto {
	
	/** 페이지 넘버*/
	@Schema(description = "페이징 번호", example = "1")
    private Integer page = 1;
    
    /** 조회할 페이즈 사이즈 */
	@Schema(description = "페이징 사이즈", example = "10")
    private Integer size = 10;
    
    public Pageable toPageable() {
        // 기본값 처리
        int pageNo = (page != null && page > 0) ? page : 1; // 1-based 유지
        int pageSize = (size != null && size > 0) ? size : 10;
        return PageRequest.of(pageNo - 1, pageSize);
    }
}
