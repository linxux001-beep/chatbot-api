package com.linxux001.chatbotapidomain.zsxq;

import com.linxux001.chatbotapidomain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import com.linxux001.chatbotapidomain.zsxq.model.vo.Topics;

import java.io.IOException;
import java.util.List;


public interface IZsxqApi {
    
    UnAnsweredQuestionsAggregates queryUnAnswerQuestionsTopicId(String groupId, String cookie)throws IOException;
    UnAnsweredQuestionsAggregates queryUnAnswerQuestionsTopicId2(String groupId, String cookie)throws IOException;
    
    boolean answer(String groupId, String cookie, String topicId, String text, boolean silenced) throws IOException;
    boolean answer2(String commentId, String cookie, String text, boolean silenced) throws IOException;
}
