package com.homefit.homefit.consult.application.command;

import com.homefit.homefit.consult.domain.ReferenceType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AddReferenceCommand {
    private final List<Word> words;

    public static AddReferenceCommand of(List<Word> words) {
        return new AddReferenceCommand(words);
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Word {
        private String content;
        private ReferenceType referenceType;
        private List<String> tags;

        public static Word of(String content, ReferenceType referenceType, List<String> tags) {
            return new Word(content, referenceType, tags);
        }
    }
}
