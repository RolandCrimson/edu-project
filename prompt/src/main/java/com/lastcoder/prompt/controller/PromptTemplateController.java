package com.lastcoder.prompt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lastcoder.prompt.service.PromptTemplateService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/springai")
@Slf4j
public class PromptTemplateController {
    // ##### 필드 #####
    @Autowired
    private PromptTemplateService promptService;

    // ##### 요청 매핑 메소드 #####
    @PostMapping(value = "/prompt-template", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<String> promptTemplate(@RequestParam String statement, @RequestParam String language) {
        Flux<String> response = promptService.promptTemplate1(statement, language);
        // Flux<String> response = promptService.promptTemplate2(statement, language);
        // Flux<String> response = promptService.promptTemplate3(statement, language);
        return response;
    }
}
