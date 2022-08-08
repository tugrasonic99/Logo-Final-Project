package com.traveladminapp.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception exception) {
		ErrorResponse response = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
		log.info("Exception Occured :: response: {}", response.toString());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(QueryEmptyException.class)
	public ResponseEntity<ErrorResponse> handleException(QueryEmptyException exception) {
		ErrorResponse response = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
		log.info("Exception Occured :: response: {}", response.toString());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

	}

}
