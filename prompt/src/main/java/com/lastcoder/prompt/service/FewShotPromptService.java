package com.lastcoder.prompt.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FewShotPromptService {
    private ChatClient chatClient;

    public FewShotPromptService(ChatClient.Builder chatClientBuilder) {
        chatClient = chatClientBuilder
                .defaultOptions(ChatOptions.builder()
                        .model("gpt-4o-mini")
                        .temperature(0.3)
                        .maxTokens(300))
                .build();
    }

    public String fewShotPrompt(String order) {
        // 프롬프트 생성
        String strPrompt = """
                고객 주문을 유효한 JSON 형식으로 바꿔주세요.
                추가 설명은 포함하지 마세요.

                예시1:
                파인트 사이즈, 체리쥬빌레, 알폰소망고로 주세요.
                JSON 응답:
                {
                  "size": "파인트",
                  "ingredients": ["체리쥬빌레", "알폰소망고"]
                }

                예시2:
                쿼터 사이즈, 엄마는외계인, 아몬드봉봉, 블루베리요거트, 애플민트로 주세요.
                JSON 응답:
                {
                  "size": "쿼터",
                  "ingredients": ["엄마는외계인", "아몬드봉봉", "블루베리요거트", "애플민트"]
                }

                예시3:
                패밀리 사이즈, 엄마는외계인, 아몬드봉봉, 블루베리요거트, 애플민트, 슈팅스타로 주세요.
                JSON 응답:
                {
                  "size": "패밀리",
                  "ingredients": ["엄마는외계인", "아몬드봉봉", "블루베리요거트", "애플민트", "슈팅스타"]
                }

                고객 주문: %s""".formatted(order);

        Prompt prompt = Prompt.builder()
                .content(strPrompt)
                .build();

        String icecreamOrderJson = chatClient.prompt(prompt)
                .call()
                .content();

        return icecreamOrderJson;
    }
}
