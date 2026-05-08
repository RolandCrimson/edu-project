package com.adacho.chat_model.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adacho.chat_model.service.AiService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/springai")
@Slf4j
public class AiController {
  private final AiService aiService;

  public AiController(AiService aiService) {
    this.aiService = aiService;
  }

  // private final AiServiceByChatClient aiService;

  // public AiController(AiServiceByChatClient aiService) {
  // this.aiService = aiService;
  // }

  // ##### 요청 매핑 메소드 #####
  @PostMapping(value = "/chat-string", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
  public String chatModel(@RequestParam String question) {
    String answerText = aiService.generateText(question);
    return answerText;
  }

  @PostMapping(value = "/chat-stream", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_NDJSON_VALUE
  // 라인으로 구분된 청크 텍스트 newline delimited JSON
  )
  public Flux<String> chatModelStream(@RequestParam String question) {
    Flux<String> answerStreamText = aiService.generateStreamText(question);
    return answerStreamText;
  }
}
