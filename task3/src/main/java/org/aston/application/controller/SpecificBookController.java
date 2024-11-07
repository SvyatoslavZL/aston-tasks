package org.aston.application.controller;

import lombok.RequiredArgsConstructor;
import org.aston.application.dto.BookTo;
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
@RequestMapping("/books/{id:\\d+}")
public class SpecificBookController {

    private final CrudService<BookTo> bookService;
    private final MessageSource messageSource;

    @ModelAttribute(Key.FOUND + Key.BOOK)
    public BookTo findBook(@PathVariable(Key.ID) long id) {
        return bookService.get(id)
                .orElseThrow(() -> new NoSuchElementException(Key.BOOK + " " + Key.NOT_FOUND));
    }

    @GetMapping
    public BookTo getBook(@ModelAttribute(Key.FOUND + Key.BOOK) BookTo bookTo) {
        return bookTo;
    }

    @PatchMapping
    public ResponseEntity<?> updateBook(@PathVariable(Key.ID) long id,
                                        @RequestBody BookTo bookTo,
                                        BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            bookService.update(id, bookTo);
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBook(@PathVariable(Key.ID) long id) {
        bookService.delete(id);
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
