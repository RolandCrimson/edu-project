package com.lastcoder.jdbc_chatmemory.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lastcoder.jdbc_chatmemory.service.ChatMemoryService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/springai")
@Slf4j
@RequiredArgsConstructor
public class ChatMemoryController {
  private final ChatMemoryService chatMemoryServiceService;

  @PostMapping(value = "/chat", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
  public String jdbcChatMemory(@RequestParam String question, HttpSession session) {
    String answer = chatMemoryServiceService.chat(question, session.getId());
    return answer;
  }
}
