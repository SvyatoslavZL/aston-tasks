package org.aston.application.controller_advices;

import lombok.RequiredArgsConstructor;
import org.aston.application.util.Constants;
import org.aston.application.util.Key;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@ControllerAdvice
@RequiredArgsConstructor
public class BadRequestControllerAdvice {

    private final MessageSource messageSource;

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ProblemDetail> handledBindException(BindException exception, Locale locale) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                this.messageSource.getMessage(
                        Constants.ERROR_400_TITLE, new Object[0],
                        Constants.ERROR_400_TITLE, locale
                ));

        problemDetail.setProperty(Key.ERRORS, exception.getAllErrors().stream()
                .map(MessageSourceResolvable::getDefaultMessage));

        return ResponseEntity.badRequest().body(problemDetail);
    }
}
