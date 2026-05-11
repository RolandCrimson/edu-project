package com.lastcoder.prompt.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class DefaultMethodService {

    private ChatClient chatClient;

    public DefaultMethodService(ChatClient.Builder chatClientBuilder) {
        chatClient = chatClientBuilder
                .defaultSystem("당신은 금융 전문가입니다.")
                .defaultOptions(ChatOptions.builder()
                        .temperature(0.3)
                        .model("gpt-4o-mini")
                        .maxTokens(300))
                .build();
    }

    // ##### 메소드 #####
    public Flux<String> defaultMethod(String question) {
        Flux<String> response = chatClient.prompt()
                .user(question)
                .stream()
                .content();
        return response;
    }
}
