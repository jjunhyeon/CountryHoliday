package io.github.jjunhyeon.java21_assignment.domain.service;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import io.github.jjunhyeon.java21_assignment.api.dto.CountryDto;
import io.github.jjunhyeon.java21_assignment.api.dto.GlobalHolidayDto;
import io.github.jjunhyeon.java21_assignment.api.dto.request.GlobalHolidaySearchCondition;
import io.github.jjunhyeon.java21_assignment.api.dto.response.GlobalHolidayDataResponse;
import io.github.jjunhyeon.java21_assignment.domain.entity.Holiday;
import io.github.jjunhyeon.java21_assignment.domain.entity.HolidayCounty;
import io.github.jjunhyeon.java21_assignment.domain.entity.HolidayType;
import io.github.jjunhyeon.java21_assignment.domain.repository.HolidayCountyRepository;
import io.github.jjunhyeon.java21_assignment.domain.repository.HolidayRepository;
import io.github.jjunhyeon.java21_assignment.domain.repository.HolidayTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @packageName : io.github.jjunhyeon.java21_assignment.domain.service
 * @fileName : GlobalHolidayService.java
 * @author : JunHyeon
 * @date : 2025.11.28
 * @description : 국가의 공휴일 처리를 위한 클래스
 * =========================================================== DATE
 * AUTHOR NOTE
 * -----------------------------------------------------------
 * 2025.11.28 KimJunHyeon 최초 생성
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GlobalHolidayService {

	private final WebClient webClient;
	private final HolidayRepository holidayRepository;
	private final HolidayCountyRepository holidayCountyRepository;
	private final HolidayTypeRepository holidayTypeRepository;
	private final CountryService countryService ;

	/**
	 * 국가별 공휴일 목록 조회
	 */
	public Page<GlobalHolidayDataResponse> searchGlobalHolidayByCondition(GlobalHolidaySearchCondition request) {

		Page<GlobalHolidayDataResponse> pageResult = holidayRepository.searchGlobalHolidayByCondition(request);
		List<GlobalHolidayDataResponse> db = holidayRepository.searchGlobalHolidayByCondition(request).getContent();

		List<Long> holidayIds = db.stream().map(GlobalHolidayDataResponse::getHolidaySn).collect(Collectors.toList());
		List<HolidayType> hTypes = holidayTypeRepository.findHolidayTypesByHolidayIds(holidayIds);
		List<HolidayCounty> hCounty = holidayCountyRepository.findHolidayCountiesByHolidayIds(holidayIds);

		Map<Long, List<String>> typesMap = hTypes.stream().collect(Collectors.groupingBy(
				ht -> ht.getHoliday().getHolidaySn(), Collectors.mapping(HolidayType::getType, Collectors.toList())));

		Map<Long, List<String>> countiesMap = hCounty.stream()
				.collect(Collectors.groupingBy(hc -> hc.getHoliday().getHolidaySn(),
						Collectors.mapping(HolidayCounty::getCounty, Collectors.toList())));
		db.forEach(dto -> {
			Long holidaySn = dto.getHolidaySn(); // DTO에 부모 PK 필드 필요
			dto.setTypes(typesMap.getOrDefault(holidaySn, new ArrayList<>()));
			dto.setCounties(countiesMap.getOrDefault(holidaySn, new ArrayList<>()));
		});
		return new PageImpl<>(db, request.toPageable(), pageResult.getTotalElements());
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void refreshHolidays(String countryCode, Integer year) {

		if (log.isDebugEnabled()) {
			log.debug("refrsh 처리 시작 [[요청 국가코드 :{} , 요청 년도 year : {}]]", countryCode, year);
		}
		List<GlobalHolidayDto> GlobalHolidayDtoList = fetchHolidays(year, countryCode);
		
		if (!GlobalHolidayDtoList.isEmpty()) {
			int insertCount = 0;
			int updateCount = 0;
			for (final GlobalHolidayDto dto : GlobalHolidayDtoList) {

				// 같은 중복 데이터 존재할 수 있음
				// 5월 5일의 경우 kr 어린이날, 석가탄신일 존재했음
				// 원천 데이터에
				List<Holiday> dbs = holidayRepository.findByCountryCodeAndHolidayYmd(dto.getCountryCode(),
						dto.getHolidayYmd());
				if (Objects.isNull(dbs) || dbs.isEmpty()) {
					// insert
					Holiday holiday = dto.toHolidayEntity();
					if (dto.getTypes() != null) {
						List<HolidayType> typeEntities = dto.getTypes().stream().map(type -> {
							HolidayType ht = new HolidayType();
							ht.setType(type);
							ht.setHoliday(holiday); // 연관관계 설정
							return ht;
						}).toList();
						holiday.setTypes(typeEntities);
					}
					if (dto.getCounties() != null) {
						List<HolidayCounty> countyEntities = dto.getCounties().stream().map(county -> {
							HolidayCounty hc = new HolidayCounty();
							hc.setCounty(county);
							hc.setHoliday(holiday);
							return hc;
						}).toList();
						holiday.setCounties(countyEntities);
					}
					holidayRepository.save(holiday);
					insertCount++;
				} else {
				    Optional<Holiday> exactMatch = dbs.stream()
				            .filter(data -> data .getCountryCode().equals(dto.getCountryCode())
				                    && data.getHolidayYmd().equals(dto.getHolidayYmd())
				                    && data.getHolidayName().equals(dto.getHolidayName()))
				            .findFirst();
				    boolean isUpdate = false;
				    Holiday newHoliday = dto.toHolidayEntity();
				    if (exactMatch.isPresent()) {
				        isUpdate = synchronizeHoliday(exactMatch.get(), newHoliday);
				        dto.setSyncRequired(true);
				    } else {
						for (Holiday db : dbs) { // 하나의 원천으로 2건 update X
							// 이미 반영된 dto 생략함
							if(dto.isSyncRequired()) continue;
							isUpdate = synchronizeHoliday(db, newHoliday);
							dto.setSyncRequired(true);
							if(isUpdate) break;
						}
				    }
					if (isUpdate) {
						updateCount++;
					}
				}
			}
			log.info("[refresh api called] insert : {} , update : {}", insertCount, updateCount);
		}
	}

	/**
	 * @param db : TARGET
	 * @param newHoliday : 원천 DB 정보
	 */
	private boolean synchronizeHoliday(Holiday db, Holiday newHoliday) {
		boolean isUpdate = false;
		if (!db.equals(newHoliday)) {
			BeanUtils.copyProperties(newHoliday, db, "holidaySn", "regDate", "types", "counties");
			db.setUpdateDate(LocalDateTime.now());
			isUpdate = true;
		}
		
		List<HolidayType> newTypes = newHoliday.getTypes();
		List<HolidayType> cleanedNewTypes = (newTypes == null ? Collections.emptyList() : 
			newTypes.stream().filter(Objects::nonNull).peek(ht -> ht.setHoliday(db)).toList());
		
		if (db.typesChanged(cleanedNewTypes)) {
			if (db.getTypes() == null)
				db.setTypes(new ArrayList<>());
			db.getTypes().clear();
			db.getTypes().addAll(cleanedNewTypes);
			isUpdate = true;
		}

		List<HolidayCounty> newCounty = newHoliday.getCounties();
		List<HolidayCounty> cleanedNewCounty = (newCounty == null ? Collections.emptyList()
				: newCounty.stream().filter(Objects::nonNull).peek(hc -> hc.setHoliday(db)).toList());
		
		if (db.countiesChanged(cleanedNewCounty)) {
			if (db.getCounties() == null)
				db.setCounties(new ArrayList<>());
			db.getCounties().clear();
			db.getCounties().addAll(cleanedNewCounty);
			isUpdate = true;
		}
		return isUpdate;
	}

	/**
	 * [countryCode, year] 기준 삭제
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public long deleteHolidays(String countryCode, Integer year) {
		List<Holiday> db = holidayRepository.findByCountryCodeAndYear(countryCode, year);
		for (Holiday holiday : db) {
			holiday.getTypes().clear();
			holiday.getCounties().clear();
		}
		holidayRepository.flush();
		return holidayRepository.deleteAllByCountryCodeAndYear(countryCode, year);
	}

	/**
	 * 최근 5년(2020~2025) 공휴일 수집 후, DB 적재 애플리케이션 시작과 함께 로드 어플리케이션 구동을 위한 최초 기초 데이터를
	 * 적재한다.
	 */
	public void loadDefaultData() {
		// 국가 정보 조회
		List<CountryDto> countryInfoList = countryService.fetchAvailableCountries();
		if (countryInfoList == null || countryInfoList.isEmpty()) {
			log.error("[api/v3/AvailableCountries] API 데이터 누락, 상세 API 확인 필요");
			throw new RuntimeException("국가정보 데이터가 존재하지 않아 기동 실패했습니다.");
		}

		List<Integer> baseYears = IntStream.rangeClosed(Year.now().getValue() - 5, Year.now().getValue()).boxed()
				.collect(Collectors.toList());
		// 실제 공휴일 정보 조회 + HolidayEntity 변환
		List<Holiday> holidayEntityList = countryInfoList.parallelStream().flatMap(country -> baseYears.stream()
				.flatMap(year -> fetchHolidays(year, country.getCountryCode()).stream()).map(dto -> {
					Holiday holiday = dto.toHolidayEntity();
					// types가 존재하면 HolidayType 엔티티 생성
					if (dto.getTypes() != null) {
						List<HolidayType> typeEntities = dto.getTypes().stream().map(type -> {
							HolidayType ht = new HolidayType();
							ht.setType(type);
							ht.setHoliday(holiday); // 연관관계 설정
							return ht;
						}).toList();
						holiday.setTypes(typeEntities);
					}

					if (dto.getCounties() != null) {
						List<HolidayCounty> countyEntities = dto.getCounties().stream().map(county -> {
							HolidayCounty hc = new HolidayCounty();
							hc.setCounty(county);
							hc.setHoliday(holiday);
							return hc;
						}).toList();
						holiday.setCounties(countyEntities);
					}
					return holiday;
				})).collect(Collectors.toList());
		if (holidayEntityList == null || holidayEntityList.isEmpty()) {
			log.warn("적재할 데이터가 없습니다.");
		} else {
			log.info("BASE DATA INSERT STATRED ");
			holidayRepository.saveAll(holidayEntityList);
			log.info("BASE DATA LOAD END [Start Application]");
		}
	}

	/**
	 * [외부 API] 공휴일 API에서 국가별 공휴일 정보 조회
	 * 
	 * @param year        년도
	 * @param countryCode 국가코드
	 * @return 외부 API에서 조회한 국가 목록
	 */
	private List<GlobalHolidayDto> fetchHolidays(Integer year, String countryCode) {
		try {
			return webClient.get().uri(
					uriBuilder -> uriBuilder.pathSegment("PublicHolidays", String.valueOf(year), countryCode).build())
					.retrieve().bodyToFlux(GlobalHolidayDto.class).collectList().block();
		} catch (WebClientResponseException e) {
			log.error("[WebClient 서버 에러] : (status {}): {}", e.getStatusCode(), e.getResponseBodyAsString(), e);
			throw new IllegalStateException("외부 공휴일 API 오류로 인해 서비스 기동 실패", e);
		} catch (WebClientRequestException e) {
			log.error("[WebClient 클라이언트 에러] : {}", e.getMessage());
			throw new IllegalStateException("외부 공휴일 API 연결 실패로 서비스 기동 불가", e);
		} catch (Exception e) {
			log.error("[fetchHolidays service error]: {}", e.getMessage(), e);
			throw e;
		}
	}
}
