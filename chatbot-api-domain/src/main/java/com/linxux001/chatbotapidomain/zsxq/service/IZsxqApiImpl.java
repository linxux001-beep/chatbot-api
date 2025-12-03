package com.linxux001.chatbotapidomain.zsxq.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.linxux001.chatbotapidomain.zsxq.IZsxqApi;
import com.linxux001.chatbotapidomain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import com.linxux001.chatbotapidomain.zsxq.model.req.AnswerReq;
import com.linxux001.chatbotapidomain.zsxq.model.req.ReqData;
import com.linxux001.chatbotapidomain.zsxq.model.res.AnswerRes;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class IZsxqApiImpl implements IZsxqApi {
   
    private Logger logger= LoggerFactory.getLogger(IZsxqApi.class);
    
    @Override
    public UnAnsweredQuestionsAggregates queryUnAnswerQuestionsTopicId(String groupId, String cookie) throws IOException {

        CloseableHttpClient httpClient= HttpClientBuilder.create().build();

//        HttpGet httpGet = new HttpGet("https://api.zsxq.com/v2/groups/"+groupId+"/topics?scope=unanswered_questions&count=20");
        HttpGet httpGet = new HttpGet("https://api.zsxq.com/v2/groups/"+groupId+"/topics?scope=all&count=20");

        httpGet.addHeader("cookie",cookie);
        httpGet.addHeader("Content-Type","application/json;charset=UTF-8");

        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(httpResponse.getEntity());
            logger.info("拉取提问数据。groupId：{} jsonStr：{}", groupId, res);
            return JSON.parseObject(res,UnAnsweredQuestionsAggregates.class);
        }else {
            throw new RuntimeException("queryUnAnswerQuestionsTopicId error code is "+httpResponse.getStatusLine().getStatusCode());
        }
    }

    @Override
    public UnAnsweredQuestionsAggregates queryUnAnswerQuestionsTopicId2(String groupId, String cookie) throws IOException {
        CloseableHttpClient httpClient= HttpClientBuilder.create().build();

        HttpGet httpGet = new HttpGet("https://api.zsxq.com/v2/groups/28885518425541/topics?scope=all&count=20");

        httpGet.addHeader("cookie",cookie);
        httpGet.addHeader("Content-Type","application/json;charset=UTF-8");


        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(httpResponse.getEntity());
            logger.info("拉取提问数据。groupId：{} jsonStr：{}", groupId, res);
            return JSON.parseObject(res,UnAnsweredQuestionsAggregates.class);
        }else {
            throw new RuntimeException("queryUnAnswerQuestionsTopicId error code is "+httpResponse.getStatusLine().getStatusCode());
        }
    }

    @Override
    public boolean answer(String groupId, String cookie, String topicId, String text, boolean silenced) throws IOException {
        CloseableHttpClient httpClient= HttpClientBuilder.create().build();

        HttpPost httpPost = new HttpPost("https://api.zsxq.com/v2/topics/"+topicId+"/answer");

        httpPost.addHeader("cookie",cookie);
        httpPost.addHeader("Content-Type","application/json;charset=UTF-8");
        httpPost.addHeader("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
        
//        String paramJson = "{\n" +
//                "  \"req_data\": {\n" +
//                "    \"text\": \"这是api回答\\n\",\n" +
//                "    \"image_ids\": [],\n" +
//                "    \"mentioned_user_ids\": []\n" +
//                "  }\n" +
//                "}";

        AnswerReq answerReq=new AnswerReq(new ReqData(text,silenced));
        String paramJson = JSON.toJSONString(answerReq.toString());
        
        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));

        httpPost.setEntity(stringEntity);
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(httpResponse.getEntity());
            AnswerRes answerRes = JSON.parseObject(res, AnswerRes.class);
            return answerRes.isSucceeded();
        }else {
            throw new RuntimeException("answer error code is "+httpResponse.getStatusLine().getStatusCode());
        }
    }

    public boolean answer2(String commentId, String cookie, String text, boolean silenced) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/" + commentId + "/comments");
        post.addHeader("cookie", cookie);
        post.addHeader("Content-Type", "application/json;charset=utf8");
        post.addHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36");

        /* 测试数据
          String paramJson = "{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"自己去百度！\\n\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"silenced\": false\n" +
                "  }\n" +
                "}";
         */

        AnswerReq answerReq = new AnswerReq(new ReqData(text, silenced));
        String paramJson = JSONObject.toJSONString(answerReq);

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
//            logger.info("回答问题结果。groupId：{} topicId：{} jsonStr：{}", groupId, topicId, jsonStr);
            AnswerRes answerRes = JSON.parseObject(jsonStr, AnswerRes.class);
            return answerRes.isSucceeded();
        } else {
            throw new RuntimeException("answer Err Code is " + response.getStatusLine().getStatusCode());
        }
    }
    
}
