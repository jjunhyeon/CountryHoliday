package io.github.jjunhyeon.java21_assignment.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import io.github.jjunhyeon.java21_assignment.batch.tasklet.GlobalHolidaySyncTasklet;
import lombok.RequiredArgsConstructor;

/**
* @packageName    : io.github.jjunhyeon.java21_assignment.batch.config
* @fileName       : GlobalHolidayJobConfig.java
* @author         : JunHyeon
* @date           : 2025.12.03
* @description    : 글로벌 공휴일 관리 Batch JobConfig
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.12.03        KimJunHyeon      최초 생성
*/
@Configuration
@RequiredArgsConstructor
public class GlobalHolidayJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    Step GlobalHolidaySyncStep(GlobalHolidaySyncTasklet tasklet) {
        return new StepBuilder("GlobalHolidaySyncStep", jobRepository)
                .tasklet(tasklet, transactionManager)
                .build();
    }

    @Bean
    Job holidaySyncJob(Step GlobalHolidaySyncStep) {
        return new JobBuilder("GlobalHolidaySyncJob", jobRepository)
                .start(GlobalHolidaySyncStep)
                .build();
    }
}
