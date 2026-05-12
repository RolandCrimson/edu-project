package com.lastcoder.prompt.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lastcoder.prompt.service.FewShotPromptService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/springai")
@Slf4j
@RequiredArgsConstructor
public class FewShotPromptController {
    private final FewShotPromptService fewShotService;

    @PostMapping(value = "/few-shot-prompt", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String fewShotPrompt(@RequestParam String order) {
        String json = fewShotService.fewShotPrompt(order);
        return json;
    }
}
