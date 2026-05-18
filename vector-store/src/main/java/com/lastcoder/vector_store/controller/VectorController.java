package com.lastcoder.vector_store.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lastcoder.vector_store.service.EmbedService;
import com.lastcoder.vector_store.service.FaceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/springai")
@Slf4j
@RequiredArgsConstructor
public class VectorController {
  private final EmbedService embedService;
  private final FaceService faceService;

  @PostMapping(value = "/text-embedding", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
  public String textEmbedding(@RequestParam String question) {
    embedService.textEmbedding(question);
    return "터미널 출력을 확인하세요.";
  }

  @PostMapping(value = "/add-document", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
  public String addDocument(@RequestParam String question) {
    embedService.addDocument();
    return "벡터 저장소에 Document들이 저장되었습니다.";
  }

  @PostMapping(value = "/search-document-text", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
  public String searchDocumentByText(@RequestParam String question) {
    List<Document> documents = embedService.searchDocumentByText(question);

    String text = "";
    for (Document document : documents) {
      text += "<div class='mb-2'>";
      text += "  <span class='me-2'>유사도 점수: %f,</span>".formatted(document.getScore());
      text += "  <span>%s(%s)</span>".formatted(document.getText(),
          document.getMetadata().get("type"));
      text += "</div>";
    }
    return text;
  }

  @PostMapping(value = "/search-document-sr", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
  public String searchDocumentBySearchRequest(@RequestParam String question) {
    List<Document> documents = embedService.searchDocumentBySearchRequest(question);
    System.out.println("검색된 Document 수: " + documents.size());
    String text = "";
    for (Document document : documents) {
      text += "<div class='mb-2'>";
      text += "  <span class='me-2'>유사도 점수: %f,</span>".formatted(document.getScore());
      text += "  <span>%s(%s)</span>".formatted(document.getText(),
          document.getMetadata().get("type"));
      text += "</div>";
    }
    return text;
  }

  @PostMapping(value = "/delete-document", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
  public String deleteDocument(@RequestParam String question) {
    embedService.deleteDocument();
    return "Document들이 삭제되었습니다.";
  }

  @PostMapping(value = "/add-face", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
  public String addFace(@RequestParam String personName, @RequestParam MultipartFile[] attach) throws IOException {
    for (MultipartFile mf : attach) {
      faceService.addFace(personName, mf);
    }
    return "얼굴이 저장되었습니다.";
  }

  @PostMapping(value = "/find-face", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
  public String findFace(@RequestParam MultipartFile attach) throws IOException {
    String personName = faceService.findFace(attach);
    return personName;
  }
}
