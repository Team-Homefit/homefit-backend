package com.homefit.homefit.consult.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.ai.vectorstore.redis.RedisVectorStore.MetadataField;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPooled;

@Configuration
public class VectorStoreConfig {
    @Value("${spring.ai.vectorstore.redis.index}")
    String index;
    @Value("${spring.ai.vectorstore.redis.prefix}")
    String prefix;
    @Value("${redis-stack.host}")
    String host;
    @Value("${redis-stack.port}")
    Integer port;

    @Bean
    VectorStore vectorStore(EmbeddingModel embeddingModel) {
        return RedisVectorStore.builder(new JedisPooled(host, port), embeddingModel)
                .indexName(index)
                .prefix(prefix)
                .metadataFields(
                        MetadataField.tag("type"),
                        MetadataField.tag("tags"),
                        MetadataField.tag("date")
                )
                .initializeSchema(true)
                .build();
    }
}
