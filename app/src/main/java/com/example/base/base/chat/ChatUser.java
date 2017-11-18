package com.example.base.base.chat;


import com.base.Models.User;
import com.stfalcon.chatkit.commons.models.IUser;

/**
 * Created by Devam on 18-Nov-17.
 */

public class ChatUser implements IUser{


    private final User user;

    public ChatUser(User user){
        this.user = user;
    }

    @Override
    public String getId() {
        return String.valueOf(this.user.getId());
    }

    @Override
    public String getName() {
        return this.user.getName();
    }

    @Override
    public String getAvatar() {
        return null;
    }
}
