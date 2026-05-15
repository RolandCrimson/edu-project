package com.lastcoder.advisor.advisor;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LanguageAdvisor implements CallAdvisor {
  public static final String LANGUAGE_OPTION = "language";
  private String language = "한국어";
  private int order;

  public LanguageAdvisor(int order) {
    this.order = order;
  }

  @Override
  public String getName() {
    return this.getClass().getSimpleName();
  }

  @Override
  public int getOrder() {
    return this.order;
  }

  @Override
  public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {
    ChatClientRequest mutatedRequest = augmentPrompt(request);
    ChatClientResponse response = chain.nextCall(mutatedRequest);
    // 응답 반환
    return response;
  }

  // 사용자 메시지 강화
  private ChatClientRequest augmentPrompt(ChatClientRequest request) {
    // 추가할 사용자 텍스트 얻기
    String userText = this.language + "로 답변해주세요.";
    String selectedLanguage = (String) request.context().get(LANGUAGE_OPTION);
    if (selectedLanguage != null) {
      userText = selectedLanguage + "로 답변해주세요.";
    }
    String finalUserText = userText;

    Prompt originalPrompt = request.prompt();
    Prompt augmentedPrompt = originalPrompt.augmentUserMessage(
        userMessage -> UserMessage.builder()
            .text(userMessage.getText() + " " + finalUserText)
            .build());

    ChatClientRequest mutatedRequest = request.mutate()
        .prompt(augmentedPrompt)
        .build();
    return mutatedRequest;
  }
}