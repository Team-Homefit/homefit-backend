package com.homefit.homefit.consult.controller.request;

import com.homefit.homefit.consult.domain.ReferenceType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AddReferenceRequest {
    private final List<Word> words;
    
    @Override
	public String toString() {
		return "AddReferenceRequest [words=" + words + "]";
	}

	@Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Word {
        private String content;
        private ReferenceType referenceType;
        private List<String> tags;
        
		@Override
		public String toString() {
			return "Word [content=" + content + ", referenceType=" + referenceType + ", tags=" + tags + "]";
		}
    }
}
