package com.homefit.homefit.consult.controller;

import com.homefit.homefit.consult.application.ReferenceService;
import com.homefit.homefit.consult.application.command.AddReferenceCommand;
import com.homefit.homefit.consult.application.dto.SimilarWord;
import com.homefit.homefit.consult.controller.request.AddReferenceRequest;
import com.homefit.homefit.consult.controller.response.SearchSimilarReferenceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/consult/data")
@RestController
public class ReferenceController {
    private final ReferenceService referenceService;

    @PostMapping()
    public ResponseEntity<Void> add(@RequestBody AddReferenceRequest request) {
        log.info("단어 저장 요청: request = {}", request);

        List<AddReferenceCommand.Word> words = request.getWords().stream()
                .map(word -> AddReferenceCommand.Word.of(word.getContent(), word.getReferenceType(), word.getTags()))
                .toList();
        AddReferenceCommand command = AddReferenceCommand.of(words);

        referenceService.add(command);

        return ResponseEntity.ok(null);
    }

    @GetMapping
    public ResponseEntity<SearchSimilarReferenceResponse> search(@RequestParam String query) {
        log.info("단어 질의 요청: query = {}", query);

        List<SimilarWord> words = referenceService.search(query);

        return ResponseEntity.ok(SearchSimilarReferenceResponse.of(query, words));
    }
}
