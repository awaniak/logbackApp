package io.github.awaniak.logbackApp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class BasicController {

    @GetMapping("books")
    public String getBooks(HttpServletRequest request) {
        log.info("Getting books");
        return "Harry Potter";
    }
}
