package com.homefit.homefit.consult.config;

import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdvisorConfig {
    @Autowired
    ChatMemory chatMemory;

    @Bean
    MessageChatMemoryAdvisor messageChatMemoryAdvisor() {
        return MessageChatMemoryAdvisor.builder(chatMemory).build();
    }
    
    @Bean
    QuestionAnswerAdvisor questionAnwerAdvisor(VectorStore vectorStore) {
    	return QuestionAnswerAdvisor
    			.builder(vectorStore)
    			.searchRequest(SearchRequest.builder().similarityThreshold(0.91).topK(6).build())
    			.build();
    }
}
