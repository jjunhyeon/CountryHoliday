package io.github.jjunhyeon.java21_assignment.api.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Getter;

/**
* @packageName    : io.github.jjunhyeon.java21_assignment.api.dto
* @fileName       : CountryDto.java
* @author         : JunHyeon
* @date           : 2025.11.28
* @description    : 국가명과 코드를 매핑하기 위한 DTO
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.11.28        KimJunHyeon      최초 생성
*/
@Getter
public class CountryDto {
	
	/** 국가명 */
	@JsonAlias("name")
	private String countryName;
	
	/** 국가코드 */
	private String countryCode;
}
