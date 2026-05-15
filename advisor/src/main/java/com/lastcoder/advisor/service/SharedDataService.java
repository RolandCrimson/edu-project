package com.lastcoder.advisor.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import com.lastcoder.advisor.advisor.LanguageAdvisor;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SharedDataService {
  private ChatClient chatClient;

  public SharedDataService(ChatClient.Builder chatClientBuilder) {
    this.chatClient = chatClientBuilder
        .defaultAdvisors(new LanguageAdvisor(Ordered.HIGHEST_PRECEDENCE))
        .defaultOptions(ChatOptions.builder()
            .temperature(0.3)
            .model("gpt-4o-mini"))
        .build();
  }

  public String advisorContext(String question) {
    String response = chatClient.prompt()
        .advisors(spec -> spec.param(LanguageAdvisor.LANGUAGE_OPTION, "영어"))
        .user(question)
        .call()
        .content();
    return response;
  }
}
