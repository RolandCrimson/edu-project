package com.lastcoder.prompt.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lastcoder.prompt.service.StepBackPromptService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/springai")
@Slf4j
@RequiredArgsConstructor
public class StepBackPromptController {
  private final StepBackPromptService stepService;

  @PostMapping(value = "/step-back-prompt", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
  public String stepBackPrompt(@RequestParam String question) throws Exception {
    String answer = stepService.stepBackPrompt(question);
    return answer;
  }
}
