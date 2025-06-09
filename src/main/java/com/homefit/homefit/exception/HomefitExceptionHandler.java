package com.homefit.homefit.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import jakarta.validation.ConstraintDeclarationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class HomefitExceptionHandler {
    @ExceptionHandler(HomefitException.class)
    ProblemDetail handleHomefitException(HomefitException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(e.getHttpStatus());
        problemDetail.setTitle(e.getTitle());
        problemDetail.setDetail(e.getDetail());

        if (e.getDetail() == null || e.getDetail().isBlank()) {
            log.error("HomefitException 발생: {}", e.getTitle());
        } else {
            log.error("HomefitException 발생: {} - {}", e.getTitle(), e.getDetail());
        }

        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String detailMessage = e.getBindingResult().getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("올바르지 않은 요청입니다");
        problemDetail.setDetail(detailMessage);

        log.error("MethodArgumentNotValidException 발생: {}", detailMessage);

        return problemDetail;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ProblemDetail handleConstraintViolationException(ConstraintViolationException e) {
        String detailMessage = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("올바르지 않은 요청입니다");
        problemDetail.setDetail(detailMessage);

        log.error("ConstraintViolationException 발생: {}", detailMessage);

        return problemDetail;
    }

    @ExceptionHandler(ConstraintDeclarationException.class)
    ProblemDetail handleConstraintDeclarationException(ConstraintDeclarationException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("올바르지 않은 요청입니다");
        problemDetail.setDetail(e.getMessage());

        log.error("ConstraintDeclarationException 발생: {}", e.getMessage());

        return problemDetail;
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    ProblemDetail handleHandlerMethodValidationException(HandlerMethodValidationException e) {
        String detailMessage = e.getParameterValidationResults().stream()
                .flatMap(result -> result.getResolvableErrors().stream())
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("올바르지 않은 요청입니다");
        problemDetail.setDetail(detailMessage);

        log.error("HandlerMethodValidationException 발생: {}", detailMessage);

        return problemDetail;
    }

    @ExceptionHandler(MissingRequestValueException.class)
    ProblemDetail handleMissingRequestValueException(MissingRequestValueException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("올바르지 않은 요청입니다");
        problemDetail.setDetail("파라미터, 쿠키, 헤더 등의 요청에 필수 값이 누락되었습니다");

        log.error("MissingRequestValueException 발생", e);

        return problemDetail;
    }

    @ExceptionHandler(AccessDeniedException.class)
    ProblemDetail handleAccessDeniedException(AccessDeniedException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        problemDetail.setTitle("권한이 없습니다.");

        log.error("AccessDeniedException 발생", e);

        return problemDetail;
    }

    @ExceptionHandler(RuntimeException.class)
    ProblemDetail handleRuntimeException(RuntimeException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("알 수 없는 오류가 발생하였습니다");

        log.error("RuntimeException 발생", e);

        return problemDetail;
    }
}
