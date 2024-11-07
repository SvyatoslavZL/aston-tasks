package org.aston.application.controller;

import lombok.RequiredArgsConstructor;
import org.aston.application.dto.BookTo;
import org.aston.application.service.CrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final CrudService<BookTo> bookService;

    @GetMapping
    public List<BookTo> getBooks() {
        return bookService.getAll();
    }

    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody BookTo bookTo,
                                     BindingResult bindingResult,
                                     UriComponentsBuilder uriBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            BookTo newBookTo = bookService.create(bookTo);
            return ResponseEntity
                    .created(uriBuilder.replacePath("/books/{id}").buildAndExpand(newBookTo.getId()).toUri())
                    .body(newBookTo);
        }
    }

}