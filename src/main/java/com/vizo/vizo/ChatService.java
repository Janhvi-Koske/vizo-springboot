package com.vizo.vizo;

import java.util.List;

public interface ChatService {

     Chat createChat(User reqUser , User user2);
     Chat findChatById(Integer chatId) throws Exception;
     List<Chat> findUsersChat(Integer userId);
}
