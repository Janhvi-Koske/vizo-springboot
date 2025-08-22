package com.vizo.vizo;

import java.util.List;

public interface MessageService {
    
    Message createMessage(User user, Integer chatId, Message req) throws Exception;

    List<Message> findChatsMessages(Integer chatId) throws Exception;
}
