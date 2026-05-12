package com.lastcoder.prompt.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lastcoder.prompt.service.DefaultMethodService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/springai")
@Slf4j
@RequiredArgsConstructor
public class DefaultMethodController {

    private final DefaultMethodService defaultService;

    // ##### 메소드 #####
    @PostMapping(value = "/default-method", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<String> defaultMethod(@RequestParam String question) {
        Flux<String> response = defaultService.defaultMethod(question);
        return response;
    }
}
