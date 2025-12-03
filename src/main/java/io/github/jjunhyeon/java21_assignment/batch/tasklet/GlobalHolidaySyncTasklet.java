package io.github.jjunhyeon.java21_assignment.batch.tasklet;

import java.time.Year;
import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Configuration;

import io.github.jjunhyeon.java21_assignment.api.dto.CountryDto;
import io.github.jjunhyeon.java21_assignment.domain.service.CountryService;
import io.github.jjunhyeon.java21_assignment.domain.service.GlobalHolidayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
* @packageName    : io.github.jjunhyeon.java21_assignment.batch.tasklet
* @fileName       : HolidaySyncTasklet.java
* @author         : JunHyeon
* @date           : 2025.12.01
* @description    : 글로벌 공휴일 관리 Batch Tasklet
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.12.01        KimJunHyeon      최초 생성
*/
@Configuration
@RequiredArgsConstructor
@Slf4j
public class GlobalHolidaySyncTasklet implements Tasklet {

	private final CountryService countryService;
	private final GlobalHolidayService globalholidayService;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		List<CountryDto> countryList =  countryService.fetchAvailableCountries();
		countryList.forEach(list -> {
			log.info("전년도·금년도 데이터를 자동 동기화 TASKLET STARTED");
			globalholidayService.refreshHolidays(list.getCountryCode(), Year.now().getValue());
			globalholidayService.refreshHolidays(list.getCountryCode(), Year.now().getValue() - 1);
			log.info("전년도·금년도 데이터를 자동 동기화 TASKLET COMPLETED");
		});
		return RepeatStatus.FINISHED;
	}
}
