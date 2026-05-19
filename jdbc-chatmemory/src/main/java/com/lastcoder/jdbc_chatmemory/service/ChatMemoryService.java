package com.lastcoder.jdbc_chatmemory.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ChatMemoryService {
  private ChatClient chatClient;

  public ChatMemoryService(JdbcChatMemoryRepository chatMemoryRepository, ChatClient.Builder chatClientBuilder) {
    ChatMemory chatMemory = MessageWindowChatMemory.builder()
        .chatMemoryRepository(chatMemoryRepository)
        .maxMessages(20)
        .build();

    this.chatClient = chatClientBuilder
        .defaultAdvisors(
            MessageChatMemoryAdvisor.builder(chatMemory).build(),
            new SimpleLoggerAdvisor(Ordered.LOWEST_PRECEDENCE - 1))
        .build();
  }

  public String chat(String userText, String conversationId) {
    String answer = chatClient.prompt()
        .user(userText)
        .advisors(advisorSpec -> advisorSpec.param(
            ChatMemory.CONVERSATION_ID, conversationId))
        .call()
        .content();
    return answer;
  }
}
