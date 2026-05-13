package com.lastcoder.llm_output.dto;

import lombok.Data;

@Data
public class ReviewClassification {
  public enum Sentiment {
    POSITIVE, NEGATIVE
  }

  private String review;
  private Sentiment classification;
}
