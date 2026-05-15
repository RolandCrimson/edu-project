package com.lastcoder.advisor.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lastcoder.advisor.service.AdvisorChainService;
import com.lastcoder.advisor.service.SharedDataService;
import com.lastcoder.advisor.service.LoggerAdvisorService;
import com.lastcoder.advisor.service.SafeGuardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/springai")
@Slf4j
public class AdvisorController {

  private final AdvisorChainService advisorPriorityService;
  private final SharedDataService sharedDataService;
  private final LoggerAdvisorService loggerService;
  private final SafeGuardService safeGuardService;

  @PostMapping(path = "/advisor-chain", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
  public String advisorChain(@RequestParam String question) {
    String response = advisorPriorityService.advisorChain(question);
    return response;
  }

  @PostMapping(value = "/advisor-context", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
  public String advisorContext(@RequestParam String question) {
    String response = sharedDataService.advisorContext(question);
    return response;
  }

  @PostMapping(value = "/advisor-logging", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
  public String advisorLogging(@RequestParam String question) {
    String response = loggerService.advisorLogging(question);
    return response;
  }

  @PostMapping(value = "/advisor-safe-guard", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
  public String advisorSafeGuard(@RequestParam String question) {
    String response = safeGuardService.advisorSafeGuard(question);
    return response;
  }

}