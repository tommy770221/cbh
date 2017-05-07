package com.cbh.web;

import com.cbh.mongo.LineMessage;
import com.cbh.mongo.Statements;
import com.cbh.service.LineMessageMongoService;
import com.cbh.service.StatementsMongoService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.io.ByteStreams;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import com.linecorp.bot.client.LineMessagingServiceBuilder;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.CallbackRequest;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import retrofit2.Response;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by Tommy on 2017/4/14.
 */
@RequestMapping("/line")
@Controller
public class LineBotController {


    private final ObjectMapper objectMapper=buildObjectMapper();


    @Autowired
    private StatementsMongoService statementsMongoService;
    @Autowired
    private LineMessageMongoService lineMessageService;

    @RequestMapping(value = "block",method ={RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    @ResponseBody
    public String blockTemplate(HttpServletRequest httpServletRequest) throws IOException {
        String channelToken="hhNohi5sJV4/yj1tvTCvBPrxSop6WKo+GsxOCbE7dI8tYc1+8xbUIFm7raVZ7CrBpkt2N29F3QngT7HyEC/OOi1Tw+n281xb7YOwXku1c1SnK4FAbkpa0J+Vzy5Xz3/6+uCw9JMjibkIjY3nilOg6wdB04t89/1O/w1cDnyilFU=";
       //LineBotCallbackRequestParser lineBotCallbackRequestParser=new LineBotCallbackRequestParser(new LineSignatureValidator(channelToken.getBytes()));// StringWriter writer = new StringWriter();
       // IOUtils.copy(httpServletRequest.getInputStream(), writer, "utf-8");
       String theString ="";
        boolean isMiss;
       // System.out.println(theString);
        try {
           // CallbackRequest callbackRequest = lineBotCallbackRequestParser.handle(httpServletRequest);
            byte[] json = ByteStreams.toByteArray(httpServletRequest.getInputStream());
            CallbackRequest callbackRequest = (CallbackRequest) objectMapper.readValue(json, CallbackRequest.class);
            List<Event> events= callbackRequest.getEvents();
            for(Event event:events){
                if(event instanceof MessageEvent) {
                    MessageEvent env=(MessageEvent) event;
                    if(env.getMessage() instanceof TextMessageContent) {
                    MessageEvent<TextMessageContent> askContent=(MessageEvent<TextMessageContent>) event;
                    TextMessage ask=new TextMessage(askContent.getMessage().getText());
                    String askForJeiba=".*";
                    String responseAns= "對不起,我聽不懂你再說什麼";

                        JiebaSegmenter segmenter = new JiebaSegmenter();
                        System.out.println(segmenter.process(ask.getText(), JiebaSegmenter.SegMode.SEARCH).toString());
                        List<SegToken> segTokenList=segmenter.process(ask.getText(), JiebaSegmenter.SegMode.SEARCH);
                        for(SegToken segToken:segTokenList){
                            System.out.println(segToken.word.toString());
                            askForJeiba=askForJeiba+segToken.word.toString()+".*";
                        }
                        List<Statements> statementsList=statementsMongoService.findStatementsByRegexpResponse(askForJeiba);

                        if(! (statementsList==null)){
                            int ran= (int)(Math.random()*300+1);
                            if(statementsList.size()!=0){
                                int i=ran % statementsList.size();
                                responseAns=statementsList.get(i).getText();
                                TextMessage textMessage = new TextMessage(responseAns);
                                ReplyMessage replyMessage = new ReplyMessage(
                                        env.getReplyToken() ,
                                        textMessage
                                );
                                Response<BotApiResponse> response =
                                        LineMessagingServiceBuilder
                                                .create(channelToken)
                                                .build()
                                                .replyMessage(replyMessage)
                                                .execute();
                            }else{
                                TextMessage textMessage = new TextMessage(askContent.getMessage().getText());
                                ReplyMessage replyMessage = new ReplyMessage(
                                        env.getReplyToken() ,
                                        textMessage
                                );
                                Response<BotApiResponse> response =
                                        LineMessagingServiceBuilder
                                                .create(channelToken)
                                                .build()
                                                .replyMessage(replyMessage)
                                                .execute();
                            }
                        }

                    LineMessage lineMessage=new LineMessage();
                    lineMessage.setAskMessage(ask.getText());
                    lineMessage.setResponseMessage(responseAns);
                    lineMessage.setUserLineId(event.getSource().getUserId());
                    lineMessage.setCreateDate(new Date());
                    lineMessageService.save(lineMessage);
                    }else{
                        System.out.println(env.getMessage().getClass().getClass().getName());
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "{\"test\":\""+theString+"\"}";
    }

    private static ObjectMapper buildObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule()).configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        return objectMapper;
    }
}
