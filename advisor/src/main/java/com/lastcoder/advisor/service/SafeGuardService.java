package com.lastcoder.advisor.service;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SafeGuardService {
  private ChatClient chatClient;

  public SafeGuardService(ChatClient.Builder chatClientBuilder) {
    SafeGuardAdvisor safeGuardAdvisor = new SafeGuardAdvisor(
        List.of("병신", "계좌번호", "폭력", "폭탄", "마약"),
        "해당 질문은 민감한 콘텐츠 요청이므로 응답할 수 없습니다.",
        Ordered.HIGHEST_PRECEDENCE);

    this.chatClient = chatClientBuilder
        .defaultAdvisors(safeGuardAdvisor)
        .defaultOptions(ChatOptions.builder()
            .temperature(0.3)
            .model("gpt-4o-mini"))
        .build();
  }

  public String advisorSafeGuard(String question) {
    String response = chatClient.prompt()
        .user(question)
        .call()
        .content();
    return response;
  }
}
