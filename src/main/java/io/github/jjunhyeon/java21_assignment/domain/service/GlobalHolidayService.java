package io.github.jjunhyeon.java21_assignment.domain.service;

import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import io.github.jjunhyeon.java21_assignment.api.dto.CountryDto;
import io.github.jjunhyeon.java21_assignment.api.dto.GlobalHolidayDto;
import io.github.jjunhyeon.java21_assignment.api.dto.request.GlobalHolidaySearchCondition;
import io.github.jjunhyeon.java21_assignment.config.WebClientConfig;
import io.github.jjunhyeon.java21_assignment.domain.entity.Holiday;
import io.github.jjunhyeon.java21_assignment.domain.repository.HolidayRepository;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * @packageName : io.github.jjunhyeon.java21_assignment.domain.service
 * @fileName : GlobalHolidayService.java
 * @author : JunHyeon
 * @date : 2025.11.28
 * @description : 국가의 공휴일 처리를 위한 클래스
 *              =========================================================== DATE
 *              AUTHOR NOTE
 *              -----------------------------------------------------------
 *              2025.11.28 KimJunHyeon 최초 생성
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GlobalHolidayService {

	private final WebClientConfig webClientConfig;
	private final HolidayRepository holidayRepository;
	
	/**
	 * 국가별 공휴일 목록 조회
	 */
	public Page<GlobalHolidayDto> searchGlobalHolidayByCondition(@RequestBody GlobalHolidaySearchCondition request){
		return holidayRepository.searchGlobalHolidayByCondition(request);
	}
	
	@Transactional(value = TxType.REQUIRED)
	public void refreshHolidays(String countryCode, Integer year) {
		
		if(log.isDebugEnabled()){
			log.debug("refrsh 처리 시작 [[요청 국가코드 :{} , 요청 년도 year : {}]]", countryCode, year);
		}
		
		WebClient webClient = webClientConfig.webClientBuilder().baseUrl("https://date.nager.at/api/v3/PublicHolidays").build();
		List<GlobalHolidayDto> GlobalHolidayDtoList = webClient.get()
		        .uri(uriBuilder -> uriBuilder
		                .pathSegment(String.valueOf(year), countryCode)
		                .build())
		        .retrieve()
		        .bodyToFlux(GlobalHolidayDto.class)
		        .collectList()
		        .block();
		
		if(!GlobalHolidayDtoList.isEmpty()) {
			int insertCount = 0;
			int updateCount = 0;
			for(final GlobalHolidayDto data : GlobalHolidayDtoList){
				Holiday db = holidayRepository.findByCountryCodeAndHolidayYmd(data.getCountryCode(), data.getHolidayYmd());
				if(db == null) {
					// insert
					Holiday holiday = new Holiday();
					BeanUtils.copyProperties(data.toHolidayEntity(), holiday);
					holidayRepository.save(holiday);
					insertCount ++;
				} else {
					Holiday newHoliday = data.toHolidayEntity();
				    if(!db.equals(newHoliday)) {
				    	System.out.println("equals?:" + newHoliday + "and old db:" + db);
				    	BeanUtils.copyProperties(newHoliday, db, "holidaySn", "regDate", "updateDate");
				    	updateCount ++;
				    }
				}
			}
			log.info("[refresh api called] insert : {} , update : {}", insertCount , updateCount);
		}
	}
	
	@Transactional(value = TxType.REQUIRED)
	public long deleteHolidays(String countryCode, Integer year) {
		return holidayRepository.deleteAllByCountryCodeAndYear(countryCode, year);
	}
	
	/**
	 * 최근 5년(2020~2025) 공휴일 수집 후, DB 적재 애플리케이션 시작과 함께 로드
	 * 어플리케이션 구동을 위한 최초 기초 데이터를 적재한다.
	 */
	public void loadDefaultData() {
		try {
			WebClient webClient = webClientConfig.webClientBuilder().baseUrl("https://date.nager.at/api/v3").build();
			// AvailableCountries 조회
			List<CountryDto> countryInfoList = webClient.get()
					.uri(uriBuilder -> uriBuilder.pathSegment("AvailableCountries").build()).retrieve()
					.bodyToFlux(CountryDto.class).collectList().block();

			if (countryInfoList == null || countryInfoList.isEmpty()) {
				log.error("[api/v3/AvailableCountries] API 데이터 누락, 상세 API 확인 필요");
				throw new RuntimeException("국가정보 데이터가 존재하지 않아 기동 실패했습니다.");
			}

			List<Integer> baseYears = IntStream.rangeClosed(Year.now().getValue() - 5, Year.now().getValue()).boxed()
					.collect(Collectors.toList());

			// 휴일 조회
			List<Holiday> holidayEntityList = Flux.fromIterable(countryInfoList)
					.flatMap(country -> Flux.fromIterable(baseYears)
							.flatMap(year -> webClient.get()
									.uri(uriBuilder -> uriBuilder.pathSegment("PublicHolidays", String.valueOf(year),
											country.getCountryCode()).build())
									.retrieve()
									.bodyToFlux(GlobalHolidayDto.class))
							.map(dto -> dto.toHolidayEntity()
							)).collectList().block();
			
			if (holidayEntityList == null || holidayEntityList.isEmpty()) {
				log.warn("적재할 데이터가 없습니다.");
			} else {
				log.info("BASE DATA INSERT STATRED ");
				holidayRepository.saveAll(holidayEntityList);
				log.info("BASE DATA LOAD END [Start Application]");
			}
		} catch (WebClientResponseException e) {
		    log.error("[WebClient 서버 에러] : (status {}): {}", e.getStatusCode(), e.getResponseBodyAsString(), e);
		    throw new IllegalStateException("외부 API 오류로 인해 서비스 기동 실패", e);
		}  catch (WebClientRequestException e) {
			log.error("[WebClient 클라이언트 에러] : " + e.getMessage());
			throw new IllegalStateException("외부 API 연결 실패로 서비스 기동 불가", e);
		} catch (Exception e) {
			log.error("[loadDefaultData service error]: " + e.getMessage());
			throw e;
		}
	}
}
