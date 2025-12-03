package io.github.jjunhyeon.java21_assignment.batch.scheduler;

import java.time.LocalDate;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
* @packageName    : io.github.jjunhyeon.java21_assignment.batch.scheduler
* @fileName       : BatchScheduler.java
* @author         : JunHyeon
* @date           : 2025.12.03
* @description    :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.12.03        KimJunHyeon      최초 생성
*/
@Component
@EnableScheduling
@RequiredArgsConstructor
public class BatchScheduler {
    private final JobLauncher jobLauncher;
    private final Job GlobalHolidaySyncJob;

    /**
    * 매년 1 월 2 일 01:00 KST 에 전년도·금년도 데이터를 자동 동기화 BATCH 
    */
    @Scheduled(cron = "0 0 1 2 1 *", zone = "Asia/Seoul")
    public void runGlobalHolidaySyncJob() throws Exception {
        jobLauncher.run(GlobalHolidaySyncJob, new JobParametersBuilder()
                .addLocalDate("baseDate", LocalDate.now())
                .toJobParameters());
    }
}
