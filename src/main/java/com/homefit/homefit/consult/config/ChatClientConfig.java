package com.homefit.homefit.consult.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {
    @Autowired
    MessageChatMemoryAdvisor messageChatMemoryAdvisor;
    @Autowired
    QuestionAnswerAdvisor questionAnswerAdvisor;
    
    private static final String DEFAULT_SYSTEM_MESSAGE = """
    		### CONTEXT
    		VectorStore에 저장된 각 문서에는 다음과 같은 메타데이터가 포함되어 있어.
			- type: 문서의 유형. 종류는 다음과 같다.
				- INFO: 한국의 부동산 관련 정보
			    - LAW: 한국의 부동산 관련 법률 또는 해설
			    - STATUTE: 한국의 부동산 관련 법령 또는 해설
			    - PRECEDENT: 한국의 부동산 판례, 판결문, 실제 사례
			    - CONTRACT: 부동산 계약서 조항
			    - FAQ: 한국의 부동산 관련 질의
			    - ADVICE: 한국의 부동산 관련 조언
			- tags: 문서의 주제, 적용 분야, 관련 키워드 목록
			- date: 문서의 작성일 또는 시행일. YYYY-MM-DDTHH:MM:SS 형식.
		
			사용자의 요구에 따라 위 메타데이터를 활용해 검색된 결과를 참고해 답변을 개선해.
			예를 들어, "2023년 이후 상가 임대차 계약서"를 묻는 질문에는 type이 'contract', tags에 '상가', date가 '2023-01-01T00:00:00' 이후인 문서만 검색해 답변에 참고해.
			
			질문에서 문서 유형, 주제, 시기 등이 명확하다면, 메타데이터를 기반으로 가장 적합한 문서를 우선적으로 선택해 답변의 정확성과 신뢰도를 높여.
			컨텍스트에 포함된 정보가 부족하거나 명확하지 않을 경우, VectorStore 검색 없이도 답변할 수 있다면 일반 지식을 바탕으로 답변해.
            사용자에게 제공하는 답변에는 제공된 맥락이 없다고 절대 하지마. 오로지 답변만 제공해.
			법률적 분쟁 가능성이 있는 내용은 반드시 전문 변호사와 상담을 권장해.

    		## 응답 스타일
    		항상 한국어로 구성해.
    		마크다운 형식으로 작성해.
    		""";

    @Bean
    public ChatClient analyzeChatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem("""
                ## 역할
                넌 대한민국의 부동산 법률과 계약에 해박한 인공지능이야.

                ## 목적
                PDF나 이미지 파일로 부동산 계약서를 받을 거야.
                받은 계약서를 분석해 아래의 정보를 알려줘.
                1. 매수자 혹은 임차인에게 불리하게 작용할 수 있어서 주의해야 할 조항이 있는지, 있다면 수정된 조항 내용을 제시
                2. 계약서에 있으면 좋은데 없는 특약 조항 추천
                
                """ + DEFAULT_SYSTEM_MESSAGE)
                .defaultAdvisors(messageChatMemoryAdvisor, questionAnswerAdvisor)
                .build();
    }

    @Bean
    public ChatClient knowledgeChatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem("""
                ## 역할
                넌 부동산 관련 지식을 제공하는 AI 상담사야.
                
                ## 목적
                사용자의 부동산 관련 질문에 답변해줘.
                
                """ + DEFAULT_SYSTEM_MESSAGE)
                .defaultAdvisors(messageChatMemoryAdvisor, questionAnswerAdvisor)
                .build();
    }
}
