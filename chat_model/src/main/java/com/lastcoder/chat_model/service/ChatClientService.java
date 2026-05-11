package com.lastcoder.chat_model.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class ChatClientService {
    private final ChatClient chatClient;

    // ##### 생성자 #####
    public ChatClientService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();

    }

    // ##### 메소드 #####
    public String generateText(String question) {
        String answer = chatClient.prompt()
                .system("사용자 질문에 대해 한국어로 답변을 해야 합니다.")
                .user(question)
                .options(ChatOptions.builder()
                        .model("gpt-4o-mini")
                        .temperature(0.3)
                        .maxTokens(1000))
                .call()
                .content();

        return answer;
    }

    public Flux<String> generateStreamText(String question) {
        Flux<String> fluxString = chatClient.prompt()
                .system("사용자 질문에 대해 한국어로 답변을 해야 합니다.")
                .user(question)
                .options(ChatOptions.builder()
                        .model("gpt-4o-mini")
                        .temperature(0.3)
                        .maxTokens(1000))
                .stream()
                .content();

        return fluxString;
    }
}
