package com.homefit.homefit.consult.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.ai.document.Document;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homefit.homefit.exception.HomefitException;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SimilarWord {
    private final String id;
    private final String content;
    private final String type;
    private final List<String> tags;
    private final LocalDateTime time;
    private final Double distance;
    private final Double score;

    public static SimilarWord from(Document document, ObjectMapper mapper) {
        Object tagsObject = document.getMetadata().get("tags");
        
        List<String> tags;
		try {
			tags = mapper.readValue((String) tagsObject, new TypeReference<List<String>>() {});
		} catch (JsonProcessingException e) {
			throw new HomefitException(HttpStatus.INTERNAL_SERVER_ERROR, "데이터 파싱에 실패했습니다.");
		}
        
        return new SimilarWord(
                document.getId(),
                document.getText(),
                (String) document.getMetadata().get("type"),
                tags,
                LocalDateTime.parse((String) document.getMetadata().get("date")),
                ((Float) document.getMetadata().get("distance")).doubleValue(),
                document.getScore()
        );
    }
}
