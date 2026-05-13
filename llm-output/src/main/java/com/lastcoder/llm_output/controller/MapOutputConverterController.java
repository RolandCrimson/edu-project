package com.lastcoder.llm_output.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lastcoder.llm_output.service.MapOutputConverterService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/springai")
@Slf4j
@RequiredArgsConstructor
public class MapOutputConverterController {
  private final MapOutputConverterService aiService;

  @PostMapping(value = "/map-output-converter", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Map<String, Object> mapOutputConverter(@RequestParam String attraction) {
    // Map<String, Object> hotelInfo = aiService.mapOutputConverterLowLevel(hotel);
    Map<String, Object> tourInfo = aiService.mapOutputConverter(attraction);
    return tourInfo;
  }
}
