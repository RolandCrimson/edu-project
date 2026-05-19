package com.lastcoder.vector_store.service;

import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.embedding.EmbeddingResponseMetadata;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmbedService {
  private final EmbeddingModel embeddingModel;
  private final VectorStore vectorStore;

  public void textEmbedding(String question) {
    EmbeddingResponse response = embeddingModel.embedForResponse(List.of(question));

    // 임베딩 모델 정보 얻기
    EmbeddingResponseMetadata metadata = response.getMetadata();
    log.info("모델 이름: {}", metadata.getModel());
    log.info("모델의 임베딩 차원: {}", embeddingModel.dimensions());

    // 임베딩 결과 얻기
    Embedding embedding = response.getResults().get(0);
    log.info("벡터 차원: {}", embedding.getOutput().length);
    log.info("벡터: {}", embedding.getOutput());
  }

  // public void textEmbedding(String question) {
  // float[] vector = embeddingModel.embed(question);
  // log.info("벡터 차원: {}", vector.length);
  // log.info("벡터: {}", vector);
  // }

  public void addDocument() {
    // Document 목록 생성
    List<Document> documents = List.of(
        new Document("사려니숲길은 제주의 숨은 비경 31곳 중 하나로, 비자림로를 시작으로 물찻오름과 사려니 오름을 거쳐가는 삼나무가 우거진 숲길이다.",
            Map.of("location", "제주도", "type", "숲")),
        new Document("죽변해안스카이레일은 파도소리를 들으며 바닷길을 달리는 해안 모노레일이다.", Map.of("location", "울진", "type", "액티비티")),
        new Document("곶자왈은 제주의 용암지대에 만들어진 특이한 숲이다.", Map.of("location", "제주도", "type", "숲")),
        new Document("흥겨운 체험과 풍류로 가득한 전통문화 테마파크 한국민속촌", Map.of("location", "용인", "type", "테마공원")),
        new Document("문경 레일바이크는 국내 최초로 철로를 달리는 레일 바이크이다.", Map.of("location", "문경", "type", "액티비티")),
        new Document("서울 잠실에 있는 롯데월드는 가족과 함께 즐길 수 있는 도심 놀이공원이다.",
            Map.of("location", "서울", "type", "액티비티")),
        new Document("제주도 서귀포시 안덕면에 자리한 피규어뮤지엄은 국내외 유명한 캐릭터의 피규어를 한자리에서 볼수 있는 국내 최댁 규모의 피규어 박물관이다.",
            Map.of("location", "제주도", "type", "테마공원")));

    // 벡터 저장소에 저장
    vectorStore.add(documents);
  }

  public List<Document> searchDocumentByText(String question) {
    // List<Document> documents = vectorStore.similaritySearch(question); // default
    // 4개
    List<Document> documents = vectorStore.similaritySearch(
        SearchRequest.builder()
            .query(question)
            .topK(3)
            .build());
    return documents;
  }

  public List<Document> searchDocumentBySearchRequest(String question) {
    List<Document> documents = vectorStore.similaritySearch(
        SearchRequest.builder()
            .query(question)
            .topK(1)
            .similarityThreshold(0.3)
            .filterExpression("location == '제주도' && type == '숲'")
            .build());
    return documents;
  }

  public void deleteDocument() {
    vectorStore.delete("type == '액티비티'");
  }
}
