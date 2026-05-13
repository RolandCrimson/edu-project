package com.lastcoder.llm_output.service;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MapOutputConverterService {
  private ChatClient chatClient;

  public MapOutputConverterService(ChatClient.Builder chatClientBuilder) {
    chatClient = chatClientBuilder
        .defaultOptions(ChatOptions.builder()
            .temperature(0.3)
            .model("gpt-4o-mini"))
        .build();
  }

  public Map<String, Object> mapOutputConverter(String hotel) {
    Map<String, Object> hotelInfo = chatClient.prompt()
        .user("%s에 대해 정보를 알려주세요".formatted(hotel))
        .call()
        .entity(new MapOutputConverter());
    return hotelInfo;
  }

}
