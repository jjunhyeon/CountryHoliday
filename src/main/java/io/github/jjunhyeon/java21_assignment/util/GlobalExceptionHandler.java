package io.github.jjunhyeon.java21_assignment.util;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.validation.ValidationException;

/**
* @packageName    : io.github.jjunhyeon.java21_assignment.util
* @fileName       : GlobalExceptionHandler.java
* @author         : JunHyeon
* @date           : 2025.12.03
* @description    : rest api 정상 예외 응답을 위한 GlobalExceptionHandler
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.12.03        KimJunHyeon      최초 생성
*/
@RestControllerAdvice(annotations = {RestController.class}, basePackages = {"io.github.jjunhyeon.java21_assignment.api.controller"})
public class GlobalExceptionHandler {
	@ExceptionHandler(ValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, Object> handleConstraintViolation(ValidationException ex) {
		return Map.of("status", HttpStatus.BAD_REQUEST.value(), "message", "Validation Failed", "detail",
				ex.getMessage()
		);
	}
}
