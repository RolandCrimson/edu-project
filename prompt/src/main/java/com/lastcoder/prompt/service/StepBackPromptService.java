package com.lastcoder.prompt.service;

import java.util.List;
import java.util.Objects;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;

import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Slf4j
public class StepBackPromptService {
  private ChatClient chatClient;

  public StepBackPromptService(ChatClient.Builder chatClientBuilder) {
    chatClient = chatClientBuilder
        .defaultOptions(ChatOptions.builder()
            .temperature(0.3)
            .model("gpt-4o-mini"))
        .build();
  }

  public String stepBackPrompt(String question) throws Exception {
    String questions = chatClient.prompt()
        .user("""
            사용자 질문을 처리할 때 Step-Back 프롬프트 기법을 사용합니다.
            사용자 질문을 단계별 질문들로 재구성해주세요.
            맨 마지막 질문은 사용자 질문과 일치해야 합니다.
            단계별 질문을 항목으로 하는 JSON 배열로 출력해 주세요.
            예시: ["...", "...", "...", "..."]
            사용자 질문: %s
            """.formatted(question))
        .call()
        .content();

    String json = questions.substring(questions.indexOf("["), questions.indexOf("]") + 1);
    log.info(json);

    ObjectMapper objectMapper = new ObjectMapper();
    List<String> questionlList = objectMapper.readValue(json, new TypeReference<List<String>>() {
    });

    String[] answers = new String[questionlList.size()];
    for (int i = 0; i < questionlList.size(); i++) {
      String questionItem = questionlList.get(i);
      String stepAnswer = getAnswer(questionItem, answers);
      answers[i] = stepAnswer;
      log.info("단계{} 질문: {}, 답변: {}", i + 1, questionItem, stepAnswer);
    }

    return answers[answers.length - 1];
  }

  public String getAnswer(String question, String... prevAnswers) {
    String context = "";
    for (String a : prevAnswers) {
      context += Objects.requireNonNullElse(a, "");
    }
    String answer = chatClient.prompt()
        .user("""
            %s
            문맥: %s
            """.formatted(question, context))
        .call()
        .content();
    return answer;
  }
}
