package com.dream.search.listener;

import com.dream.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ItemUpdateListener implements MessageListener {
    @Autowired
    private SearchItemService searchItemService;
    @Override
    public void onMessage(Message message) {
        try {
            //判断发送的消息类型是否是TextMessage类型
            if (message instanceof TextMessage){
                TextMessage message1 = (TextMessage) message;
                long itemId = Long.parseLong(message1.getText());
                //向索引库中更新索引
                searchItemService.updateSearchItemById(itemId);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
