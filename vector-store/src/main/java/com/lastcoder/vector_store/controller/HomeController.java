package com.lastcoder.vector_store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
  @GetMapping("/")
  public String home() {
    return "home";
  }

  @GetMapping("/text-embedding")
  public String textEmbedding() {
    return "text-embedding";
  }

  @GetMapping("/add-document")
  public String addDocument() {
    return "add-document";
  }

  @GetMapping("/search-document-text")
  public String searchDocumentText() {
    return "search-document-text";
  }

  @GetMapping("/search-document-sr")
  public String searchDocumentSR() {
    return "search-document-sr";
  }

  @GetMapping("/delete-document")
  public String deleteDocument() {
    return "delete-document";
  }

  @GetMapping("/image-embedding")
  public String faceRecognition() {
    return "image-embedding";
  }
}
