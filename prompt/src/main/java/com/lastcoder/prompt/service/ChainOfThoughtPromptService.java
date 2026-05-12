package com.lastcoder.prompt.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class ChainOfThoughtPromptService {
  private ChatClient chatClient;

  // ##### 생성자 #####
  public ChainOfThoughtPromptService(ChatClient.Builder chatClientBuilder) {
    chatClient = chatClientBuilder
        .defaultOptions(ChatOptions.builder()
            .temperature(0.5)
            .model("gpt-4o-mini"))
        .build();
  }

  public Flux<String> chainOfThought(String question) {
    Flux<String> answer = chatClient.prompt()
        .user("""
            %s
            단계별로 생각해 봅시다.

            [예시]
            질문: 영희가 시장에서 1,500원짜리 사과 3개와 2,000원짜리 배 1개를 샀습니다.
            만 원짜리 지폐를 냈다면, 거스름돈은 얼마일까요?

            답변: 사과 3개의 가격은 1,500원 * 3 = 4,500원입니다.
            배 1개의 가격은 2,000원입니다.
            총 구매 금액은 4,500원 + 2,000원 = 6,500원입니다.
            만 원짜리 지폐를 냈으므로, 거스름돈은 10,000원 - 6,500원 = 3,500원입니다. 정답은 3,500원입니다.
            """.formatted(question))
        .stream()
        .content();
    return answer;
  }
}
