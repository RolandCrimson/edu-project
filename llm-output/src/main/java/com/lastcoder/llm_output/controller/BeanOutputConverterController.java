package com.lastcoder.llm_output.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lastcoder.llm_output.dto.Restaurant;
import com.lastcoder.llm_output.service.BeanOutputConverterService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/springai")
@Slf4j
@RequiredArgsConstructor
public class BeanOutputConverterController {
  private final BeanOutputConverterService beanService;

  @PostMapping(value = "/bean-output-converter", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Restaurant beanOutputConverter(@RequestParam String city) {
    Restaurant hotel = beanService.beanOutputConverter(city);
    return hotel;
  }
}
