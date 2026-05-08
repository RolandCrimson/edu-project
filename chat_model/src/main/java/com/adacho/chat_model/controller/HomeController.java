package com.adacho.chat_model.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
  @GetMapping("/")
  public String home() {
    return "home";
  }

  @GetMapping("/chat-string")
  public String chatModel() {
    return "chat-string";
  }

  @GetMapping("/chat-stream")
  public String chatModelStream() {
    return "chat-stream";
  }
}
