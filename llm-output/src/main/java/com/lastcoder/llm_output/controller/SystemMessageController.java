package com.lastcoder.llm_output.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lastcoder.llm_output.dto.ReviewClassification;
import com.lastcoder.llm_output.service.SystemMessageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/springai")
@Slf4j
@RequiredArgsConstructor
public class SystemMessageController {
  private final SystemMessageService aiService;

  @PostMapping(value = "/system-message", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ReviewClassification beanOutputConverter(@RequestParam String review) {
    ReviewClassification reviewClassification = aiService.classifyReview(review);
    return reviewClassification;
  }
}
