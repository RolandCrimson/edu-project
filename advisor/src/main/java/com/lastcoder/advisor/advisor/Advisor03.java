package com.lastcoder.advisor.advisor;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.core.Ordered;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Advisor03 implements CallAdvisor {
  @Override
  public String getName() {
    return this.getClass().getSimpleName();
  }

  @Override
  public int getOrder() {
    return Ordered.HIGHEST_PRECEDENCE + 3;
  }

  @Override
  public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {
    log.info("[Before nextCall()]");
    ChatClientResponse advisedResponse = chain.nextCall(request);
    log.info("[After nextCall()]");
    return advisedResponse;
  }

}