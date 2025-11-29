package com.linxux001.chatbotapidomain.zsxq;

import com.linxux001.chatbotapidomain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;

import java.io.IOException;


public interface IZsxqApi {
    
    UnAnsweredQuestionsAggregates queryUnAnswerQuestionsTopicId(String groupId, String cookie)throws IOException;
    
    boolean answer(String groupId, String cookie, String topicId, String text, boolean silenced) throws IOException;
}
