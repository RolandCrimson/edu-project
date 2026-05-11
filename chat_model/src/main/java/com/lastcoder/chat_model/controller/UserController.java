package com.lastcoder.chat_model.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lastcoder.chat_model.service.ChatClientService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/springai")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    // private final ChatModelService chatService;

    private final ChatClientService chatService;

    // ##### 요청 매핑 메소드 #####
    @PostMapping(value = "/chat-text", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String chatModel(@RequestParam String question) {
        String answerText = chatService.generateText(question);
        return answerText;
    }

    @PostMapping(value = "/chat-stream", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_NDJSON_VALUE
    // 라인으로 구분된 청크 텍스트 newline delimited JSON
    )
    public Flux<String> chatModelStream(@RequestParam String question) {
        Flux<String> answerStreamText = chatService.generateStreamText(question);
        return answerStreamText;
    }
}
