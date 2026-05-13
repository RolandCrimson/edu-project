package com.lastcoder.llm_output.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lastcoder.llm_output.dto.Restaurant;
import com.lastcoder.llm_output.service.ParameterizedTypeReferenceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/springai")
@Slf4j
@RequiredArgsConstructor
public class ParameterizedTypeReferenceController {
  private final ParameterizedTypeReferenceService paramService;

  @PostMapping(value = "/generic-bean-output-converter", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Restaurant> genericBeanOutputConverter(@RequestParam String cities) {
    List<Restaurant> restaurants = paramService.BeanOutputConverter(cities);
    return restaurants;
  }
}
