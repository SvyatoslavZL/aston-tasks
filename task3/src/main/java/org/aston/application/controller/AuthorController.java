package org.aston.application.controller;

import lombok.RequiredArgsConstructor;
import org.aston.application.dto.AuthorTo;
import org.aston.application.service.CrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {

    private final CrudService<AuthorTo> authorService;

    @GetMapping
    public List<AuthorTo> getAuthors() {
        return authorService.getAll();
    }

    @PostMapping
    public ResponseEntity<?> createAuthor(@RequestBody AuthorTo authorTo,
                                          BindingResult bindingResult,
                                          UriComponentsBuilder uriBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            AuthorTo newAuthorTo = authorService.create(authorTo);
            return ResponseEntity
                    .created(uriBuilder.replacePath("/authors/{id}").buildAndExpand(newAuthorTo.getId()).toUri())
                    .body(newAuthorTo);
        }
    }

}
