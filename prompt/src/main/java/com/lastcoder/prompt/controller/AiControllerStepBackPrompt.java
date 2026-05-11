package com.lastcoder.prompt.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lastcoder.prompt.service.AiServiceStepBackPrompt;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/ai")
@Slf4j
public class AiControllerStepBackPrompt {
    // ##### 필드 #####
    private AiServiceStepBackPrompt aiService;

    // ##### 생성자 #####
    public AiControllerStepBackPrompt(AiServiceStepBackPrompt aiService) {
        this.aiService = aiService;
    }

    @PostMapping(value = "/step-back-prompt", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String stepBackPrompt(@RequestParam("question") String question) throws Exception {
        String answer = aiService.stepBackPrompt(question);
        return answer;
    }
}
