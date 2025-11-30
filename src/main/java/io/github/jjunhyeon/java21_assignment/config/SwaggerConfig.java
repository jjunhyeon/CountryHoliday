package io.github.jjunhyeon.java21_assignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

/**
* @packageName    : io.github.jjunhyeon.java21_assignment.config
* @fileName       : SwaggerConfig.java
* @author         : JunHyeon
* @date           : 2025.11.30
* @description    :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.11.30        KimJunHyeon      최초 생성
*/
@Configuration
public class SwaggerConfig {
	
    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Global Holiday API")
                        .version("1.0")
                        .description("백엔드 개발자 과제 API"));
    }
}
