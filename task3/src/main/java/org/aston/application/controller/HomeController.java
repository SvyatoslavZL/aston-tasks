package org.aston.application.controller;

import org.aston.application.util.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return Constants.HOME_MESSAGE;
    }

}
