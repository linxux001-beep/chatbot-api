package com.linxux001.chatbotapidomain.ai.service;

import com.alibaba.fastjson.JSON;
import com.linxux001.chatbotapidomain.ai.IOpenAI;
import com.linxux001.chatbotapidomain.ai.model.aggregates.AIAnswer;
import com.linxux001.chatbotapidomain.ai.model.vo.Choices;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class IOpenAIImpl implements IOpenAI {
    
    private Logger logger= LoggerFactory.getLogger(IOpenAIImpl.class);
    
    @Override
    public String doChatGPT(String openAiKey,String text) throws IOException {

        CloseableHttpClient httpClient= HttpClientBuilder.create().build();

        HttpPost httpPost = new HttpPost("https://api.deepseek.com/v1/chat/completions");
        httpPost.addHeader("Content-Type","application/json");
        httpPost.addHeader("Authorization","Bearer sk-8ede83df9ce049bd94df97f36d78b262");

        String paramJson = "{\"model\": \"deepseek-chat\",\n" +
                "        \"messages\": [\n" +
                "          {\"role\": \"system\", \"content\": \"You are a helpful assistant.\"},\n" +
                "          {\"role\": \"user\", \"content\": \""+text+"\"}\n" +
                "        ],\n" +
                "        \"stream\": false }";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        httpPost.setEntity(stringEntity);
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(httpResponse.getEntity());
            AIAnswer aiAnswer = JSON.parseObject(jsonStr, AIAnswer.class);
            StringBuilder answers = new StringBuilder();
            List<Choices> choices = aiAnswer.getChoices();
            for (Choices choice : choices) {
                answers.append(choice.getMessage().getContent());
            }
            return answers.toString();
            
        }else {
            throw new RuntimeException("api.openai.com err code is "+httpResponse.getStatusLine().getStatusCode());
        }
        
    }
}
