package com.linxux001.chatbotapiapplication.job;

import com.alibaba.fastjson.JSON;
import com.linxux001.chatbotapidomain.ai.IOpenAI;
import com.linxux001.chatbotapidomain.zsxq.IZsxqApi;
import com.linxux001.chatbotapidomain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import com.linxux001.chatbotapidomain.zsxq.model.vo.Topics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

@EnableScheduling
@Configuration
public class ChatbotSchedule {
    
    private Logger logger = LoggerFactory.getLogger(ChatbotSchedule.class);

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

    @Scheduled(cron = "0/30 * * * * ?")
    public void run() {
        try {
            
            if ((new Random()).nextBoolean()){
                logger.info("随机打烊中。。。");
                return;
            }

            GregorianCalendar calendar=new GregorianCalendar();
            int hour=calendar.get(GregorianCalendar.HOUR_OF_DAY);
            if (hour >= 22 || hour < 8){
                logger.info("打烊时间不工作，AI下班了。。。");
                return;
            }
            
//            UnAnsweredQuestionsAggregates unAnsweredQuestionsAggregates=this.zsxqApi.queryUnAnswerQuestionsTopicId(this.groupId,this.cookie);
            UnAnsweredQuestionsAggregates unAnsweredQuestionsAggregates =this.zsxqApi.queryUnAnswerQuestionsTopicId2(this.groupId,this.cookie);
//            logger.info("unAnsweredQuestionsAggregates:{}", JSON.toJSONString(unAnsweredQuestionsAggregates));
            List<Topics> topics = unAnsweredQuestionsAggregates.getResp_data().getTopics();
            logger.info("topics is:",topics);
            if (null == topics || topics.isEmpty()) {
                
                this.logger.info("topics is empty");
                return;
            }
            
            Topics topic = topics.get(0);
            String answer = this.openAI.doChatGPT(openAiKey,topic.getTalk().getText().trim());
            
            boolean answered = this.zsxqApi.answer2(topic.getTopic_id(), cookie, answer, false);
            
            logger.info("编号：{},问题：{},答案：{},是否回答成功：{}",topic.getTopic_id(),topic.getTalk().getText(),answer,answered);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
    