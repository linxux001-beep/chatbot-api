package com.linxux001.chatbotapiinterfaces;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;


public class ApiTest {

    @Test
    void contextLoads() throws IOException {

        CloseableHttpClient httpClient= HttpClientBuilder.create().build();

        HttpGet httpGet = new HttpGet("https://api.zsxq.com/v2/groups/28885518425541/topics?scope=all&count=20");

        httpGet.addHeader("cookie","sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22581511815222214%22%2C%22first_id%22%3A%221891f90b8ec13-024ce7c5010db2-26031d51-2359296-1891f90b8edad4%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTg5MWY5MGI4ZWMxMy0wMjRjZTdjNTAxMGRiMi0yNjAzMWQ1MS0yMzU5Mjk2LTE4OTFmOTBiOGVkYWQ0IiwiJGlkZW50aXR5X2xvZ2luX2lkIjoiNTgxNTExODE1MjIyMjE0In0%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22581511815222214%22%7D%2C%22%24device_id%22%3A%221891f90b8ec13-024ce7c5010db2-26031d51-2359296-1891f90b8edad4%22%7D; abtest_env=product; zsxq_access_token=8B136CA6-94A0-4712-AC70-5655680875E2_6D66978FEA2B7436");
        httpGet.addHeader("Content-Type","application/json;charset=UTF-8");

        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(httpResponse.getEntity());
            System.out.println(res);
        }else {
            System.out.println("请求失败");
            System.out.println(httpResponse.getStatusLine().getStatusCode());
        }
    }


    @Test
    void an() throws IOException {

        CloseableHttpClient httpClient= HttpClientBuilder.create().build();

        HttpPost httpPost = new HttpPost("https://api.zsxq.com/v2/topics/14588524541554512/comments");

        httpPost.addHeader("cookie","sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22581511815222214%22%2C%22first_id%22%3A%221891f90b8ec13-024ce7c5010db2-26031d51-2359296-1891f90b8edad4%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTg5MWY5MGI4ZWMxMy0wMjRjZTdjNTAxMGRiMi0yNjAzMWQ1MS0yMzU5Mjk2LTE4OTFmOTBiOGVkYWQ0IiwiJGlkZW50aXR5X2xvZ2luX2lkIjoiNTgxNTExODE1MjIyMjE0In0%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22581511815222214%22%7D%2C%22%24device_id%22%3A%221891f90b8ec13-024ce7c5010db2-26031d51-2359296-1891f90b8edad4%22%7D; abtest_env=product; zsxq_access_token=8B136CA6-94A0-4712-AC70-5655680875E2_6D66978FEA2B7436");
        httpPost.addHeader("Content-Type","application/json;charset=UTF-8");

        String paramJson = "{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"这是api回答\\n\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"mentioned_user_ids\": []\n" +
                "  }\n" +
                "}";

        StringEntity comment = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));

        httpPost.setEntity(comment);
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(httpResponse.getEntity());
            System.out.println(res);
        }else {
            System.out.println("请求失败");
            System.out.println(httpResponse.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void test_chatGPT() throws IOException {
        CloseableHttpClient httpClient= HttpClientBuilder.create().build();

        HttpPost httpPost = new HttpPost("https://api.deepseek.com/v1/chat/completions");
        httpPost.addHeader("Content-Type","application/json");
        httpPost.addHeader("Authorization","Bearer sk-8ede83df9ce049bd94df97f36d78b262");

        String paramJson = "{\"model\": \"deepseek-chat\",\n" +
                "        \"messages\": [\n" +
                "          {\"role\": \"system\", \"content\": \"You are a helpful assistant.\"},\n" +
                "          {\"role\": \"user\", \"content\": \"帮我写一个冒泡排序!\"}\n" +
                "        ],\n" +
                "        \"stream\": false }";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        httpPost.setEntity(stringEntity);
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(httpResponse.getEntity());
            System.out.println(res);
        }else {
            System.out.println("请求失败");
            System.out.println(httpResponse.getStatusLine().getStatusCode());
        }
    }
}
