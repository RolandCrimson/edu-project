package com.lastcoder.llm_output.service;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.lastcoder.llm_output.dto.Restaurant;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ParameterizedTypeReferenceService {
  private ChatClient chatClient;

  public ParameterizedTypeReferenceService(ChatClient.Builder chatClientBuilder) {
    chatClient = chatClientBuilder
        .defaultOptions(ChatOptions.builder()
            .temperature(0.3)
            .model("gpt-4o-mini"))
        .build();
  }

  public List<Restaurant> BeanOutputConverter(String cities) {
    List<Restaurant> hotelList = chatClient.prompt().user("""
        다음 도시들에서 유명한 식당 3개를 출력하세요.
        %s
        """.formatted(cities))
        .call()
        .entity(new ParameterizedTypeReference<List<Restaurant>>() {
        });
    return hotelList;
  }
}
