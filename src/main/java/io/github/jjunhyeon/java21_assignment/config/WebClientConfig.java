package io.github.jjunhyeon.java21_assignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
* @packageName    : io.github.jjunhyeon.java21_assignment.config
* @fileName       : WebClientConfig.java
* @author         : JunHyeon
* @date           : 2025.11.28
* @description    : - 외부 REST API 호출을 위한 WebClient 설정 클래스
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.11.28        KimJunHyeon      최초 생성
*/
@Configuration
public class WebClientConfig {
	
	/**
	 * WebClient.Builder Bean 생성
	 * 외부에서 Bean을 재사용하기 위함
	 * @return WebClient.Builder 
	 */
	@Bean
	public WebClient.Builder webClientBuilder() {
		return WebClient.builder();
	}
}
