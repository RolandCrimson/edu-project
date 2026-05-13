package com.lastcoder.llm_output.service;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ListOutputConverterService {
  private ChatClient chatClient;

  public ListOutputConverterService(ChatClient.Builder chatClientBuilder) {
    chatClient = chatClientBuilder
        .defaultOptions(ChatOptions.builder()
            .temperature(0.3)
            .model("gpt-4o-mini"))
        .build();
  }

  public List<String> listOutputConverter(String city) {
    List<String> hotelList = chatClient.prompt()
        .user("%s에서 유명한 식당 목록 5개를 출력하세요.".formatted(city))
        .call()
        .entity(new ListOutputConverter());
    return hotelList;
  }
}
