package com.rashidi.transferservice.controller;

import static com.rashidi.transferservice.controller.DepositController.REQUEST_UID;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.rashidi.transferservice.exceptions.NotFoundException;
import com.rashidi.transferservice.controller.dto.ApiErrorResponse;
import com.rashidi.transferservice.metric.MetricsService;
import jakarta.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class RestExceptionHandler {

  private MetricsService metricsService;

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ApiErrorResponse> handleNotFoundException(ServletRequest request, NotFoundException e) {

    metricsService.incrementExceptionCounter("exception_type", "CustomerNotFoundException");
    return ResponseEntity.status(NOT_FOUND).body(ApiErrorResponse.builder()
        .requestUid(getRequestIdentifier(request))
        .errorCode(NOT_FOUND.value())
        .description(e.getMessage())
        .build());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiErrorResponse> handleRequestNotValidException(ServletRequest request, MethodArgumentNotValidException e) {

    List<String> errors = new ArrayList<>();
    e.getBindingResult().getFieldErrors()
        .forEach(error -> errors.add(error.getField() + ": " + error.getDefaultMessage()));
    e.getBindingResult().getGlobalErrors()
        .forEach(error -> errors.add(error.getObjectName() + ": " + error.getDefaultMessage()));

    String message = "Validation of request failed: %s".formatted(String.join(", ", errors));
    log.info(message);

    metricsService.incrementExceptionCounter("exception_type", "ValidationException");
    return ResponseEntity.status(BAD_REQUEST).body(ApiErrorResponse.builder()
        .requestUid(getRequestIdentifier(request))
        .errorCode(BAD_REQUEST.value())
        .description(message)
        .build());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiErrorResponse> handleUnknownException(ServletRequest request, Exception e) {

    metricsService.incrementExceptionCounter("exception_type", "ServerErrorException");
    return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiErrorResponse.builder()
        .requestUid(getRequestIdentifier(request))
        .errorCode(INTERNAL_SERVER_ERROR.value())
        .description(e.getMessage())
        .build());
  }

  private static String getRequestIdentifier(ServletRequest request) {
    return (String) request.getAttribute(REQUEST_UID);
  }
}
