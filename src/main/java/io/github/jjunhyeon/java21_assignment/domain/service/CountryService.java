package io.github.jjunhyeon.java21_assignment.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import io.github.jjunhyeon.java21_assignment.api.dto.CountryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
* @packageName    : io.github.jjunhyeon.java21_assignment.domain.service
* @fileName       : CountryService.java
* @author         : JunHyeon
* @date           : 2025.12.01
* @description    :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.12.01        KimJunHyeon      최초 생성
*/
@Service
@Slf4j
@RequiredArgsConstructor
public class CountryService {

	private final WebClient webClient;
	
	/**
	 * [외부 API] 국가코드 API에서 사용 가능한 국가 목록을 조회
	 * @return 외부 API에서 조회한 국가 목록
	 */
    public List<CountryDto> fetchAvailableCountries() {
        try {
            return webClient.get()
                    .uri("/AvailableCountries")
                    .retrieve()
                    .bodyToFlux(CountryDto.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException e) {
	        log.error("[WebClient 서버 에러] : (status {}): {}", e.getStatusCode(), e.getResponseBodyAsString(), e);
	        throw new IllegalStateException("외부 국가코드 목록 API 오류로 인해 서비스 기동 실패", e);
	    } catch (WebClientRequestException e) {
	        log.error("[WebClient 클라이언트 에러] : {}", e.getMessage());
	        throw new IllegalStateException("외부 국가코드 목록 API 연결 실패로 서비스 기동 불가", e);
	    } catch (Exception e) {
	        log.error("[fetchAvailableCountries service error]: {}", e.getMessage(), e);
	        throw e;
	    }
    }
}
