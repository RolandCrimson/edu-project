package com.lastcoder.llm_image.service;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.ai.image.ImageMessage;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class ImageService {
  private ChatClient chatClient;

  private ImageModel imageModel; // OpenAiImageModel 자동 주입

  public ImageService(ChatClient.Builder chatClientBuilder, ImageModel imageModel) {
    chatClient = chatClientBuilder
        .defaultOptions(ChatOptions.builder()
            .temperature(0.3)
            .model("gpt-4o-mini"))
        .build();
    this.imageModel = imageModel;
  }

  public Flux<String> imageAnalysis(String question, String contentType, byte[] bytes) {
    // 시스템 메시지 생성
    SystemMessage systemMessage = SystemMessage.builder()
        .text("""
              당신은 이미지 분석가입니다.
              사용자 질문에 맞게 이미지를 분석하고 답변을 한국어로 하세요.
            """)
        .build();

    // 미디어 생성
    Media media = Media.builder()
        .mimeType(MimeType.valueOf(contentType))
        .data(new ByteArrayResource(bytes))
        .build();

    // 사용자 메시지 생성
    UserMessage userMessage = UserMessage.builder()
        .text(question)
        .media(media)
        .build();

    // 프롬프트 생성
    Prompt prompt = Prompt.builder()
        .messages(systemMessage, userMessage)
        .build();

    // LLM에 요청하고, 응답받기
    Flux<String> flux = chatClient.prompt(prompt)
        .stream()
        .content();
    return flux;
  }

  private String koToEn(String text) {
    String question = """
          당신은 번역가입니다. 아래 한글 문장을 영어 문장으로 번역해주세요.
          %s
        """.formatted(text);

    UserMessage userMessage = UserMessage.builder()
        .text(question)
        .build();

    Prompt prompt = Prompt.builder()
        .messages(userMessage)
        .build();

    String englishDescription = chatClient.prompt(prompt).call().content();
    return englishDescription;
  }

  public String generateImage(String description) {
    // 한글 => 영어 번역
    String englishDescription = koToEn(description);

    ImageMessage imageMessage = new ImageMessage(englishDescription);

    OpenAiImageOptions imageOptions = OpenAiImageOptions.builder()
        .model("gpt-image-1")
        .quality("low")
        .width(1536)
        .height(1024)
        .N(1)
        .build();

    // 프롬프트 생성
    List<ImageMessage> imageMessageList = List.of(imageMessage);
    ImagePrompt imagePrompt = new ImagePrompt(imageMessageList, imageOptions);

    // 모델 호출 및 응답 받기
    ImageResponse imageResponse = imageModel.call(imagePrompt);

    // base64로 인코딩된 이미지 문자열 얻기
    String b64Json = imageResponse.getResult().getOutput().getB64Json();
    return b64Json;
  }
}
