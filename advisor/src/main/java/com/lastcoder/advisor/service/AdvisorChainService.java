package com.lastcoder.advisor.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;

import com.lastcoder.advisor.advisor.Advisor01;
import com.lastcoder.advisor.advisor.Advisor02;
import com.lastcoder.advisor.advisor.Advisor03;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdvisorChainService {
  private ChatClient chatClient;

  public AdvisorChainService(ChatClient.Builder chatClientBuilder) {
    this.chatClient = chatClientBuilder
        .defaultAdvisors(new Advisor01(), new Advisor02())
        .defaultOptions(ChatOptions.builder()
            .temperature(0.3)
            .model("gpt-4o-mini"))
        .build();
  }

  public String advisorChain(String question) {
    String response = chatClient.prompt()
        .advisors(new Advisor03())
        .user(question)
        .call()
        .content();
    return response;
  }
}
