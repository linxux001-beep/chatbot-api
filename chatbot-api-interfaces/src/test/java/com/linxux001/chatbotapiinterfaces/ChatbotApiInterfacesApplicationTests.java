package com.linxux001.chatbotapiinterfaces;

import com.linxux001.chatbotapidomain.ai.IOpenAI;
import com.linxux001.chatbotapidomain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import com.linxux001.chatbotapidomain.zsxq.model.vo.Topics;
import com.linxux001.chatbotapidomain.zsxq.IZsxqApi;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class ChatbotApiInterfacesApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(ChatbotApiInterfacesApplicationTests.class);

   

    @Value("${chatbot-api.gourpId}")
    private String groupId;
    
    @Value("${chatbot-api.cookie}")
    private String cookie;
    
    @Value("${chatbot-api.openAiKey}")
    private String openAiKey;
    
    @Resource
    private IZsxqApi zsxqApi;
    
    @Resource
    private IOpenAI openAI;
    
    @Test
    public void test_zsxqApi()throws IOException{
        UnAnsweredQuestionsAggregates unansweredQuestionsAggregates = zsxqApi.queryUnAnswerQuestionsTopicId(groupId,cookie);
        log.info("unansweredQuestionsAggregates:{}", JSON.toJSONString(unansweredQuestionsAggregates));

        List<Topics> topics=unansweredQuestionsAggregates.getResp_data().getTopics();
        for (Topics topic : topics) {
            String topicId=topic.getTopic_id();
            String text=topic.getQuestion().getText();
            log.info("topicId:{},text:{}",topicId,text);
            
            zsxqApi.answer(groupId,topicId,cookie,text,false);
        }
    }
    
    @Test
    public void test_openAI()throws IOException{
        String question="你好，你是谁？";
        String answer=openAI.doChatGPT(openAiKey,question);
        log.info("answer:{}",answer);
    }
    
}
