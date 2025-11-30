package io.github.jjunhyeon.java21_assignment;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.github.jjunhyeon.java21_assignment.domain.service.GlobalHolidayService;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaAuditing
public class BackendAssignmentApplication implements CommandLineRunner {

	private final GlobalHolidayService globalHolidayService;
	
	public static void main(String[] args) {
		SpringApplication.run(BackendAssignmentApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		globalHolidayService.loadDefaultData();
	}
}
