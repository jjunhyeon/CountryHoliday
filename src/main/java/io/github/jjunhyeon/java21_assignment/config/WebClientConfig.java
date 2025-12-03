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
	 * 국가목록 API 
	 */
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://date.nager.at/api/v3")
                .build();
    }
    
}
