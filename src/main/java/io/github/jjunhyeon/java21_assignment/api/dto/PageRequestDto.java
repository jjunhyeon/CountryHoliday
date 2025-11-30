package io.github.jjunhyeon.java21_assignment.api.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import lombok.Getter;
import lombok.Setter;

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
public class PageRequestDto {
	
	/** 페이지 넘버*/
    private Integer page = 1;
    
    /** 조회할 페이즈 사이즈 */
    private Integer size = 10;
    
    public Pageable toPageable() {
    	if(page == 0) {
    		this.page  = 1;
    	}
    	
    	if(this.size ==0) {
    		this.size = 10;
    	}
    	return PageRequest.of(
    			this.page != null ? this.page : 1,
    					this.size != null ? this.size : 10);
    }
}
