package com.vizo.vizo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
public class CreateMessage {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @PostMapping("/api/messages/chat/{chatId}")
    public Message creatMessage(@RequestBody Message req, @RequestHeader("Authorization") String jwt,@PathVariable Integer chatId) throws Exception{
        User user=userService.findUserByJwt(jwt);
        Message message=messageService.creatMessage(user, chatId, req);
        return message;
    }


    
    @GetMapping("/api/messages/chat/{chatId}")
    public List<Message> findChatsMessage( @RequestHeader("Authorization") String jwt,@PathVariable Integer chatId) throws Exception{
        User user=userService.findUserByJwt(jwt);
        List<Message> messages=messageService.findChatsMessages(chatId);
        return messages;
    }
    










}
