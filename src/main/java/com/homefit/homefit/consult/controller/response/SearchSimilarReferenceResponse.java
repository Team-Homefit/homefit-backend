package com.homefit.homefit.consult.controller.response;

import com.homefit.homefit.consult.application.dto.SimilarWord;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchSimilarReferenceResponse {
    private final String query;
    private final List<Word> words;

    public static SearchSimilarReferenceResponse of(String query, List<SimilarWord> dtos) {
        return new SearchSimilarReferenceResponse(
                query,
                dtos.stream().map(Word::from).toList()
        );
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    static class Word {
        private final String content;
        private final String type;
        private final List<String> tags;
        private final LocalDateTime time;
        private final Double distance;
        private final Double score;

        public static Word from(SimilarWord dto) {
            return new Word(
                    dto.getContent(),
                    dto.getType(),
                    dto.getTags(),
                    dto.getTime(),
                    dto.getDistance(),
                    dto.getScore()
            );
        }
    }
}
