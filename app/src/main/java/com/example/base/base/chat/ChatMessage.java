package com.example.base.base.chat;

import com.base.Models.Message;
import com.base.Models.User;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.Date;

/**
 * Created by Devam on 18-Nov-17.
 */

public class ChatMessage implements IMessage {

    private final Message message;

    public ChatMessage(Message message) {
        this.message = message;
    }

    @Override
    public String getId() {
        return String.valueOf(this.message.getId());
    }

    @Override
    public String getText() {
        return this.message.getContent();
    }

    @Override
    public ChatUser getUser() {

        //Static
        User user = new User();
        user.setId(1);
        user.setName("Devam");
        return new ChatUser(user);
    }

    @Override
    public Date getCreatedAt() {
        return this.message.getCreated_at();
    }
}
