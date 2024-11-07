package org.aston.application.controller;

import lombok.RequiredArgsConstructor;
import org.aston.application.dto.AuthorTo;
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
@RequestMapping("/authors/{id:\\d+}")
public class SpecificAuthorController {

    private final CrudService<AuthorTo> authorService;
    private final MessageSource messageSource;

    @ModelAttribute(Key.FOUND + Key.AUTHOR)
    public AuthorTo findAuthor(@PathVariable(Key.ID) long id) {
        return authorService.get(id)
                .orElseThrow(() -> new NoSuchElementException(Key.AUTHOR + " " + Key.NOT_FOUND));
    }

    @GetMapping
    public AuthorTo getAuthor(@ModelAttribute(Key.FOUND + Key.AUTHOR) AuthorTo authorTo) {
        return authorTo;
    }

    @PatchMapping
    public ResponseEntity<?> updateAuthor(@PathVariable(Key.ID) long id,
                                          @RequestBody AuthorTo authorTo,
                                          BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            authorService.update(id, authorTo);
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAuthor(@PathVariable(Key.ID) long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException exception,
                                                                      Locale locale) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, messageSource.getMessage(
                        exception.getMessage(), new Object[0], exception.getMessage(), locale
                ))
        );
    }

}
