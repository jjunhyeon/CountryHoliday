package io.github.jjunhyeon.java21_assignment;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import io.github.jjunhyeon.java21_assignment.api.controller.GlobalHolidayController;
import io.github.jjunhyeon.java21_assignment.api.dto.request.GlobalHolidaySearchCondition;
import io.github.jjunhyeon.java21_assignment.api.dto.response.GlobalHolidayDataResponse;
import io.github.jjunhyeon.java21_assignment.domain.service.GlobalHolidayService;
/**
* @packageName    : io.github.jjunhyeon.java21_assignment
* @fileName       : GlobalHolidayControllerTest.java
* @author         : JunHyeon
* @date           : 2025.12.03
* @description    :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.12.03        KimJunHyeon      최초 생성
*/
@WebMvcTest(GlobalHolidayController.class)
@MockBean(JpaMetamodelMappingContext.class)
@DisplayName("정상적인 조건으로 요청 시 200 응답과 예상한 데이터 반환")
class GlobalHolidayControllerTest {

	@MockitoBean
	private GlobalHolidayService globalHolidayService;
	
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    @DisplayName("정상 페이징 조회 및 200 응답 확인")
    void searchGlobalHoliday_success() throws Exception {
        
    	// 검색 가정 기본 정보
        GlobalHolidaySearchCondition request = new GlobalHolidaySearchCondition();
        request.setPage(1);
        request.setSize(10);
        request.setCountryCode("KR");

        GlobalHolidayDataResponse response = new GlobalHolidayDataResponse();
        response.setHolidaySn(1L);
        response.setHolidayYmd(LocalDate.of(2025, 12, 25));
        response.setLocalHolidayName("크리스마스");
        response.setHolidayName("Christmas Day");
        response.setCountryCode("KR");
        
        Page<GlobalHolidayDataResponse> page =new PageImpl<>(List.of(response));
        // 세팅한 정보로 return
        given(globalHolidayService.searchGlobalHolidayByCondition(any())).willReturn(page);

        mockMvc.perform(get("/holidays")
        		.param("page", "1")         
                .param("size", "10")        
                .param("countryCode", "KR"))
                .andExpect(status().isOk()) // 1. 응답상태 200 검증
                .andExpect(jsonPath("$.data").isArray()) // data 배열 확인
                .andExpect(jsonPath("$.data[0].countryCode").value("KR"))
                .andExpect(jsonPath("$.data[0].holidayName").value("Christmas Day")); // 응답으로 나온 정보가 내가 세팅한 KR인지
    }
    
    /**
     * 필수값 누락으로 인한 , 400 응답 검증
     */
    @Test
    @DisplayName("휴일 검색 실패: 필수 파라미터(countryCode) 누락 시 400 Bad Request 반환")
    void searchGlobalHoliday_validation_fail_byPageNumber() throws Exception {
        // CountryCode 없이 요청 (유효성 검사 실패 유도)
    	 mockMvc.perform(get("/holidays")
                .param("page", "-2")         
                .param("size", "10")        
                .param("countryCode", "KR")
            ).andExpect(status().isBadRequest()) 
    	        .andExpect(jsonPath("$.status").value(400))
    	        .andExpect(jsonPath("$.message").value("Validation Failed"))
    	        .andExpect(jsonPath("$.detail").value("페이지 번호는 1이상으로 입력해주세요."))
    			.andExpect(jsonPath("$.message", startsWith("Validation Failed")))
            		.andReturn(); // MvcResult 객체를 반환받습니다.
    }
}
