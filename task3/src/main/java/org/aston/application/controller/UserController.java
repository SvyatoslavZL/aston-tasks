package org.aston.application.controller;

import lombok.RequiredArgsConstructor;
import org.aston.application.dto.UserTo;
import org.aston.application.service.CrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final CrudService<UserTo> userService;

    @GetMapping
    public List<UserTo> getUsers() {
        return userService.getAll();
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserTo userTo,
                                        BindingResult bindingResult,
                                        UriComponentsBuilder uriBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            UserTo newUserTo = userService.create(userTo);
            return ResponseEntity
                    .created(uriBuilder.replacePath("/users/{id}").buildAndExpand(newUserTo.getId()).toUri())
                    .body(newUserTo);
        }
    }

}
