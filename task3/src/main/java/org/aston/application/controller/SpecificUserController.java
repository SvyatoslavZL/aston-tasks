package org.aston.application.controller;

import lombok.RequiredArgsConstructor;
import org.aston.application.dto.UserTo;
import org.aston.application.service.CrudService;
import org.aston.application.util.Key;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{id:\\d+}")
public class SpecificUserController {

    private final CrudService<UserTo> userService;
    private final MessageSource messageSource;

    @ModelAttribute(Key.FOUND + Key.USER)
    public UserTo findUser(@PathVariable(Key.ID) long id) {
        return userService.get(id)
                .orElseThrow(() -> new NoSuchElementException(Key.USER + " " + Key.NOT_FOUND));
    }

    @GetMapping
    public UserTo getUser(@ModelAttribute(Key.FOUND + Key.USER) UserTo userTo) {
        return userTo;
    }

    @PatchMapping
    public ResponseEntity<?> updateUser(@PathVariable(Key.ID) long id,
                                        @RequestBody UserTo userTo,
                                        BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            userService.update(id, userTo);
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@PathVariable(Key.ID) long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException exception,
                                                                      Locale locale) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, this.messageSource.getMessage(
                        exception.getMessage(), new Object[0], exception.getMessage(), locale
                ))
        );
    }

}
