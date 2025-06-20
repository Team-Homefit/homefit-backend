package com.homefit.homefit.consult.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homefit.homefit.consult.application.command.AddReferenceCommand;
import com.homefit.homefit.consult.application.dto.SimilarWord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReferenceService {
    private final VectorStore vectorStore;
    private final ObjectMapper mapper;

    public void add(AddReferenceCommand command) {
        List<Document> documents = command.getWords().stream()
                .map(word -> convert(word))
                .toList();

        vectorStore.add(documents);
    }

    public List<SimilarWord> search(String query) {
        SearchRequest searchRequest = SearchRequest.builder()
                .query(query)
                .similarityThreshold(0.91)
                .topK(5)
                .build();

        List<Document> results = this.vectorStore.similaritySearch(searchRequest);
        if (results == null) {
            return new ArrayList<>();
        }

        return results.stream().map(result -> SimilarWord.from(result, mapper)).toList();
    }

    private Document convert(AddReferenceCommand.Word word) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("type", word.getReferenceType().name());
        metadata.put("tags", word.getTags());
        metadata.put("date", LocalDateTime.now().toString());

        return new Document(word.getContent(), metadata);
    }
}
