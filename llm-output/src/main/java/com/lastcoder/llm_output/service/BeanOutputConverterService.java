package com.lastcoder.llm_output.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;

import com.lastcoder.llm_output.dto.Restaurant;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BeanOutputConverterService {
  private ChatClient chatClient;

  public BeanOutputConverterService(ChatClient.Builder chatClientBuilder) {
    chatClient = chatClientBuilder
        .defaultOptions(ChatOptions.builder()
            .temperature(0.3)
            .model("gpt-4o-mini"))
        .build();
  }

  public Restaurant beanOutputConverter(String city) {
    Restaurant restaurant = chatClient.prompt()
        .user("%s에서 유명한 식당 목록 5개를 출력하세요.".formatted(city))
        .call()
        .entity(Restaurant.class);
    return restaurant;
  }
}
