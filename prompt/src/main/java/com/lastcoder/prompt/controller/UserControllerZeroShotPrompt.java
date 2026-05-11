package com.lastcoder.prompt.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lastcoder.prompt.service.ZeroShotPromptService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/springai")
@Slf4j
@RequiredArgsConstructor
public class UserControllerZeroShotPrompt {
    private final ZeroShotPromptService zeroShotService;

    @PostMapping(value = "/zero-shot-prompt", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String zeroShotPrompt(@RequestParam String review) {
        String reviewClass = zeroShotService.zeroShotPrompt(review);
        return reviewClass;
    }
}
