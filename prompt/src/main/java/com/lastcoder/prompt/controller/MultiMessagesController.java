package com.lastcoder.prompt.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.chat.messages.Message;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lastcoder.prompt.service.MultiMessagesService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/springai")
@Slf4j
@RequiredArgsConstructor
public class MultiMessagesController {
    // ##### 필드 #####
    private final MultiMessagesService messageService;

    // ##### 요청 매핑 메소드 #####
    @PostMapping(value = "/multi-messages", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String multiMessages(@RequestParam String question, HttpSession session) {
        @SuppressWarnings("unchecked")
        List<Message> chatMemory = (List<Message>) session.getAttribute("chatMemory");
        if (chatMemory == null) {
            log.info("chatMemory == null");
            chatMemory = new ArrayList<Message>();
            session.setAttribute("chatMemory", chatMemory);
        }
        String answer = messageService.multiMessages(question, chatMemory);
        return answer;
    }
}
